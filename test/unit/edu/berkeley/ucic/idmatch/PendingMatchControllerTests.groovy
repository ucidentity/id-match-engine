package edu.berkeley.ucic.idmatch

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import grails.test.mixin.domain.DomainClassUnitTestMixin


/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(PendingMatchController)
@TestMixin(DomainClassUnitTestMixin)
class PendingMatchControllerTests {

    void setUp() {
 
    }

    void tearDown() {
        // Tear down logic here
    }

    /* GET /v1/pendingMatches */
    void testPendingMatchesList() {
       mockDomain(PendingMatch)
       controller.list();
       assert response.status == 200;
    }
    
    /* GET /v1/pendingMatches/:id */
    void testPendingMatchesDetail() {
        this.controller.params.id = '1234';
        this.controller.show();
        assert response.status == 300
    }
 
}
