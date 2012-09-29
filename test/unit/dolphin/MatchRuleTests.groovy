package dolphin



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MatchRule)
class MatchRuleTests {

    void testSomething() {
       
      println "this is println from testSomething in MatchRuleTests"
      def m = new MatchRule(attribute: "attr", score: "30", algorithm: "Soundex");
      //def m = new MatchRule();
      if(!m.validate()){
       m.errors.each{println it} 
      }
      if( !m.save() ) {
      m.errors.each {
        println it
   }
   }
     // assertNotNull(m.save(flush: true));
      
    }
}
