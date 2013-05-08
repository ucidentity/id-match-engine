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
      def p1 = new PendingMatch(SOR : '123', sorId  : '123', requestJson : '{}', lastRunTimeStamp : new Date());
      def p2 = new PendingMatch(SOR : '123', sorId  : '124', requestJson : '{}', lastRunTimeStamp : new Date());
      mockDomain(PendingMatch, [p1,p2]);
       controller.list();
       assert response.status == 200;
       assert response.getJson();
    }
    
    /* GET /v1/pendingMatches/:id */
    void testPendingMatchesDetail() {
       
        def date = new Date(); 
        def p1 = new PendingMatch(SOR : '123', sorId  : '123', requestJson : '{}', lastRunTimeStamp : date);
        def p2 = new PendingMatch(SOR : '123', sorId  : '124', requestJson : '{}', lastRunTimeStamp : date);
        mockDomain(PendingMatch, [p1,p2]);
        controller.params.id = '1';
        controller.show();
        assert response.status == 300;
        assertTrue response.text.contains("123"); 

    }

    
    /* GET /v1/pendingMatches/:id */
    void testPendingMatchesDetailForMissingId() {

        def p1 = new PendingMatch(SOR : '123', sorId  : '123', requestJson : '{}', lastRunTimeStamp : new Date());
        def p2 = new PendingMatch(SOR : '123', sorId  : '124', requestJson : '{}', lastRunTimeStamp : new Date());
        mockDomain(PendingMatch, [p1,p2]);
        controller.params.id = '11';
        controller.show();
        assert response.status == 404;
        assertFalse response.text.contains("SOR");

    }


 
}
