package edu.berkeley.ucic.idmatch;

import edu.berkeley.ucic.idmatch.User;


/**
 * service to manage User
 */
class UserService {

    def configService;
 
    java.util.List  users = [];
    def REFRESH_INTERVAL_TIME = 6*60*60*1000;
    def lastRefreshTime;
    

    def getCache() {
      log.info("Enter getCache");
      if(users.size() == 0) { log.debug("users is null"); warmUpCache(); }
      //the following two stmts are added for testing only, you may remove it
      //i notice a difference in the size and count responses       
      log.info("Exit getCache with  ${users.size()} users"); //TODO: users.size is different from Users.count!!
      return users;
    }

   /* should we use findAll or getAll */
   def warmUpCache(){
     log.info("User.getAll called");
      users = User.getAll(); 
      lastRefreshTime = new Date().getTime();
   }

  
  /* should we user findAll or getAll */ 
   def renewCache(){
      log.info("renewCache called");
       users = User.getAll();
       lastRefreshTime = new Date().getTime();

   }

   def pollForRefresh(){
         def timeSinceRefresh = new Date().getTime() - lastRefreshTime;
         if(timeSinceRefresh >= REFRESH_INTERVAL_TIME) { log.debug("${timeSinceRefresh} ${REFRESH_INTERVAL_TIME}"); renewCache();}
   }

 
    /**
     * creates or updates the User 
     */  
    def User createOrUpdate(String SOR, String sorId, java.util.Map jsonDataMap){
      log.debug("Enter createOrUpdate");
      log.debug("passed in values are "+jsonDataMap+","+SOR+","+sorId);
      def user  = User.findWhere(SOR : SOR, sorId : sorId);
      java.util.Map params = schemaAdapter(jsonDataMap);
      params.SOR = SOR;
      params.sorId = sorId;
      if(user == null) { user =  new User(params); }
      else{ user.properties = params; }
      try { user.save(flush: true, failOnError : true); }catch(e){log.debug(e.getMessage()); user = null; }
      log.debug("EXIT createOrUpdate with user object "+user?.id);
      return user;
    }

    /**
     * converts json input into User map
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


 }
