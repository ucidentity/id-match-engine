package edu.berkeley.ucic.idmatch;

import edu.berkeley.ucic.idmatch.Person;


/**
 * service to manage Person
 */
class PersonService {

    def configService;
 
    java.util.List  persons = [];
    def REFRESH_INTERVAL_TIME = 6*60*60*1000;
    def lastRefreshTime;
    

    def getCache() {
      log.info("Enter getCache");
      if(persons.size() == 0) { log.debug("persons is null"); warmUpCache(); }
      //the following two stmts are added for testing only, you may remove it
      //i notice a difference in the size and count responses       
      log.info("Exit getCache with  ${persons.size()} persons"); //TODO: persons.size is different from Persons.count!!
      return persons;
    }

   /* should we use findAll or getAll */
   def warmUpCache(){
     log.info("Person.getAll called");
      persons = Person.getAll(); 
      lastRefreshTime = new Date().getTime();
   }

  
  /* should we person findAll or getAll */ 
   def renewCache(){
      log.info("renewCache called");
       persons = Person.getAll();
       lastRefreshTime = new Date().getTime();

   }

   def pollForRefresh(){
         def timeSinceRefresh = new Date().getTime() - lastRefreshTime;
         if(timeSinceRefresh >= REFRESH_INTERVAL_TIME) { log.debug("${timeSinceRefresh} ${REFRESH_INTERVAL_TIME}"); renewCache();}
   }

 
    /**
     * creates or updates the Person 
     */  
    def Person createOrUpdate(String SOR, String sorId, java.util.Map jsonDataMap){
      log.debug("Enter createOrUpdate");
      log.debug("passed in values are "+jsonDataMap+","+SOR+","+sorId);
      def person  = getPerson(SOR,sorId);
      //the following takes keys from json,transforms them to registry column names
      java.util.Map newParams = schemaAdapter(jsonDataMap);
      if(person == null) { newParams.SOR = SOR; newParams.sorId = sorId; person =  new Person(newParams); }
      else{ person.properties = newParams; }
      try { person.save(flush: true, failOnError : true); }catch(e){log.debug(e.getMessage()); person = null; }
      log.debug("EXIT createOrUpdate with person object "+person?.id);
      return person;
    }

    /**
     * converts json input into Person map
     * based on the config.schemaMap in config file
     */
    def java.util.Map schemaAdapter(java.util.Map jsonDataMap){
        log.debug("Enter: schemaAdapter");
        java.util.Map params = [:];
        def schemaMap = configService.getSchemaMap();
        schemaMap.each(){ key,value ->
             log.debug("key value in schemaMap are "+key+":"+value);
             if(jsonDataMap.has(key)) params."${value}" = jsonDataMap.get(key);                
             log.debug("params.value is "+value+" and "+params."${value}");
        }
        return params;
      }

     /**
      * get a person for a given SOR and sorId
      */
      def Person getPerson(String SOR, String sorId){
         log.debug("Enter: getPerson with SOR and sorId set to"+SOR+"-"+sorId);
         java.util.Map queryParams = [:];
         queryParams.SOR = SOR;
         queryParams.sorId = sorId;
         log.debug("queryParams is "+queryParams);
         def person  = Person.findWhere(queryParams);
         log.debug("Exit: getPerson with person "+person);
         return person;

       }


 }
