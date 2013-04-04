package edu.berkeley.ucic.idmatch; 

import grails.converters.JSON;

class MainAppController {

    edu.berkeley.ucic.idmatchSecurityService securityService;
    edu.berkeley.ucic.idmatchFuzzyMatchService  fuzzyMatchService;
    edu.berkeley.ucic.idmatchCanonicalMatchService  canonicalMatchService

    def index() { render "USAGE: getMatches" }
  
    /*
     * return canonical and fuzzy matches
     * first check canonical matches, if no canonical matches found,
     * then do a fuzzy match
     */ 
    def getMatches() {
   
      def failure = [reason : "failed authentication"];
      if(securityService.login(request) == false) render failure as JSON;
      java.util.Map jsonDataMap = JSON.parse(request).data;
      //run matches if request has json payload
      if(jsonDataMap) { 
         java.util.List canonicalMatches = canonicalMatchService.getMatches(jsonDataMap);
         if(canonicalMatches.size() > 0) render canonicalMatches;
         else render fuzzyMatchService.getMatches(jsonDataMap) as JSON; 
         }
      else render "[error: "json request payload is empty"]";

     } 
}
