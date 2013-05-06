package edu.berkeley.ucic.idmatch

//auto marshalling of JSON
import grails.convertors.JSON;

/**
 * controller to manage pendingMatch domain object
 */
class PendingMatchController {

    //static scaffold = true
    def index() { forward action: "list" }
   
    /*
     * GET /v1/pendingMatches
     */ 
    def list() {
        render(status: 300, text :  PendingMatch.list() as JSON);
    }

    /*
     * GET /v1/pendingMatches/$id
     */
    def show() {
        def p = PendingMatch.get(params.id)
        render(status : 200, text : p as JSON )
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
