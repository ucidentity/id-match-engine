package edu.berkeley.ucic.idmatch; 

import grails.converters.JSON;
import groovy.transform.CompileStatic;
import javax.servlet.http.HttpServletRequest;


/*
 * this controller exposes CanonicalService and its methods for http request
 * use this to initiate canonical match request
 */
class CanonicalController {


    //TODO: is this in urlMappings or here?
    //static allowedMethods = [getMatches:'GET', index:['POST', 'DELETE']]
    
    def  canonicalMatchService;
    def securityService;
    def configService;
    def schemaService;
  
    /*
     * this is the main method that is called via this service 
     */
    def getMatches(){
      def failure = "failed authentication";
      log.debug("the result of login "+securityService.login(request));
      if(!securityService.login(request)) {render(status : 401, text : failure); return; }
      log.debug "passed authn";
      def jsonDataMap = JSON.parse(request).data;
      if(!schemaService.isInputSchemaValid(jsonDataMap)) { render(status : 400, text : "Input Attr not valid schema"); return;}
      if(jsonDataMap) { render canonicalMatchService.getMatches(jsonDataMap) as JSON; }
      else render(status : 401, text : "json is empty");
    }
 
     /*
      * usage
      */ 
     def index() {

      render """USAGE: curl -X POST -d "{"data": {"fName": "venu", "lName": "alla", "ssn": "111222333", "dob" : "123456", "city" : "Berkeley"}}" -H "clien-H "password:123456" -H "content-type: application/json" http://localhost:8080/dolphin/canonical/run"""
    }

}
