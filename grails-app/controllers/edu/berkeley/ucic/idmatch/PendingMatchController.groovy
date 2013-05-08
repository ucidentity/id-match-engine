package edu.berkeley.ucic.idmatch

//auto marshalling of JSON
import grails.converters.JSON;

/**
 * controller to manage pendingMatch resource 
 * pendingMatch captures the match request as placed in point in time
 * pendingMatch will not the result set for the given match input
 */
class PendingMatchController {

    static scaffold = true
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
     * return 400 if params.id missing
     */
    def show() {
        println "here is the id sent as params.id $params.id"
        if(!params.id) render (status : 400, text : "missing required input params.id" );
        def p =  PendingMatch.get(params.id);
        if(p == null) render(status : 404, text : "resource with $params.id not found" )
        else render(status : 300, text : p as JSON);
    }

    /*
     * PUT /v1/pendingMatch
     * create PendingMatch and return status 200
     */
    def create() {
      //get json payload, assign it to requestJsonVal;
      def requestJsonVal = [];
      def p = new PendingMatch(SOR : params.sor, sorId : params.sorId, requestJson: [], lastRunTimeStamp: new Date());
      render(status : 200, id: p.id);
    }
   
}
