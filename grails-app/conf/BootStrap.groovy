import org.apache.commons.lang.RandomStringUtils;

class BootStrap {

    def init = { servletContext ->
        
        def users = dolphin.User.list();
        users.each{ it.delete(flush: true) } 
        1.times { i ->
            def ssn = RandomStringUtils.random(9,"0123456789");
            def dob = RandomStringUtils.random(8,"0123456789"); 
            println "${ssn}  and ${dob}";
            def newChar = (char)i;
            def fname = RandomStringUtils.random(5, "ABCDEFGHIJKLMNOPGRSTUVXYZ");
            def lname = RandomStringUtils.random(5,"ABCDEFGHIJKLMNOPGRSTUVXYZ");
            println fname + "-"+lname;
            new dolphin.User(attr1: ssn, attr2: lname, attr3: fname, attr4 : dob ).save()

        }
        println dolphin.User.count;
     }
    
    def destroy = {
    }
}
