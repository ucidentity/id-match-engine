package edu.berkeley.ucic.idmatch; 

import grails.converters.JSON;
import groovy.transform.CompileStatic;
import javax.servlet.http.HttpServletRequest;


/*
 * this controller exposes CanonicalService and its methods for http request
 * use this to initiate canonical match request
 */
class CanonicalController {

    def  canonicalMatchService;
    def securityService;
  
    /*
     * this is the main method that is called via this service 
     */
    def getMatches(){
      def failure = [reason : "failed authentication"];
      if(securityService.login(request) == false) render failure as JSON;
      def jsonDataMap = JSON.parse(request).data;
      if(jsonDataMap) { render canonicalMatchService.getMatches(jsonDataMap) as JSON; }
      else render "json is empty";
    }
 
     /*
      * redirect to usage
      */
       def index() { forward action: "usage"}

     /*
      * usage
      */ 
     def usage() {

      render """USAGE: curl -X POST -d "{"data": {"fName": "venu", "lName": "alla", "ssn": "111222333", "dob" : "123456", "city" : "Berkeley"}}" -H "clien-H "password:123456" -H "content-type: application/json" http://localhost:8080/dolphin/canonical/run"""
    }

}
