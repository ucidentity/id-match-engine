import org.apache.commons.lang.RandomStringUtils;

class BootStrap {

    def init = { servletContext ->
        
        //def users = dolphin.User.list();
        //users.each{ it.delete(flush: true) } 
        1.times { i ->
            def ssn = RandomStringUtils.random(9,"0123456789");
            def dob = RandomStringUtils.random(8,"0123456789"); 
            def fname = RandomStringUtils.random(5, "ABCDEFGHIJKLMNOPGRSTUVXYZ");
            def lname = RandomStringUtils.random(5,"ABCDEFGHIJKLMNOPGRSTUVXYZ");
            def city = RandomStringUtils.random(8,"ABCDEFGHIJKLMNOPGRSTUVXYZ");
            new dolphin.User(attr1: ssn, attr2: lname, attr3: fname, attr4 : dob, attr5: city ).save()

        }
        println dolphin.User.count;
     }
    
    def destroy = {
    }
}
