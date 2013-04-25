package edu.berkeley.ucic.idmatch

class CanonicalMatchService {
    def grailsApplication;
   
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
      def validatedRules = getValidatedRules();
      def hqlStmt = getSqlFromRules(validatedRules, jsonDataMap);
      results = User.findAll("${hqlStmt}"); // uses HQL
      log.debug( "results are "+results);
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
        "from User where (attr1 = '111222333') OR (attr4 = '123456' AND attr3 = 'alla') OR (attr2 = 'venu' AND attr3 = 'alla' AND attr5 = 'Berkeley')";
      log.debug("hql stmt is ${hqlStmt}");
      java.util.List results = User.findAll(hqlStmt);
      log.debug("results are "+results);
      return results;

    }


    /**
     * rule is skipped if there is no incoming request value for any of the attributes in the rule
     */
     def java.util.List getValidatedRules(){

      def canonicalRules = grailsApplication.config.idMatch.canonicalMatchRuleSet;
      log.debug( "rules is "+canonicalRules);
      def matchTypeKeySet = matchTypes.keySet(); //get the attributes
      log.debug( "matchTypeKeySet is" +matchTypeKeySet);
      def schemaMap = grailsApplication.config.idMatch.schemaMap;
      log.debug( "schemaMap is "+schemaMap);
      def jsonDataMapKey = jsonDataMap.keySet();
      log.debug( "json data key is "+jsonDataMapKey);
      //filter the rules and keep only those that have attr values in the request
      //also remove rules that have attrs which are not configured for Match type algorithms
      java.util.List validatedFuzzyRules = [];
      canonicalRules.each(){ rule ->
          int emptyAttributeCount = 0;
          rule.each() { attr ->
            def properAttr;
                    if(attr.contains(NOT_EQUALS_FLAG)) {
                       properAttr = attr.substring(2);
                    }else {
                       properAttr = attr;
                    }

            log.debug("${jsonDataMap.has(properAttr)}");
            if(!jsonDataMap.has(properAttr)){log.debug("found ${attr} empty in request"); emptyAttributeCount = emptyAttributeCount+1; }
          }
          if(emptyAttributeCount == 0) validatedFuzzyRules.add(rule);
      }

      return validatedFuzzyRules;

     }


     /**
     * build dynamic sql stmt for all the rules where attribute in the rule has incoming request value
     * for example: say a rule "ssn,lname,dob", if incoming value has no ssn, then this rule is skipped
     * if rule set is ["ssn","dob,lname"], then sql filter should be 
     * (ssn = foo) OR (dob = foo AND lname = foo )
      */
      def String getSqlFromRules(java.util.List validatedRules, java.util.Map jsonDataMap){
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
                    def jsonInputValue = jsonDataMap.get(properAttr);
                    def realColName = grailsApplication.config.idMatch.schemaMap.get(properAttr);
                    if((ruleStmt == null)) ruleStmt = "${realColName} ${sqlOperator} '${jsonInputValue}'";
                    else ruleStmt = "${ruleStmt} AND ${realColName} ${sqlOperator} '${jsonInputValue}'";
                    log.debug(ruleStmt);
                } //for each attr loop
                //append to composite stmt
                if(allRulesStmt == null) allRulesStmt  =  "(${ruleStmt})";
                else {allRulesStmt = "${allRulesStmt} OR (${ruleStmt})";}
                log.debug(allRulesStmt);
      } // for each rule loop
      def hqlStmt = "from User where ${allRulesStmt}".trim();
      log.debug( hqlStmt );
      return hqlStmt;
     }
}
