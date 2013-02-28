package dolphin

import grails.converters.JSON;
import groovy.transform.CompileStatic;
import javax.servlet.http.HttpServletRequest;

class CanonicalController {

    dolphin.CanonicalMatchService  canonicalMatchService;
    dolphin.SecurityService securityService;
  
    /*
     * this is the main method that is called via this service 
     */
    def run(){
      def failure = [reason : "failed authentication"];
      if(securityService.login(request) == false) render failure as JSON;
      def jsonDataMap = JSON.parse(request).data;
      if(jsonDataMap) { render canonicalMatchService.executeRules(jsonDataMap) as JSON; }
      else render "json is empty";
    }
 
    def index() {

      render """USAGE: curl -X POST -d "{"data": {"fName": "venu", "lName": "alla", "ssn": "111222333", "dob" : "123456", "city" : "Berkeley"}}" -H "clien-H "password:123456" -H "content-type: application/json" http://localhost:8080/dolphin/canonical/run"""
    }

}
