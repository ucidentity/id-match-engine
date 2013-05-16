package edu.berkeley.ucic.idmatch

//auto marshall XML and JSON
import grails.converters.*; 

/**
 * provides service to interact with People resource
 *
 */
class PeopleController {


    def userService;

    //static  scaffold = true;

    def renewCache(){
       userService.renewCache();
       render "users size in cache is ${userService.people.size()}";
    }


    //GET /v1/SOR/sorId
    def getSorUser(){
        if(!params.SOR && !params.sorId){ render(status : 400, text : "missing required attr"); return; }
        def sorUser = User.findWhere(SOR : params.SOR, sorId : params.sorId);
        if(sorUser == null){ render(status : 404, text : "user not found"); return; }
        render(status : 200, text: sorUser as JSON);

     }

    
    //GET /v1/SOR
    def getSorUsers(){
       if(!params.SOR){ render(status : 400, text : "missing required attr"); return; }
       else {
           def sorUsers = User.findAll { SOR == params.SOR };
           render(status : 300, text : sorUsers as JSON );
       }
     }

    //DELETE /v1/SOR/sorId
    def deleteSorUser(){
        if(!params.SOR || !params.sorId){render(status: 400, text : "missing required attr"); return; }
        else {
          def sorUser = User.findWhere(SOR : params.SOR, sorId : params.sorId);
          if(!sorUser){render(status: 404, text : "user not found"); return};
          sorUser.delete();
        }
     }
    
    //PUT /v1/SOR/sorId 
    //need to have referenceId inorder to create a new entry
    def createOrUpdateSORUser(){
      log.debug("the result of login "+securityService.login(request));
      if(!securityService.login(request)) {render(status : 401, text : "invalid credentials" ); return; }
      log.debug "passed authn";
      def jsonDataMap = JSON.parse(request).data;
      def user = userService.create(jsonDataMap);
      if(user == null) { render(status : 400, text : "Failed to create user"); return;}
      render(status : 200, text : "success fully created "+user.referenceId);
    }


}
