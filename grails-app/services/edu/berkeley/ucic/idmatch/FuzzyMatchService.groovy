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
     * for each attribute in the rule, see if the fuzzyMatchType 
     * has matchType configuration set for that attribute
     * if not skip this rule
     * finally pick the first rule from the remaining rules and run the match
     * 
     */
    def java.util.List getMatches(java.util.Map jsonDataMap){

      String method = "getMatches";
      log.info( "Enter:${method} with jsonDataMap is "+ jsonDataMap);
      java.util.List results = [];

      //get rules that have attributes with values in request and match types in configuration
      java.util.List asIsRules = configService.getFuzzyRules().values().toArray();
      java.util.List validatedFuzzyRules = schemaService.getValidatedFuzzyRules(asIsRules,jsonDataMap);
      if(validatedFuzzyRules.size() == 0){
         log.debug("Exit:${method} early as validated rules is empty");
         return results;
      }

      //for each rule, get matches
      validatedFuzzyRules.each { rule -> 
         java.util.List matchesByRule = getMatchesByRule(rule, jsonDataMap);
         results.addAll(matchesByRule); //add the matches for this rule to the total results
        } 

      log.info("Exit:${method} returns with results "+results);
      //transform the entries into summary entries as configured in Config.groovy
      if(results.size()>0) return schemaService.bulkPersonFriendlySchemaAdapter(results);
      return results;

    } //getMatches() done

    
    /**
     * for a given rule, get users to match using blocking Filter
     * use this result set to run through fuzzy match logic as configured in matchTypes
     * return users who match the rule
     */
    def java.util.List getMatchesByRule(java.util.Map rule, java.util.Map jsonDataMap){

      String method = "getMatchesByRule";
      log.debug("Enter:${method} for rule ${rule}");
      java.util.List results = [];

      java.util.List listToMatch = getBlockingFilterResults(rule,jsonDataMap);
      if(listToMatch.size() == 0){
          log.debug("Exit:${method} early as blockingFilterResults is empty");
          return results;
      }

      if(rule.matchAttributes.size() == 0){
         log.debug("Exit:${method} early as matchAttributes is empty");
         if(rule.blockingFilter.contains(WILD_CARD)) return [];
         return listToMatch; //if not WILD_CARD, then return blocking filter results
      }
 
      /* now that you have users to match against, 
       * for each attr in the fuzzyRule, run a match 
       * as you run match on each attribute, the candidate list keeps shrinking, 
       * hence the attributes later in the list will only run matches 
       * on a list that is much shorter.
       */
      def matchTypes = configService.getFuzzyAttributeAlgorithmMap();
      log.debug("fuzzyAttributeAlgorithmMap is"+matchTypes);
      def schemaMap = configService.getSchemaMap();
      def fuzzyMatchAttrs = rule.matchAttributes;

      //if the fuzzyAttrs do not have Fuzzy Algorithms setup, then return empty list, do not proceed
      if(!schemaService.isFuzzyAttributeAlgorithmConfigured(fuzzyMatchAttrs)) return [];

      //we are here because all fuzzyMatchAttrs have algorithms configured
      fuzzyMatchAttrs.each() { fuzzyAttr ->

         log.debug("getting algorithm for ${fuzzyAttr} from ${matchTypes.get(fuzzyAttr)}");
         String serviceName = "edu.berkeley.ucic.idmatch."+matchTypes.get(fuzzyAttr).matchType+"Service";
         String distance = matchTypes.get(fuzzyAttr).distance;
         String registryName = schemaMap.get(fuzzyAttr);
         String inputValue = jsonDataMap.get(fuzzyAttr);

         log.debug("doing a match for ${listToMatch.size()} usrs ");
         log.debug("with inputValue = ${inputValue} and registryName = ${registryName}");
         log.debug(" and serviceName = ${serviceName} and distance = ${distance}");
         def  fuzzyService = this.class.classLoader.loadClass(serviceName, true)?.newInstance()
         if(distance == null) { 
            listToMatch =  fuzzyService.findMatches(inputValue,registryName,listToMatch); }
         else{ 
             listToMatch = fuzzyService.findMatches(inputValue,registryName,listToMatch,distance as int);  }
        }
       log.debug("Exit:${method} returns ${listToMatch.size()} users");
       return listToMatch; 
} //getMatchesForEachRule

    
    /**
     * given a blocking filter, get the result set
     * by constructing sql using the attrs in the filter
     */
     def java.util.List getBlockingFilterResults(java.util.Map rule, java.util.Map jsonDataMap){
         def method = "getBlockingFilterResults";
         log.debug("Enter:${method}  for rule ${rule}");
         def blockingFilterAttrs = rule.blockingFilter;
         def schemaMap = configService.getSchemaMap();
         //construct SQL stmt
        def ruleStmt; //empty sql stmt
        for(attr in  blockingFilterAttrs) {
                    log.debug( "working with ${attr} to make sql");

                    if(attr.equals(WILD_CARD)){ 
                      log.debug("Exit:${method} early with all usrs bcs ${attr} is ${WILD_CARD}");
                      return personService.getCache(); }

                    if(!jsonDataMap.containsKey(attr)) {
                       log.debug("Exit:${method} early with empty users bcs ${attr} is missing in jsoninput");
                       return [];
                    }

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
                    log.debug("new ruleStmt ${ruleStmt}");
                } //for each attr loop
      //run blockingFilter sql to get users to run match against
      log.debug("final ruleStmt is ${ruleStmt}");
      def hqlStmt = "from Person where ${ruleStmt}".trim();
      def listToMatch = Person.findAll("${hqlStmt}"); // uses HQL
      log.debug("Exit:${method} with listToMatch size of ${listToMatch.size()}");
      return listToMatch;
     }

} //end class
