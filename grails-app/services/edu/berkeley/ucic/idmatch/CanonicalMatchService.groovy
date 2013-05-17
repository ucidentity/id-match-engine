package edu.berkeley.ucic.idmatch

class CanonicalMatchService {
    def grailsApplication;
    def configService;
    def schemaService;
   
    final String EQUALS = "=";
    final String NOT_EQUALS="!=";
    final String NOT_EQUALS_FLAG=NOT_EQUALS;
    final int PREFIX_LENGTH = 2;


    /*
     */
    def java.util.List  getMatches(java.util.Map jsonDataMap) {
      log.info("Enter getMatches");
      java.util.List  results = [];
      log.debug( "json map is "+ jsonDataMap );
      def validatedRules = schemaService.getValidatedCanonicalRules(jsonDataMap);
      if(validatedRules.size() > 0) {
      def hqlStmt = getSqlFromRules(validatedRules, jsonDataMap);
      log.debug("do Person.findAll on "+hqlStmt);
      results = Person.findAll("${hqlStmt}"); // uses HQL
      log.debug( "results are "+results);
      }
      java.util.ArrayList summaryResult = [];
      results.each{ result -> 
                    log.debug(result.attr1);
                    summaryResult.add(result.attr1);
                  }
      log.info("Exiting getMatches");
      return summaryResult;
    }


    /*
     * only for debugging purposes
     */
    String execute(java.lang.Object jsonObject){ 
        return jsonObject.data;

    }

    /*
     * only for debugging purposes
     */
    String hqlTest(){
      String hqlStmt = 
        "from Person where (attr1 = '111222333') OR (attr4 = '123456' AND attr3 = 'alla') OR (attr2 = 'venu' AND attr3 = 'alla' AND attr5 = 'Berkeley')";
      log.debug("hql stmt is ${hqlStmt}");
      java.util.List results = Person.findAll(hqlStmt);
      log.debug("results are "+results);
      return results;

    }


     /**
     * build dynamic sql stmt for all the rules where attribute in the rule has incoming request value
     * for example: say a rule "ssn,lname,dob", if incoming value has no ssn, then this rule is skipped
     * if rule set is ["ssn","dob,lname"], then sql filter should be 
     * (ssn = foo) OR (dob = foo AND lname = foo )
      */
      def String getSqlFromRules(java.util.List validatedRules, java.util.Map jsonDataMap){
       log.debug("enter getSqlFromRules");
       def schemaMap = configService.getSchemaMap();
       log.debug("got schema from configService ${schemaMap}");
       def allRulesStmt;
       validatedRules.each { rule ->
           //if rule has only one attribute
           log.debug("${rule} and ${rule.size()}");
                def ruleStmt;
                rule.each{ attr ->
                    log.debug( "got ${attr} to make sql");
                    def properAttr; //name of attr after removing flag prefixes
                    def sqlOperator = EQUALS;
          if(attr.contains(NOT_EQUALS_FLAG)) {
             properAttr = attr.substring(NOT_EQUALS_FLAG.length());
             sqlOperator = NOT_EQUALS;
          } else { properAttr = attr; }
                    log.debug("after prefix cleanup, attr is ${properAttr}");
                    def jsonInputValue = jsonDataMap.get(properAttr);
                    def realColName = schemaMap.get(properAttr);
                    log.debug("building sql for column "+realColName+" with value "+jsonInputValue);
                    //def realColName = grailsApplication.config.idMatch.schemaMap.get(properAttr);
                    if((ruleStmt == null)) ruleStmt = "${realColName} ${sqlOperator} '${jsonInputValue}'";
                    else ruleStmt = "${ruleStmt} AND ${realColName} ${sqlOperator} '${jsonInputValue}'";
                    log.debug(ruleStmt);
                } //for each attr loop
                //append to composite stmt
                if(allRulesStmt == null) allRulesStmt  =  "(${ruleStmt})";
                else {allRulesStmt = "${allRulesStmt} OR (${ruleStmt})";}
                log.debug(allRulesStmt);
      } // for each rule loop
      def hqlStmt = "from Person where ${allRulesStmt}".trim();
      log.debug( "Exit getSqlFromRules with return of "+ hqlStmt );
      return hqlStmt;
     }
}
