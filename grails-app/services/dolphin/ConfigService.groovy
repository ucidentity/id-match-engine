package dolphin

/*
 * the purpose of this class is to decouple configuration access
 * so the other services that need access to configuration do not need to know how it is
 * fetched, the future version of this will be database based as opposed to config file based
 */

class ConfigService {
 
    def grailsApplication;

    /*
     * used to create a dynamic sql
     */
    def getCanonicalRules() {
       return grailsApplication.config.idMatch.canonicalMatchRuleSet;
    }


    /*
     * will pick only one rule out a set of rules configured here
     */
    def getFuzzyRules(){
       return grailsApplication.config.idMatch.fuzzyMatchRuleSet;
    }


    def getSchemaMap(){
        return grailsApplication.config.idMatch.schemaMap;
    }


   /*
    * fetch the blocking filter rules and then construct a dynamic sql stmt 
    */
    def getBlockingFilter(){
        
    }
  
    /*
     * for a given attribute, return the Fuzzy algorithm type to be used 
     */
    def getFuzzyMatchType(String attribute){
      def rules = grailsApplication.config.idMatch.attributeFuzzyMatchType;
      println "rules is "+rules;
      def ruleKeySet = rules.keySet(); //get keys for the rules
      def ruleConfigMap = rules.get(attribute);
      def algorithm = ruleConfigMap.algorithm;
      def distance = ruleConfigMap.distance;
      return algorithm;
    } 

    

    
}
