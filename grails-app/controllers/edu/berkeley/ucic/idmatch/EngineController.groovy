package edu.berkeley.ucic.idmatch; 

import grails.converters.JSON;
import edu.berkeley.ucic.idmatch.*;


/**
 * primary API of this application
 * expose API to run matches, both canonical and fuzzy
 */
class EngineController {

    /*
        see URLMappings instead 
        static allowedMethods = [getMatches:'GET',
                                 index:['GET', 'POST']]
    */

    def securityService;
    def fuzzyMatchService;
    def canonicalMatchService;
    def schemaService;

    def index() { render "USAGE: GET:getMatches, GET:getCanonicalMatches, GET:getFuzzyMatches" }
  
    /*
     * return canonical and fuzzy matches
     * first check canonical matches, if no canonical matches found,
     * then do a fuzzy match
     * return 401 if un-authenticated (403 if un-authz )
     */ 
    def findMatches() {
   
      if(securityService.login(request) == false) render(status: 401, text: "Authentication failed");
      java.util.Map jsonDataMap = JSON.parse(request).person;
      if(jsonDataMap == null) render(status: 400, text:"Bad Request: json request payload is empty");
      else println "proceed further"
      //run matches if request has json payload
         java.util.List canonicalMatches = canonicalMatchService.getMatches(jsonDataMap);
         if(canonicalMatches.size() > 0) render(status: 300, results: canonicalMatches);
         def fuzzyMatches = fuzzyMatchService.getMatches(jsonDataMap);
         if(fuzzyMatches.size() == 0) render(status: 404);
     } 



    /**
     * only returns canonical matches
     */
    def findCanonicalMatches() {
      log.debug("Enter: findCanonicalMatches");
      if(!securityService.login(request)) {render(status : 401, text : "failed authn"); return; }
      log.debug "passed authn";
      def jsonDataMap = JSON.parse(request).person;
      if(!schemaService.isInputSchemaValid(jsonDataMap)) { render(status : 400, text : "Input Attr not valid schema"); return;}
      if(jsonDataMap) { render canonicalMatchService.getMatches(jsonDataMap) as JSON; }
      else render(status : 401, text : "json is empty");


    }

    /**
     * only returns fuzzy matches
     */
    def findFuzzyMatches(){

      log.debug("Enter: findFuzzyMatches");
      if(!securityService.login(request)) {render(status : 401, text : "failed authn"); return; }
      def jsonDataMap = JSON.parse(request).person;
      if(!schemaService.isInputSchemaValid(jsonDataMap)) { render(status : 400, text : "Input Attr not valid schema"); return;}
      if(jsonDataMap) { render fuzzyMatchService.getMatches(jsonDataMap) as JSON; }
      else render(status : 401, text : "json is empty");


     }

    
}
