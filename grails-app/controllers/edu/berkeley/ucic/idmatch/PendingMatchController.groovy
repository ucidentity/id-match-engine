package edu.berkeley.ucic.idmatch

//auto marshalling of JSON
import grails.converters.JSON;

/**
 * controller to manage pendingMatch resource 
 * pendingMatch captures the match request as placed in point in time
 * pendingMatch will not the result set for the given match input
 */
class PendingMatchController {
 
    def pendingMatchService;
    def securityService;
    
    String http401Message = "Invalid Credentials";
    String http400Message = "Missing request params";
    String http404Message = "Request Resource not found";

    static scaffold = true
    def index() { forward action: "list" }
   
    /*
     * GET /v1/pendingMatches
     * should return 200
     */ 
    def list() {
      if(!securityService.login(request)) {render(status : 401, text : http401Message); return; }
        render(status: 200, text :  PendingMatch.findAll() as JSON);
    }

    /*
     * GET /v1/pendingMatches/$id
     * should return 300
     * return 400 if params.id missing
     * TODO: remove class and other non-user friendly properties from JSON
     */
    def show() {
       log.debug("Enter: show");
      if(!securityService.login(request)) {render(status : 401, text : http401Message); return; }
        if(!params.id) render (status : 400, text : http400Message );
        def p =  pendingMatchService.get(params.id);
        if(p == null){ render(status : 404, text : http404Message ); return; }
        log.debug("Exit: show with ${p.toString()}");
        render(status : 300, text : p as JSON);
    }

    /*
     * PUT /v1/pendingMatch
     * create PendingMatch and return status 200
     */
    def createOrUpdate() {
      log.debug("Enter: createOrUpdate with payload ${JSON.parse(request)}");
      //get json payload, assign it to requestJsonVal;
      if(!securityService.login(request)) {render(status : 401, text : http401Message); return; }
      def jsonDataMap = JSON.parse(request); 
      //need SOR and sorId to create/update a pendingMatch entry
      if(jsonDataMap.SOR == null || jsonDataMap.sorId == null) {
        log.debug("missing one of SOR, sorId "+jsonDataMap);
        render(status : 400, text : http400Message); return;
      }
      def p = pendingMatchService.createOrUpdate(jsonDataMap);
      if(p == null){ render(status : 400, text: "Create/Update Failed"); return;
      }
      render(status : 200, text : "Success: for ${p.id}");
    }
   
}
