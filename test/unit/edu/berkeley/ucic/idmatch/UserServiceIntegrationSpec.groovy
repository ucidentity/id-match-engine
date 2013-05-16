package edu.berkeley.ucic.idmatch



import spock.lang.*
import grails.plugin.spock.IntegrationSpec;
import grails.test.mixin.*;
import edu.berkeley.ucic.idmatch.*;
import spock.lang.Specification;

@Mock(User)
class UserServiceIntegrationSpec extends Specification {

   /* 
   */
    def userService = new UserService();


    //FIXTURE METHODS
    def setupSpec() {} //run before the first feature method
    def cleanupSpec() {} //run after the last feature method

    def setup() {}  //run before every feature method
    def cleanup() {} //run after every feature method
  
    //FEATURE METHODS

    /* spec-1 */
    def "create user that should pass"(){

    setup: 
      //doing nothing in setup

    when:
      def result = userService.create(jsonDataMap);

    then:
     result.referenceId == '123456789'; 

    where:
      jsonDataMap = ['referenceId' : '123456789', 'SOR' : 'hr', 'sorId' : '1234'];

    //cleanup: 
    //close db connection?
    }


   /* spec-1 */
    def "create user that should fail"(){

    setup:
      //doing nothing in setup

    when:
      def result = userService.create(jsonDataMap);

    then:
     result.referenceId == '12345679';

    where:
      jsonDataMap = ['referenceId' : '123456789', 'SOR' : 'hr', 'sorId' : '1234'];

    //cleanup:
    //close db connection?
    }

}
