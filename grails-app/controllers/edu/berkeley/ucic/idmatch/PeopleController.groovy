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

        if(!params.SOR) render (status : 400, text : "missing required attr" );
        def sorUser = People.findWhere(attr3 : SOR, attr2 : sorId);
        render(status : 200, sorUser as JSON);

     }

    
    //GET /v1/SOR
    def getSorUsers(){
       if(!params.SOR) render (status : 400, text : "missing required attr" );
       else {
           def sorUsers = People.findAll { attr3 == params.SOR };
           render(status : 300, sorUsers as JSON );
       }
     }

    //PUT /v1/SOR/sorId
    def updateSorUser(){
      if(!params.SOR || !params.sorId ) render(status : 400, text : "missing required attr" );
      else{
           def sorUser = People.findWhere(attr3 : SOR, attr2 : sorId);
           if(!sorUser) render(status : 404);
           else{ 
                sorUser.save(); //TODO
                render (status : 200);}
      }
 
     }

    //DELETE /v1/SOR/sorId
    def deleteSorUser(){
        if(!params.SOR || !params.sorId) render(status: 400, text : "missing required attr");
        else {
          def sorUser = People.findWhere(attr3 : params.SOR, attr2 : params.sorId);
          if(!sorUser) render(status: 404);
          sorUser.delete();
        }
     }
    def createSorUser(){}
}
