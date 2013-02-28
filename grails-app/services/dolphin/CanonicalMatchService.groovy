package dolphin

class CanonicalMatchService {
    def grailsApplication;

    /*
     * will build dynamic sql stmt for all the rules where attribute in the rule has incoming request value
     * rule is skipped if there is no incoming request value for any of the attributes in the rule
     * for example: say a rule "ssn,lname,dob", if incoming value has no ssn, then this rule is skipped
     * if rule set is ["ssn","dob,lname"], then sql filter should be 
     * (ssn = foo) OR (dob = foo AND lname = foo )
     */
    def java.util.List  executeRules(java.util.Map jsonDataMap) {
      log.debug("Enter");
      def results = "";
      log.debug( "json map is "+ jsonDataMap );
      def canonicalMatchRuleSet = grailsApplication.config.idMatch.canonicalMatchRuleSet;
      log.debug(canonicalMatchRuleSet); 
      def rulesCompositeStmt;
      canonicalMatchRuleSet.each {
           //if rule has only one attribute
           log.debug("${it} and ${it.size()}");
           if(it.size() == 1 ) {
             def realColName = grailsApplication.config.idMatch.schemaMap.get(it[0]);
             log.debug("real col name ${realColName}");
             def jsonInputValue = jsonDataMap.get(it[0]);
             log.debug("json value for this attr "+jsonInputValue);
             if(jsonInputValue != null) {
               def ruleStmt = "${realColName} = '${jsonInputValue}'"
               log.debug("rule stmt is ${ruleStmt}"); 
               if(rulesCompositeStmt == null) rulesCompositeStmt  =  "(${ruleStmt})";  else {rulesCompositeStmt = "${rulesCompositeStmt} OR (${ruleStmt})";}
               log.debug(rulesCompositeStmt);
           }
          }else{ 
               //if rule has more than one attribute, construct AND statement
                log.debug( it.size() );
                def ruleStmt;
                def emptyAttributeCount = 0; //making an assumption that json payload has values for all the attributes in the rule
                it.each{ attr ->
                  log.debug( "got ${attr} to make sql");
                  def jsonInputValue = jsonDataMap.get(attr);
                  if(jsonInputValue == null) { log.debug( "${attr} value is empty"); emptyAttributeCount = emptyAttributeCount + 1; }
                  if(emptyAttributeCount == 0) {
                    def realColName = grailsApplication.config.idMatch.schemaMap.get(attr);
                    if((ruleStmt == null)) ruleStmt = "${realColName} = '${jsonInputValue}'";
                    else ruleStmt = "${ruleStmt} AND ${realColName} = '${jsonInputValue}'";
                    log.debug(ruleStmt);
                  }
                } //for each attr loop
                //append to composite stmt 
                 if(emptyAttributeCount == 0) { 
                      if(rulesCompositeStmt == null) rulesCompositeStmt  =  "(${ruleStmt})";  
                      else {rulesCompositeStmt = "${rulesCompositeStmt} OR (${ruleStmt})";}
                  }
                 log.debug(rulesCompositeStmt);
         }
      } // for each rule loop
      def hqlStmt = "from User where ${rulesCompositeStmt}".trim();
      log.debug( hqlStmt );
      results = User.findAll("${hqlStmt}"); // uses HQL
      log.debug( "results are "+results);
      java.util.ArrayList summaryResult = [];
      results.each{ result -> 
                    log.debug(result.attr1);
                    summaryResult.add(result.attr1);
                  }
      log.debug(summaryResult);
      return summaryResult;
    }

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
}
