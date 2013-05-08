package edu.berkeley.ucic.idmatch

//auto marshalling of JSON
import grails.converters.JSON;

/**
 * controller to manage pendingMatch domain object
 */
class PendingMatchController {

    //static scaffold = true
    def index() { forward action: "list" }
   
    /*
     * GET /v1/pendingMatches
     * should return 200
     */ 
    def list() {
        render(status: 200, text :  PendingMatch.findAll() as JSON);
    }

    /*
     * GET /v1/pendingMatches/$id
     * should return 300
     */
    def show() {
        def p =  PendingMatch.get(1);
        render(status : 300, text : p as JSON )
    }

    /*
     * PUT /v1/pendingMatch
     * create PendingMatch and return status 200
     */
    def create() {
      def p = new PendingMatch(SOR : params.sor, sorId : params.sorId, requestJson: "{}", lastRunTimeStamp: new Date());
      render(status : 200, id: p.id);
    }
   
}
