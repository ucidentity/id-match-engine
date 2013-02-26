package dolphin

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;

import groovy.transform.Synchronized;

class FuzzyMatchService {
 
  def grailsApplication;

  //get this from configuration
  private static final int NTHREDS = 8;//grailsApplication.config.idMatch.THREADS;
  //static scope = "request";
  def transpositionService;
  def editDistanceService;
  def soundexService;
  def userService;
  //def users = User.list();



  def getTransposeMatches(String input) {
    log.debug("Enter");
      AtomicInteger atomicCounter = new AtomicInteger();
      ConcurrentHashMap atomicMap = new ConcurrentHashMap();
      atomicMap.clear();
      ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
      //def users = userService.getCache();
      def start =  new Date();
      userService.getCache().each { user -> 
      //anonymous runnable
      Runnable worker = new Runnable(){
          public void run() {
            def i = atomicCounter.incrementAndGet(); 
            log.debug("calling run for "+i+" times");
            def compareResult = transpositionService.compare(input, user.attr2);
            if(compareResult) {
               log.debug("adding user to matchList");
               atomicMap.putIfAbsent(user.id,user); 
            }
          } //run()
      } //Runnable{}
      executor.execute(worker); //add this runnable to the executor service
   }
    
    // This will make the executor accept no new threads
    // and finish all existing threads in the queue
    executor.shutdown();
    // Wait until all threads are finished
    while (!executor.isTerminated()) {}
    def end = new Date();
def timeTaken = (end.getTime() - start.getTime())/1000;
    log.debug("Exit");
    return "Transpose for ${atomicCounter.intValue()} out of  ${User.count} started at ${start} ,and took ${timeTaken} with results ${atomicMap.size()} and ${atomicMap} "
   
  }


 /*
  * Edit Distance
  */
  def getEditDistanceMatches(String input) {
    log.debug("Enter");
      AtomicInteger atomicCounter = new AtomicInteger();
      ConcurrentHashMap atomicMap = new ConcurrentHashMap();
      atomicMap.clear();
      ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
      //def users = userService.getCache();
      def start =  new Date();
      userService.getCache().each { user ->
      //anonymous runnable
      Runnable worker = new Runnable(){
          public void run() {
            def i =  atomicCounter.incrementAndGet();
            def compareResult;
            log.debug("calling run for "+i+" times");
            compareResult = editDistanceService.compare(input, user.attr2,"1");
            if(compareResult) {
               log.debug("adding user to matchList");
               atomicMap.putIfAbsent(user.id,user);
            }
          } //run()
      } //Runnable{}
      executor.execute(worker); //add this runnable to the executor service
   }
   
    // This will make the executor accept no new threads
    // and finish all existing threads in the queue
    executor.shutdown();
    // Wait until all threads are finished
    while (!executor.isTerminated()) {}
    def end = new Date();
    def timeTaken = (end.getTime() - start.getTime())/1000;
    log.debug("Exit");
    return "Edit for ${atomicCounter.intValue()} out of  ${User.count} started at ${start} ,and took ${timeTaken} with results ${atomicMap.size()} and ${atomicMap} "
  
  }



  def getSoundexMatches(String input) {
    log.debug("Enter");
      AtomicInteger atomicCounter = new AtomicInteger();
      ConcurrentHashMap atomicMap = new ConcurrentHashMap();
      atomicMap.clear();
      ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
      //def users = userService.getCache();
      def start =  new Date();
      userService.getCache().each { user ->
      //anonymous runnable
      Runnable worker = new Runnable(){
          public void run() {
            def i = atomicCounter.incrementAndGet();
            def compareResult;
            log.debug("calling run for "+i+" times");
            compareResult = soundexService.compare(input,user.attr2);
            if(compareResult) {
               log.debug("adding ${user.attr2} to matchList");
               atomicMap.putIfAbsent(user.id,user);
            }
          } //run()
      } //Runnable{}
      executor.execute(worker); //add this runnable to the executor service
   }
  
    // This will make the executor accept no new threads
    // and finish all existing threads in the queue
    executor.shutdown();
    // Wait until all threads are finished
    while (!executor.isTerminated()) {}
    def end = new Date();
    log.debug("Exit");
def timeTaken = (end.getTime() - start.getTime())/1000;
    log.debug("Exit");
    return "Soundex for ${atomicCounter.intValue()} out of  ${User.count} started at ${start} ,and took ${timeTaken} with results ${atomicMap.size()} and ${atomicMap} "
 
  }


