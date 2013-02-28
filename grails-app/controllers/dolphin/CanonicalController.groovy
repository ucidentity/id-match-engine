package dolphin

import grails.converters.JSON;
import groovy.transform.CompileStatic;
import javax.servlet.http.HttpServletRequest;

import dolphin.CanonicalMatchService;
import dolphin.SecurityService;


class CanonicalController {

    def  canonicalMatchService;
    def securityService;
  
    /*
     * this is the main method that is called via this service 
     */
    def run(){
      def failure = [reason : "failed authentication"];
      if(securityService.login(request) == false) render failure as JSON;
      def jsonDataMap = JSON.parse(request).data;
      if(jsonDataMap) {
      render canonicalMatchService.executeRules(jsonDataMap); }
      else render "json is empty";
    }
 
    def index() {
      render "call run with json data"
    }

}
