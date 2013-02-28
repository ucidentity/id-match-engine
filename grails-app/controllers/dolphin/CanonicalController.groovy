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
    @CompileStatic 
    def runCompiled() { 
      HttpServletRequest request;
      def failure = [reason : "failed authentication"];
      if(securityService.login(request) == false) render failure as JSON;
      java.lang.Object jsonObject = JSON.parse(request); //add .data later to get to data
      if(jsonObject == null) render "missing json data"
      render canonicalMatchService.execute(jsonObject);
    }
   */

    /*
     * 
     */
    def run(){
      def failure = [reason : "failed authentication"];
      if(securityService.login(request) == false) render failure as JSON;
      def jsonDataMap = JSON.parse(request).data;
      if(jsonDataMap == null) render "missing json data"
      render canonicalMatchService.executeRules(jsonDataMap);
    }
 
   def index() {
      render "call run with json data"
    }
   
   def hqlTest() {
      render canonicalMatchService.hqlTest();
   }
}
