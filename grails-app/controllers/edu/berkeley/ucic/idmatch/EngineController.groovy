package edu.berkeley.ucic.idmatch; 

import grails.converters.JSON;
import edu.berkeley.ucic.idmatch.*;


/**
 * primary API of this application
 * expose API to run matches, both canonical and fuzzy
 */
class EngineController {

    //TODO: see allowedMethods annotation

    def securityService;
    def fuzzyMatchService;
    def canonicalMatchService;
    def personService;
    def schemaService;
    def grailsApplication;

    String http401Message = "Invalid Credentials";
    String http400Message = "Missing json payload";
    String http404Message = "Requested Resource Not Found";

    //def index() { render "USAGE: GET:/v1/engine, GET:v1/engine/canonical, GET:v1/engine/fuzzy" }
  
    /*
     * return canonical and fuzzy matches
     * first check canonical matches, if no canonical matches found,
     * then do a fuzzy match
     * return 401 if un-authenticated (403 if un-authz )
     */ 
    def findMatches() {
      String method = "findMatches";
      log.debug("Enter: ${method}"); 
      if(securityService.login(request) == false){
          render(status: 401, text: http401Message); return;}
      java.util.Map jsonDataMap = JSON.parse(request);
      if(jsonDataMap == null){ 
         render(status: 400, text: http400Message); return;}
      def SOR = jsonDataMap.get("SOR");
      def sorId = jsonDataMap.get("sorId");
      java.util.Map queryParams = [:];
      queryParams.SOR = SOR;
      queryParams.sorId = sorId;
      def p = Person.findWhere(queryParams);
      if(p != null){ render(status: 200, text : p as JSON ); return; }
      //go here only if person not found for SOR/sorId
         java.util.List canonicalMatches = canonicalMatchService.getMatches(jsonDataMap);
         if(canonicalMatches.size() > 0) {
              log.debug("Exit: ${method} with ${canonicalMatches.size()} canonical matches");
              render(status: 300, text: canonicalMatches.toSet() as JSON); 
              return;};
         def fuzzyMatches = fuzzyMatchService.getMatches(jsonDataMap);
         if(fuzzyMatches.size() == 0){
              render(status: 404, text : http404Message); return;}
         log.debug("Exit: ${method} with ${fuzzyMatches.size()} fuzzy matches");
         render(status: 300, text: fuzzyMatches.toSet() as JSON);
     } 



    /**
     * only returns canonical matches
     */
    def findCanonicalMatches() {
      log.debug("Enter: findCanonicalMatches");
      if(securityService.login(request) == false){
         render(status: 401, text: http401Message); return;}
      java.util.Map jsonDataMap = JSON.parse(request);
      if(jsonDataMap == null){ 
         render(status: 400, text: http400Message); return;}
      //run matches if request has json payload
         java.util.List canonicalMatches = canonicalMatchService.getMatches(jsonDataMap);
         if(canonicalMatches.size() == 0) {render(status: 404, text : http404Message); return;}
         render(status: 300, text: canonicalMatches.toSet() as JSON); 
        // render canonicalMatches as JSON;

    }

    /**
     * only returns fuzzy matches
     */
    def findFuzzyMatches(){

      log.debug("Enter: findFuzzyMatches");
      if(securityService.login(request) == false){render(status: 401, text: http401Message); return;}
      java.util.Map jsonDataMap = JSON.parse(request);
      if(jsonDataMap == null){ render(status: 400, text: http400Message); return;}
      //run matches if request has json payload
         java.util.List fuzzyMatches = fuzzyMatchService.getMatches(jsonDataMap);
         if(fuzzyMatches?.size() == 0) {render(status: 404, text : http404Message); return;}
         render(status: 300, text: fuzzyMatches.toSet() as JSON);

     }
 
    
}
