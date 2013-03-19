package dolphin;


import dolphin.User;

class UserService {
 
    def users;
    def REFRESH_INTERVAL_TIME = 6*60*60*1000;
    def lastRefreshTime;
    

    def getCache() {
      if(users?.size() == 0) { log.debug("users is null"); warmUpCache(); }
      else {
         def timeSinceRefresh = new Date().getTime() - lastRefreshTime;
         if(timeSinceRefresh >= REFRESH_INTERVAL_TIME) { log.debug("${timeSinceRefresh} ${REFRESH_INTERVAL_TIME}"); renewCache();}
      }
      
      log.debug("User.getAll() size is ${users.size()}");
      log.debug( "User.count() is "+User.count());
      return users;
    }

   /* should we use findAll or getAll */
   def warmUpCache(){
     log.debug("User.getAll called");
      users = User.getAll(); 
      lastRefreshTime = new Date().getTime();
   }

  
  /* should we user findAll or getAll */ 
   def renewCache(){
      log.debug("renewCache called");
       users = User.getAll();
       lastRefreshTime = new Date().getTime();

   }
}
