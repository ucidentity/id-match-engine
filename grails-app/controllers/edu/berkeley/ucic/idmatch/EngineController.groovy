package edu.berkeley.ucic.idmatch; 

import grails.converters.JSON;
import edu.berkeley.ucic.idmatch.*;

class EngineController {

    //TODO: remove index after testing 
    static allowedMethods = [getMatches:'GET',
                             reconcile:'PUT',
                             index:['GET', 'POST']]


    edu.berkeley.ucic.idmatch.SecurityService securityService;
    edu.berkeley.ucic.idmatch.FuzzyMatchService  fuzzyMatchService;
    edu.berkeley.ucic.idmatch.CanonicalMatchService  canonicalMatchService;
    UserService userService;

    def index() { render "USAGE: GET:getMatches, PUT:reconcileNow" }
  
    /*
     * return canonical and fuzzy matches
     * first check canonical matches, if no canonical matches found,
     * then do a fuzzy match
     * return 401 if un-authenticated (403 if un-authz )
     */ 
    def getMatches() {
   
      if(securityService.login(request) == false) render(status: 401, text: "Authentication failed");
      java.util.Map jsonDataMap = JSON.parse(request).data;
      if(jsonDataMap == null) render(status: 400, text:"Bad Request: json request payload is empty");
      else println "proceed further"
      //run matches if request has json payload
         java.util.List canonicalMatches = canonicalMatchService.getMatches(jsonDataMap);
         if(canonicalMatches.size() > 0) render(status: 300, results: canonicalMatches);
         def fuzzyMatches = fuzzyMatchService.getMatches(jsonDataMap);
         if(fuzzyMatches.size() == 0) render(status: 404);
     } 


     /**
      * if a PUT request comes through, then add the user in the request
      */
     def reconcile() {

      if(securityService.login(request) == false) render(status: 401, text: "Authentication failed");
      java.util.Map jsonDataMap = JSON.parse(request).data;
      if(jsonDataMap == null) render(status: 400, text:"Bad Request: json request payload is empty");
      //if authn passed, and payload found, proceed to create the user
      println userService.create(jsonDataMap); 
      render(status: 200, text: "added user successfully");
     }


    
}
