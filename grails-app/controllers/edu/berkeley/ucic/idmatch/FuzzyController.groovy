package edu.berkeley.ucic.idmatch;

import grails.converters.JSON;

/*
 * this controller exposes fuzzyMatchService to http request
 * use this controller to initiate fuzzy match requests
 */
class FuzzyController {

   //remove index
   static allowedMethods = [getMatches:'GET',
                             index:['POST', 'DELETE']]

    edu.berkeley.ucic.idmatch.FuzzyMatchService  fuzzyMatchService;
    edu.berkeley.ucic.idmatch.SecurityService securityService;
  
    /*
     * this is the main method that is called via this service 
     */
    def runOld(){
      def failure = [reason : "failed authentication"];
      if(securityService.login(request) == false) render failure as JSON;
      def jsonDataMap = JSON.parse(request).data;
      if(jsonDataMap) { render fuzzyMatchService.executeRules(jsonDataMap) as JSON; }
      else render "[error: "json request payload is empty"]";
    }

   def getMatches(){
      def failure = [reason : "failed authentication"];
      if(securityService.login(request) == false) render failure as JSON;
      java.util.Map jsonDataMap = JSON.parse(request).data;
      if(jsonDataMap) { render fuzzyMatchService.getMatches(jsonDataMap) as JSON; }
      else render "[error: "json request payload is empty"]";
   }
 
    def index() {
      render """USAGE: curl -X POST -d "{"data": {"fName": "venu", "lName": "alla", "ssn": "111222333", "dob" : "123456", "city" : "Berkeley"}}" -H "clien-H "password:123456" -H "content-type: application/json" http://localhost:8080/dolphin/fuzzy/runMatches"""
    }

}
