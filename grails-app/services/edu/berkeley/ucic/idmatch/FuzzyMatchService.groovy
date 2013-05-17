package edu.berkeley.ucic.idmatch

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;


class FuzzyMatchService {
 
  def grailsApplication;
  def configService;
  def schemaService;
  def matchingService;
  def personService;

   final String WILD_CARD = "*";
   final String EQUALS = "=";
   final String NOT_EQUALS="!=";
   final String NOT_EQUALS_FLAG=NOT_EQUALS;
   final int PREFIX_LENGTH = 2;

  //get this from configuration
  private static final int NTHREDS = 8;//grailsApplication.config.idMatch.THREADS;
  //static scope = "request";
  edu.berkeley.ucic.idmatch.TransposeService  transposeService;
  edu.berkeley.ucic.idmatch.EditDistanceService editDistanceService;
  edu.berkeley.ucic.idmatch.SoundexService soundexService;



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
      def persons = personService.getCache(); //return users from the cache
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
     * 
     */
    def java.util.List getMatches(java.util.Map jsonDataMap){
      log.info( "Enter: getMatches, json map is "+ jsonDataMap);
      java.util.List results = [];
      //get rules that have attributes with values in request and match types in configuration
      java.util.List validatedFuzzyRules = schemaService.getValidatedFuzzyRules(jsonDataMap);
      //for each rule, get matches
      validatedFuzzyRules.each { rule -> 
         java.util.List matchesByRule = getMatchesByRule(rule, jsonDataMap);
         results.addAll(matchesByRule); //add the matches for this rule to the total results
        } 
      log.info("Exit: listToMatch is the final result list");
      return results;

    } //getMatches() done

    
    
    def blockingFilter(String filterName){
     //construct the sql stmt based on the configuration of the blocking filter 
      

    }



    /**
     * for a given rule, get users to match using blocking Filter
     * use this result set to run through fuzzy match logic as configured in matchTypes
     * return users who match the rule
     */
    def java.util.List getMatchesByRule(java.util.Map rule, java.util.Map jsonDataMap){
      log.debug("Enter getMatchesByRule");
      java.util.List results = [];
      java.util.List listToMatch = getBlockingFilterResults(rule,jsonDataMap);
      if(listToMatch.size() == 0) return results;
 
      //now that you have users to match against, for each attr in the fuzzyRule, run a match 
      //as you run match on each attribute, the candidate list keeps shrinking, hence the attributes later in the list 
      //will only run matches on a list that is much shorter.
      def matchTypes = configService.getMatchAttributeFuzzyAlgorithmInfo();
      def schemaMap = configService.getSchemaMap();
      def fuzzyMatchAttrs = rule.matchAttributes;
      //if the fuzzyAttrs do not have Fuzzy Algorithms setup, then return empty list, do not proceed
      if(!schemaService.isFuzzyAttributeAlgorithmConfigured(fuzzyMatchAttrs)) return results;
      //proceed only if you know that all fuzzy attrs are configured with Algorithms
      fuzzyMatchAttrs.each() { fuzzyAttr ->
       log.debug("lets get algorithm for fuzzyAttr:  "+fuzzyAttr);
       log.debug("getting algorithm from ${matchTypes.get(fuzzyAttr)}");
         String serviceName = "edu.berkeley.ucic.idmatch."+matchTypes.get(fuzzyAttr).matchType+"Service";
         log.debug("serviceName is ${serviceName}");
         String distance = matchTypes.get(fuzzyAttr).distance;
         String registryName = schemaMap.get(fuzzyAttr);
         String inputValue = jsonDataMap.get(fuzzyAttr);
         log.debug("doing a match for ${listToMatch.size()} with inputValue = ${inputValue} and registryName = ${registryName} and serviceName = ${serviceName} and distance = ${distance}");
         def  myService = this.class.classLoader.loadClass(serviceName, true)?.newInstance()
         if(distance == null) { listToMatch =  myService.findMatches(inputValue,registryName,listToMatch); }
         else{ listToMatch = myService.findMatches(inputValue,registryName,listToMatch,distance as int);  }
        }
       log.debug("Exit: getMatchesByRule returns result of size "+listToMatch.size());
       return listToMatch; //after matching all attr, return the results
} //getMatchesForEachRule

    
    /**
     * given a blocking filter, get the result set
     * by constructing sql using the attrs in the filter
     */
     def java.util.List getBlockingFilterResults(java.util.Map rule, java.util.Map jsonDataMap){
         log.debug("Enter: getBlockingFilterResults");
         def blockingFilterAttrs = rule.blockingFilter;
         def schemaMap = configService.getSchemaMap();
         //construct SQL stmt
        def ruleStmt; //empty sql stmt
        for(attr in  blockingFilterAttrs) {
                    log.debug( "got ${attr} to make sql");
                    //return all users if it is a WILD_CARD
                    if(attr.equals(WILD_CARD)){ log.debug(attr+" is "+WILD_CARD); return personService.getCache(); }
                    def properAttr;
                    def sqlOperator;
                    if(attr.contains(NOT_EQUALS_FLAG)) {
                       properAttr = attr.substring(2);
                       sqlOperator = NOT_EQUALS;
                    }else {
                       properAttr = attr;
                       sqlOperator = EQUALS;
                    }
                    
                    def jsonInputValue = jsonDataMap.get(properAttr);
                    def realColName = schemaMap.get(properAttr);
                    //if sqlStmt is empty
                    if((ruleStmt == null)) ruleStmt = "${realColName} ${sqlOperator} '${jsonInputValue}'";
                    else ruleStmt = "${ruleStmt} AND ${realColName} ${sqlOperator} '${jsonInputValue}'";
                    log.debug("new ruleStmt "+ruleStmt);
                } //for each attr loop
      //run blockingFilter sql to get users to run match against
      log.debug("final ruleStmt is "+ruleStmt);
      def hqlStmt = "from Person where ${ruleStmt}".trim();
      log.debug( "hqlStmt is "+hqlStmt );
      def listToMatch = Person.findAll("${hqlStmt}"); // uses HQL
      log.debug("Exit: getBlockingFilterResults with result size of "+listToMatch.size());
      return listToMatch;
     }

} //end class
