package edu.berkeley.ucic.idmatch

//auto marshall XML and JSON
import grails.converters.*; 

/**
 * provides service to interact with People resource
 *
 */
class PersonController {


    def personService;
    def securityService;

    String http401Message = "Invalid Credentials";
    String http400Message = "Missing Required Params";
    String http404Message = "Requested Resource not found";

    //static  scaffold = true;

    def renewCache(){
       personService.renewCache();
       render "persons size in cache is ${personService.people.size()}";
    }


    //GET /v1/SOR/sorId
    def getSorUser(){
        if(!securityService.login(request)) {render(status : 401, text : http401Message ); return; } 
        if(!params.SOR && !params.sorId){ render(status : 400, text : http400Message); return; }
        log.debug("found SOR and sorId in request "+params.SOR+" and "+params.sorId);
        def sorUser = personService.getPerson(params.SOR, params.sorId);
        if(sorUser == null){ render(status : 404, text : http404Message); return; }
        render(status : 200, text: sorUser as JSON);

     }

    
    //GET /v1/SOR
    def getSorUsers(){
       if(!securityService.login(request)) {render(status : 401, text : http401Message ); return; }
       if(!params.SOR){ render(status : 400, text : http400Message); return; }
       else {
           log.debug("found SOR "+params.SOR);
           def sorUsers = Person.findAll { SOR == params.SOR };
           render(status : 300, text : sorUsers as JSON );
       }
     }

    //DELETE /v1/SOR/sorId
    def deleteSorUser(){
        if(!securityService.login(request)) {render(status : 401, text : http401Message ); return; }
        if(!params.SOR || !params.sorId){render(status: 400, text : http400Message); return; }
        else {
          def sorUser = Person.findWhere(SOR : params.SOR, sorId : params.sorId);
          if(!sorUser){render(status: 404, text : http404Message); return};
          sorUser.delete();
        }
     }
    
    //PUT /v1/SOR/sorId 
    //Note: need to have referenceId inorder to create/update
    def createOrUpdate(){
      if(!securityService.login(request)) {render(status : 401, text : http401Message ); return; }
      log.debug "passed authn";
      def jsonDataMap = JSON.parse(request);
      //need referenceId, SOR and sorId to create or update
      if(!jsonDataMap.containsKey("referenceId") || params.SOR==null || params.sorId == null) {render(status: 400, text : http400Message); return; }
      def person = personService.createOrUpdate(params.SOR,params.sorId,jsonDataMap);
      if(person == null) { render(status : 400, text : "Failed to create person"); return;}
      render(status : 200, text : "success fully created "+person.referenceId);
    }


}
