import org.apache.commons.lang.RandomStringUtils;

import edu.berkeley.ucic.idmatch.Person;

class BootStrap {
  
    def userService;
    def grailsApplication;

    def init = { servletContext ->
        
        log.info(grailsApplication.config.idMatch); 
        //if true then create users
        def createPersons = grailsApplication.config.idMatch.test.createPersons;
        //create users equal to this size
        Integer userCount = grailsApplication.config.idMatch.test.size as int;
 
        log.info("${createPersons} and ${userCount}");

        //only run this if set to true 
        if(createPersons) {        
        }
         //uncomment it for golive
        userService.warmUpCache();
     }
    
    def destroy = {
       
    }
}
