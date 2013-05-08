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
       assert response.getJson() == [];
    }
    
    /* GET /v1/pendingMatches/:id */
    void testPendingMatchesDetail() {

        def p1 = new PendingMatch(SOR : '123', sorId  : '123', requestJson : '{}', lastRunTimeStamp : new Date());
        def p2 = new PendingMatch(SOR : '123', sorId  : '124', requestJson : '{}', lastRunTimeStamp : new Date());
        mockDomain(PendingMatch, [p1,p2]);
        this.controller.params.id = '1234';
        this.controller.show();
        assert response.status == 300;
        //assert response.getJson() == [];
    }
 
}