  def getMatchesByRule(String input){
     log.debug("Enter");
      AtomicInteger atomicCounter = new AtomicInteger();
      ConcurrentHashMap atomicMap = new ConcurrentHashMap();
      atomicMap.clear();
      ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
      def users = userService.getCache();
      def start =  new Date();
      users.each { user ->
      //anonymous runnable
      Runnable worker = new Runnable(){
          public void run() {
            def i = atomicCounter.incrementAndGet();
            log.debug("calling run for "+i+" times");
            def soundexResult = soundexService.compare(input, user.attr2);
            def transposeResult = transpositionService.compare(input,user.attr2);
            def editDistanceResult = editDistanceService.compare(input,user.attr2,"1");
            if(soundexResult&&transposeResult&&editDistanceResult) {
               log.debug("adding user to matchList");
               atomicMap.putIfAbsent(user.id,user);
            }
          } //run()
      } //Runnable{}
      executor.execute(worker); //add this runnable to the executor service
   }

    // This will make the executor accept no new threads
    // and finish all existing threads in the queue
    executor.shutdown();
    // Wait until all threads are finished
    while (!executor.isTerminated()) {}
    def end = new Date();
    log.debug("Exit");
def timeTaken = (end.getTime() - start.getTime())/1000;
    log.debug("Exit");
    return "All 3 matches for ${atomicCounter.intValue()} out of  ${User.count} started at ${start} ,and took ${timeTaken} with results ${atomicMap.size()} and ${atomicMap} "
    }


    /* 
     * run fuzzy Match rules
     * this is deprecated as FuzzyRuleConfiguration is changed
     */
    def executeRulesV1(java.util.Map jsonDataMap) {
      println "json map is "+ jsonDataMap;
      def rules = grailsApplication.config.idMatch.ruleSet;
      println "rules is "+rules;
      def ruleKeySet = rules.keySet(); //get keys for the rules
      def schemaMap = grailsApplication.config.idMatch.schemaMap;
      println "schemaMap is "+schemaMap;
      def cutOffScoreMap = grailsApplication.config.idMatch.cutOffScoreMap;
      println "cutOffScoreMap is "+cutOffScoreMap;
      def exactCutOffScore = cutOffScoreMap.get("exact") as int;
      def reconCutOffScore = cutOffScoreMap.get("recon") as int;
      def jsonDataMapKey = jsonDataMap.keySet();
      println "json data key is "+jsonDataMapKey;
      def exactResults = [];
      def reconResults = [];
      //

      //get all persons in the registry
      println "pre-fetch "+new Date();
      def persons = userService.getCache(); //return users from the cache
      println "post fetch "+new Date();
      //for each person in the person list
      persons.each(){ person ->
            println "person is "+person;
            def personMatchScore = 0;
            def personProfile = [:];
            //for each attribute in jsonDataMap
            jsonDataMapKey.each() { ruleKey ->
             println "rule Key is " + ruleKey;
             def jsonDataValue = jsonDataMap.get(ruleKey).toString(); //get value from json
             println "json data value is "+jsonDataValue;
             def dbColName = schemaMap.get(ruleKey); //get real col name from schema map
             println "schemaMap value for ruleKey is ${dbColName}";
             def registryValue = person."${dbColName}";
             println "person col value for this key is ${registryValue}";
             def ruleConfigMap = rules.get(ruleKey); //get the rule configuration for a given key
             println "rule config is "+ruleConfigMap;
             if(ruleConfigMap != null){
             def ruleScore = matchingService.executeRule(ruleConfigMap, jsonDataValue,registryValue);
             println "ruleScore is "+ruleScore;
             personMatchScore = personMatchScore + ruleScore;
             personProfile.put(ruleKey, registryValue)};

         }


            println "personMatchScore is "+personMatchScore;
             if (personMatchScore == exactCutOffScore.intValue() ) {
                personProfile.put("person", person);
                personProfile.put("personMatchScore", personMatchScore);
                exactResults.add(personProfile);
                println "exact match results are "+exactResults;
             }
             else if ((personMatchScore >= reconCutOffScore.intValue())
                       &&
                       (personMatchScore <  exactCutOffScore.intValue() )
                      )
                     {  personProfile.put("person", person);
                        personProfile.put("personMatchScore", personMatchScore);
                        reconResults.add(personProfile);
                        println "recon match results are "+reconResults;
                     }
      }
       def response = [:];
       response.put("input", jsonDataMap)
       response.put("exact" , exactResults);
       response.put("recon" , reconResults);
       println "Date match complete "+new Date();
       return response;

     }


    /*
     * newer version of rule execution
     * based on newer configuration for fuzzyRules
     */
    def executeRules(java.util.Map jsonDataMap){

     //first only pick one rule, cant run more than one rule as it will be a big performance hit
     //use confidence value to sort and pick the most relevant rule, if more than one rule is qualified to be run
     //make sure all the attributes in the rule have value in the request json payload, if not skip this rule
     //once you have the rule to run, run the blocking filter assigned to that rule, this reduces the number or users to run the match against

    }
    
    def blockingFilter(String filterName){
     //construct the sql stmt based on the configuration of the blocking filter 
      

    }

}
