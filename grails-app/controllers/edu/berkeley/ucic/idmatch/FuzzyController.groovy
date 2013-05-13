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
    def schemaService;
  
   def getMatches(){
      def failure = "failed authentication";
      log.debug("the result of login "+securityService.login(request));
      if(!securityService.login(request)) {render(status : 401, text : failure); return; }
      def jsonDataMap = JSON.parse(request).data;
      if(!schemaService.isInputSchemaValid(jsonDataMap)) { render(status : 400, text : "Input Attr not valid schema"); return;}
      if(jsonDataMap) { render fuzzyMatchService.getMatches(jsonDataMap) as JSON; }
      else render(status : 401, text : "json is empty");
   }
 
    def index() {
      render """USAGE: curl -v -X POST -d "{"data": {"fName": "venu", "lName": "alla", "ssn": "111222333", "dob" : "123456", "city" : "Berkeley"}}" -H "clien-H "password:123456" -H "content-type: application/json" http://localhost:8080/dolphin/fuzzy/runMatches"""
    }

}
