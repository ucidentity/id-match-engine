import org.apache.commons.lang.RandomStringUtils;

class BootStrap {
  
    def userService;

    def init = { servletContext ->
        
         
        //only run this if set to true 
        if(true) {        
        //dont delete unless u want to reduce or existing user count
        //def users = dolphin.User.list();
        //users.each{ it.delete(flush: true) } 
        long start = new Date().getTime();
        200000.times { i ->
            def ssn = RandomStringUtils.random(9,"0123456789");
            def dob = RandomStringUtils.random(8,"0123456789"); 
            def fname = RandomStringUtils.random(5, "ABCDEFGHIJKLMNOPGRSTUVXYZ");
            def lname = RandomStringUtils.random(5,"ABCDEFGHIJKLMNOPGRSTUVXYZ");
            def city = RandomStringUtils.random(8,"ABCDEFGHIJKLMNOPGRSTUVXYZ");
            new dolphin.User(attr1: ssn,
                             attr2: lname, 
                             attr3: fname, 
                             attr4 : dob, 
                             attr5: city ).save()

        }
        long end = new Date().getTime();
        println "created ${dolphin.User.count} in ${end-start}";
        }
        userService.warmUpCache();
     }
    
    def destroy = {
    }
}
