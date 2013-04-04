import org.apache.commons.lang.RandomStringUtils;

import edu.berkeley.ucic.idmatch.User;
import edu.berkeley.ucic.idmatch.Person;
import edu.berkeley.ucic.idmatch.Attribute;

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
        //dont delete unless u want to reduce or existing user count
        //def users = edu.berkeley.ucic.idmatch.User.list();
        //users.each{ it.delete(flush: true) } 
        long start = new Date().getTime();
        userCount.intValue().times { i ->
            def ssn = RandomStringUtils.random(9,"0123456789");
            def dob = RandomStringUtils.random(8,"0123456789"); 
            def fname = RandomStringUtils.random(5, "ABCDEFGHIJKLMNOPGRSTUVXYZ");
            def lname = RandomStringUtils.random(5,"ABCDEFGHIJKLMNOPGRSTUVXYZ");
            def city = RandomStringUtils.random(8,"ABCDEFGHIJKLMNOPGRSTUVXYZ");
            new edu.berkeley.ucic.idmatch.User(attr1: ssn,
                             attr2: lname, 
                             attr3: fname, 
                             attr4 : dob, 
                             attr5: city ).save(failOnError: true);
            def person = new edu.berkeley.ucic.idmatch.Person();
            person.addToAttributes(new Attribute(name: ssn, value: ssn));
            person.addToAttributes(new Attribute(name: dob, value: dob));
            
            person.save(failOnError: true);

        }
        
        long end = new Date().getTime();
        log.info( "created ${userCount} in ${end-start}");
        log.info( "total users in db now is ${edu.berkeley.ucic.idmatch.User.count()}")
        }
        userService.warmUpCache();
     }
    
    def destroy = {
       
    }
}
