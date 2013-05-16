package edu.berkeley.ucic.idmatch;

import edu.berkeley.ucic.idmatch.User;

class UserService {
 
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

   
    def User create(java.util.Map jsonDataMap){
      log.debug("Enter create");
      def referenceId = jsonDataMap.referenceId;
      def SOR = jsonDataMap.SOR;
      def sorId = jsonDataMap.sorId;
      log.debug("passed in values are "+referenceId+","+SOR+","+sorId);
      def user =  new User();
      user.referenceId = referenceId;
      user.SOR = SOR;
      user.sorId = sorId;
      user.save(flush: true, failOnError : true);
      log.debug("EXIT create with user object "+user);
      return user;
    }

   
}
