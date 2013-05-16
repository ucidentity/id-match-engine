import org.apache.commons.lang.RandomStringUtils;

import edu.berkeley.ucic.idmatch.User;

class BootStrap {
  
    def userService;
    def grailsApplication;

    def init = { servletContext ->
        
        log.info(grailsApplication.config.idMatch); 
        //if true then create users
        def createUsers = grailsApplication.config.idMatch.test.createUsers;
        //create users equal to this size
        Integer userCount = grailsApplication.config.idMatch.test.size as int;
 
        log.info("${createUsers} and ${userCount}");

        //only run this if set to true 
        if(createUsers) {        
        }
         //uncomment it for golive
        userService.warmUpCache();
     }
    
    def destroy = {
       
    }
}
