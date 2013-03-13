package dolphin

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;


class FuzzyMatchService {
 
  def grailsApplication;
  def matchingService;

  //get this from configuration
  private static final int NTHREDS = 8;//grailsApplication.config.idMatch.THREADS;
  //static scope = "request";
  def transpositionService;
  def editDistanceService;
  def soundexService;
  def userService;
  //def users = User.list();



    /* 
     * run fuzzy Match rules
     * this is deprecated as FuzzyRuleConfiguration is changed
     */
    def executeRules(java.util.Map jsonDataMap) {
      log.debug( "json map is "+ jsonDataMap);
      def rules = grailsApplication.config.idMatch.ruleSet;
      log.debug( "rules is "+rules);
      def ruleKeySet = rules.keySet(); //get keys for the rules
      def schemaMap = grailsApplication.config.idMatch.schemaMap;
      log.debug( "schemaMap is "+schemaMap);
      def cutOffScoreMap = grailsApplication.config.idMatch.cutOffScoreMap;
      log.debug( "cutOffScoreMap is "+cutOffScoreMap);
      def exactCutOffScore = cutOffScoreMap.get("exact") as int;
      def reconCutOffScore = cutOffScoreMap.get("recon") as int;
      def jsonDataMapKey = jsonDataMap.keySet();
      log.debug( "json data key is "+jsonDataMapKey);
      def exactResults = [];
      def reconResults = [];
      //

      //get all persons in the registry
      log.debug( "pre-fetch "+new Date());
      def persons = userService.getCache(); //return users from the cache
      log.debug( "post fetch "+new Date());
      //for each person in the person list
      persons.each(){ person ->
            log.debug( "person is "+person);
            def personMatchScore = 0;
            def personProfile = [:];
            //for each attribute in jsonDataMap
            jsonDataMapKey.each() { ruleKey ->
             log.debug( "rule Key is " + ruleKey);
             def jsonDataValue = jsonDataMap.get(ruleKey).toString(); //get value from json
             log.debug( "json data value is "+jsonDataValue);
             def dbColName = schemaMap.get(ruleKey); //get real col name from schema map
             log.debug( "schemaMap value for ruleKey is ${dbColName}");
             def registryValue = person."${dbColName}";
             log.debug( "person col value for this key is ${registryValue}");
             def ruleConfigMap = rules.get(ruleKey); //get the rule configuration for a given key
             log.debug( "rule config is "+ruleConfigMap);
             if(ruleConfigMap != null){
             def ruleScore = matchingService.executeRule(ruleConfigMap, jsonDataValue,registryValue);
             log.debug( "ruleScore is "+ruleScore);
             personMatchScore = personMatchScore + ruleScore;
             personProfile.put(ruleKey, registryValue)};

         }

            log.debug( "personMatchScore is "+personMatchScore);
             if (personMatchScore == exactCutOffScore.intValue() ) {
                personProfile.put("person", person);
                personProfile.put("personMatchScore", personMatchScore);
                exactResults.add(personProfile);
                log.debug( "exact match results are "+exactResults);
             }
             else if ((personMatchScore >= reconCutOffScore.intValue())
                       &&
                       (personMatchScore <  exactCutOffScore.intValue() )
                      )
                     {  personProfile.put("person", person);
                        personProfile.put("personMatchScore", personMatchScore);
                        reconResults.add(personProfile);
                        log.debug( "recon match results are "+reconResults);
                     }
      }
       def response = [:];
       response.put("input", jsonDataMap)
       response.put("exact" , exactResults);
       response.put("recon" , reconResults);
       log.debug( "Date match complete "+new Date());
       return response;

     }


    /*
     * newer version of rule execution
     * based on newer configuration for fuzzyRules
     * for each attribute in the rule, see if the request has a value, if value missing, skip the rule
     * for each attribute in the rule, see if the fuzzyMatchType has matchType configuration set for that attribute
     * if not skip this rule
     * finally pick the first rule from the remaining rules and run the match
     * rules should be added to the configuration by order of precedence, that is if you think ssn,dob match is more important than fname,lname,
     * then this rule should be created higher.
     * 
     */
    def runMatch(java.util.Map jsonDataMap){
      java.util.List results = [];
      log.debug( "json map is "+ jsonDataMap);
      def fuzzyRules = grailsApplication.config.idMatch.fuzzyMatchRuleSet;
      log.debug( "rules is "+rules);
      def matchTypes = grailsApplication.config.idMatch.fuzzyMatchType;
      log.debug( "matchTypes is "+matchType);
      def matchTypeKeySet = matchType.keySet(); //get the attributes 
      log.debug( "matchTypeKeySet is" +matchTypeKeySet);
      def schemaMap = grailsApplication.config.idMatch.schemaMap;
      log.debug( "schemaMap is "+schemaMap);
      def jsonDataMapKey = jsonDataMap.keySet();
      log.debug( "json data key is "+jsonDataMapKey);
      java.util.List filteredFuzzyRules = [];
      //filter the rules and keep only those that have attr values in the request
      fuzzyRules.each(){ rule -> 
          log.debug(rule.size());
          int emptyAttributeCount = 0;
          rule.each() { attr -> 
          if(!jsonDataMap.has(attr)){log.debug("found ${attr} empty in request"); emptyAttributeCount = emptyAttributeCount+1; }
          }
          if(emptyAttributeCount == 0) filteredRuleList.add(rule);
      }
      //filter the rules further to remove rules that have attr with no match type in FuzzyMatchType configuration
      //if admin does not tell me which Fuzzy Match Type to use, I cannot use this attribute to do fuzzy match and hence I will skip this rule
      java.util.List doubleFilteredFuzzyRules;
      filteredFuzzyRules.each(){ rule ->
        int emptyAttributeCount = 0;
        rule.each() { attr ->
           if(!matchTypes.has(attr)) { log.debug("found ${attr} with no match type configured"); emptyAttributeCount = emptyAttributeCount+1 }
        } 
        if(emptyAttributeCount == 0) doubleFilteredFuzzyRules.add(rule);
      }
      //finally i have rules that have attributes with values in request and match types in configuration
      //it is time to do the match
      //java.util.List users = userService.getCache();
      doubleFilteredFuzzyRules[0].each { attr -> 
         String serviceName = matchTypes.get(attr).algorithm;
         log.debug(serviceName);
      }
 
      return results;

    }
    
    def blockingFilter(String filterName){
     //construct the sql stmt based on the configuration of the blocking filter 
      

    }

}
