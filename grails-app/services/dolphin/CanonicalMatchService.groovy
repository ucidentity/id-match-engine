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
    def executeRules(java.util.Map jsonDataMap) {
     def results = "";
      log.debug( "json map is "+ jsonDataMap );
      def canonicalMatchRuleSet = grailsApplication.config.idMatch.canonicalMatchRuleSet;
      def allRulesStmt;
      canonicalMatchRuleSet.each {
           //if rule has only one attribute
           if(it.size() == 1 ) {
             def realColName = grailsApplication.config.idMatch.schemaMap.get(it[0]);
             def jsonInputValue = jsonDataMap.get(it[0]);
             if(jsonInputValue != null) {
             def ruleStmt = "${realColName} = '${jsonInputValue}'"
             allRulesStmt = allRulesStmt!=null? "${allRulesStmt} OR ${ruleStmt}" : ${ruleStmt}; }
           } else { 
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
                  }
                }
                //append to composite stmt 
                 if(emptyAttributeCount == 0) { allRulesStmt = allRulesStmt!=null? "${allRulesStmt} OR ${ruleStmt}" : ${ruleStmt};}
         }
      }
      def hqlStmt = "from User where ${selectStmt}".trim();
      log.debug( hqlStmt );
      results = User.findAll(hqlStmt); // uses HQL
      log.debug( "results are "+results);
      return results;

    }

}
