package edu.berkeley.ucic.idmatch;

import edu.berkeley.ucic.idmatch.Person;
import org.apache.commons.lang.RandomStringUtils;


/**
 * service to manage Person
 */
class PersonService {

    def configService;
    def schemaService;
 
    java.util.List  persons = [];
    def REFRESH_INTERVAL_TIME = 6*60*60*1000;
    def lastRefreshTime;
    

    def getCache() {
      String method = "getCache";
      log.info("Enter : ${method}");
      if(persons.size() == 0) { log.debug("persons is null"); warmUpCache(); }
      //the following two stmts are added for testing only, you may remove it
      //i notice a difference in the size and count responses       
      log.info("Exit: ${method} with  ${persons.size()} persons"); //TODO: persons.size is different from Persons.count!!
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
      * get a person for a given SOR and sorId
      */
      def Person getPerson(String SOR, String sorId){
         log.debug("Enter: getPerson with SOR and sorId set to"+SOR+"-"+sorId);
         def result = [:];
         java.util.Map queryParams = [:];
         queryParams.SOR = SOR;
         queryParams.sorId = sorId;
         log.debug("queryParams is "+queryParams);
         def person  = Person.findWhere(queryParams);
         if(person != null ) result = schemaService.personFriendlySchemaAdapter(person);
         log.debug(person);
         log.debug("Exit: getPerson with person "+result);
         return result;
       }

    /**
     * creates or updates the Person 
     */  
    def Person createOrUpdate(String SOR, String sorId, java.util.Map jsonDataMap){
      String method = "createOrUpdate";
      log.debug("Enter: ${method} with passed in values as ${jsonDataMap} ${SOR} ${sorId}");
      java.util.Map queryParams = [:];
         queryParams.SOR = SOR;
         queryParams.sorId = sorId;
         log.debug("queryParams is "+queryParams);
         def person  = Person.findWhere(queryParams);

      if(person == null) { 
           log.debug("Creating new user");
           java.util.Map newParams = schemaService.personRegistrySchemaAdapter(jsonDataMap);
            def incomingReferenceId = jsonDataMap.get("referenceId");
            if((incomingReferenceId == null) ||(incomingReferenceId.contains("New"))){
                newParams.referenceId = RandomStringUtils.randomNumeric(3);
            }else{ 
           newParams.referenceId = jsonDataMap.get("referenceId"); } 
           newParams.referenceId = RandomStringUtils.randomNumeric(10); 
           newParams.SOR = SOR; 
           newParams.sorId = sorId; 
           person =  new Person(newParams);}
      else{  
            log.debug("Updating a user");
            java.util.Map newParams = schemaService.personRegistrySchemaAdapter(jsonDataMap);
            newParams.referenceId = jsonDataMap.get("referenceId"); 
            person.properties = newParams; }
      try { 
           person.save(flush: true, failOnError : true); }
      catch(e){
           log.debug(e.getMessage()); person = null; }
      log.debug("EXIT: ${method} with person id "+person?.id);
      return person;
    }

     
     def deleteAll(){
         Person.executeUpdate("delete FROM  Person")
     }
 }
