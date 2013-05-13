package edu.berkeley.ucic.idmatch

import org.junit.*
import grails.test.mixin.domain.DomainClassUnitTestMixin


/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 * Use the grails.test.mixin.TestFor annotation to unit test controllers. 
 * Using TestFor in this manner activates the grails.test.mixin.web.ControllerUnitTestMixin and its associated API.
 * {@link grails.test.mixin.domain.DomainClassUnitTestMixin} A mixin that can be applied to JUnit or 
 * Spock tests to add testing support to a users test classes. Can be used in combination with 
 * ControllerUnitTestMixin to fully test controller interaction with domains without needing a database
 */

@TestFor(CanonicalController)
@TestMixin(DomainClassUnitTestMixin)
class CanonicalControllerUnitTests {

    void setUp() {
    }

    void tearDown() {
        // Tear down logic here
    }

    void testRule1() {
      defineBeans {
        configService(ConfigService)
      }
      println controller.getMatches();
    }

    void testRule2(){}


    void testRule3() {}
}
