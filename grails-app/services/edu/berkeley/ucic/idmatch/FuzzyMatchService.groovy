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
     * newer version of rule execution
     * based on newer configuration for fuzzyRules
     * for each attribute in the rule, see if the request has a value, if value missing, skip the rule
     * for each attribute in the rule, see if the fuzzyMatchType has matchType configuration set for that attribute
     * if not skip this rule
     * finally pick the first rule from the remaining rules and run the match
     * 
     */
    def java.util.List getMatches(java.util.Map jsonDataMap){
      String actionName = "getMatches";
      log.info( "Enter:${actionName} json map is "+ jsonDataMap);
      java.util.List results = [];
      //get rules that have attributes with values in request and match types in configuration
      java.util.List validatedFuzzyRules = schemaService.getValidatedFuzzyRules(jsonDataMap);
      //for each rule, get matches
      validatedFuzzyRules.each { rule -> 
         java.util.List matchesByRule = getMatchesByRule(rule, jsonDataMap);
         results.addAll(matchesByRule); //add the matches for this rule to the total results
        } 
      log.info("Exit:${actionName} returns with results "+results);
      //transform the entries into summary entries as configured in Config.groovy
      return schemaService.personSummaryAdapter(results);

    } //getMatches() done

    
    /**
     * for a given rule, get users to match using blocking Filter
     * use this result set to run through fuzzy match logic as configured in matchTypes
     * return users who match the rule
     */
    def java.util.List getMatchesByRule(java.util.Map rule, java.util.Map jsonDataMap){
      String actionName = "getMatchesByRule";
      log.debug("Enter:${actionName}");
      java.util.List results = [];
      java.util.List listToMatch = getBlockingFilterResults(rule,jsonDataMap);
      if(listToMatch.size() == 0) return results;
 
      //now that you have users to match against, for each attr in the fuzzyRule, run a match 
      //as you run match on each attribute, the candidate list keeps shrinking, hence the attributes later in the list 
      //will only run matches on a list that is much shorter.
      def matchTypes = configService.getFuzzyAttributeAlgorithmMap();
      log.debug("matchTypes is "+matchTypes);
      log.debug("fuzzyAttributeAlgorithmMap is"+matchTypes);
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
       log.debug("Exit:${actionName} returns result of size "+listToMatch.size());
       return listToMatch; //after matching all attr, return the results
} //getMatchesForEachRule

    
    /**
     * given a blocking filter, get the result set
     * by constructing sql using the attrs in the filter
     */
     def java.util.List getBlockingFilterResults(java.util.Map rule, java.util.Map jsonDataMap){
         def actionName = "getBlockingFilterResults";
         log.debug("Enter:${actionName}");
         def blockingFilterAttrs = rule.blockingFilter;
         def schemaMap = configService.getSchemaMap();
         //construct SQL stmt
        def ruleStmt; //empty sql stmt
        for(attr in  blockingFilterAttrs) {
                    log.debug( "got ${attr} to make sql");
                    //return all users if it is a WILD_CARD or if blockingFilterAttr not in jsonDataMap
                    if(attr.equals(WILD_CARD) || !jsonDataMap.containsKey(attr)){ 
                      log.debug("Exit:${actionName} because ${attr} is either ${WILD_CARD} or empty");
                      return personService.getCache(); }

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
      log.debug("Exit:${actionName} with result size of "+listToMatch.size());
      return listToMatch;
     }

} //end class
