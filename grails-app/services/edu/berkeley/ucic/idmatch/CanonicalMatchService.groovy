package edu.berkeley.ucic.idmatch

class CanonicalMatchService {

    def grailsApplication;
    def configService;
    def schemaService;
   
    final String EQUALS = "=";
    final String NOT_EQUALS="!=";
    final String NOT_EQUALS_FLAG=NOT_EQUALS;
    final int PREFIX_LENGTH = 2;


    /**
     * returns canonical matches based on canonical rules
     *
     */
    def java.util.List  getMatches(java.util.Map jsonDataMap) {
      String method = "getMatches";
      log.info("Enter: ${method} with ${jsonDataMap}");
      java.util.List  results = [];
      def validatedRules = schemaService.getValidatedCanonicalRules(jsonDataMap);
      if(validatedRules.size() == 0) { 
        log.debug("Exit:${method} early as validatedRules is empty"); return results; }
      def hqlStmt = getSqlFromRules(validatedRules, jsonDataMap);
      results = Person.findAll("${hqlStmt}"); // uses HQL
      log.info("Exit: ${method} with result size "+results.size());
      if(results.size()>0) return schemaService.bulkPersonFriendlySchemaAdapter(results); 
      return results;
    }



     /**
     * build dynamic sql stmt for all the rules where attribute in the rule has incoming request value
     * for example: say a rule "ssn,lname,dob", if incoming value has no ssn, then this rule is skipped
     * if rule set is ["ssn","dob,lname"], then sql filter should be 
     * (ssn = foo) OR (dob = foo AND lname = foo )
      */
      def String getSqlFromRules(java.util.List validatedRules, java.util.Map jsonDataMap){
       String method = "getSqlFromRules";
       log.debug("Enter: ${method}");
       def schemaMap = configService.getSchemaMap();
       log.debug("got schema from configService ${schemaMap}");
       def allRulesStmt;
       validatedRules.each { rule ->
           //if rule has only one attribute
           log.debug("Working with rule: ${rule}");
                def ruleStmt;
                rule.each{ attr ->
                    log.debug( "working with ${attr} to make sql");
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
                    if((ruleStmt == null)) ruleStmt = "${realColName} ${sqlOperator} '${jsonInputValue}'";
                    else ruleStmt = "${ruleStmt} AND ${realColName} ${sqlOperator} '${jsonInputValue}'";
                    log.debug("rule stmt is ${ruleStmt}");
                } //for each attr loop
                //append to composite stmt
                if(allRulesStmt == null) allRulesStmt  =  "(${ruleStmt})";
                else {allRulesStmt = "${allRulesStmt} OR (${ruleStmt})";}
                log.debug("all rules stmt is ${allRulesStmt}");
      } // for each rule loop
      def hqlStmt = "from Person where ${allRulesStmt}".trim();
      log.debug( "Exit: ${method} with return of ${hqlStmt}" );
      return hqlStmt;
     }


    /*
     * only for debugging purposes
     */
    String hqlTest(){
      String hqlStmt = 
        "from Person where (referenceId = '111222333') OR (attr4 = '123456' AND attr5 = 'alla') OR (attr4 = 'venu' AND attr5 = 'alla' AND attr7 = 'Berkeley')";
      log.debug("hql stmt is ${hqlStmt}");
      java.util.List results = Person.findAll(hqlStmt);
      log.debug("results are "+results);
      return results;
      }
}
