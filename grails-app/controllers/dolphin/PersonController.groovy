/* 
 * Copyright ©2012. The Regents of the University of California (Regents).
 * All Rights Reserved. Permission to use, copy, modify, and distribute this
 * software and its documentation for educational, research, and not-for-profit
 * purposes, without fee and without a signed licensing agreement, is hereby
 * granted, provided that the above copyright notice, this paragraph and the
 * following two paragraphs appear in all copies, modifications, and
 * distributions. Contact The Office of Technology Licensing, UC Berkeley, 2150
 * Shattuck Avenue, Suite 510, Berkeley, CA 94720-1620, (510) 643-7201, for
 * commercial licensing opportunities.
 * 
 * Created by Venu Alla, CalNet, IST, University of California, Berkeley
 * 
 * IN NO EVENT SHALL REGENTS BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
 * SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
 * ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * REGENTS HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * REGENTS SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY, PROVIDED
 * HEREUNDER IS PROVIDED "AS IS". REGENTS HAS NO OBLIGATION TO PROVIDE
 * MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 */
package dolphin


import grails.converters.JSON
import grails.converters.XML
import org.codehaus.groovy.grails.web.servlet.mvc.*;


class PersonController {

  def matchingService;
  def securityService;
  def canonicalMatchService;
  def fuzzyMatchService;
  def grailsApplication;


  def scaffold = true;
/*
  for each rule, get key
  get value from json input using this key
  get value from registry using this key and schemaMap
  if(compareSimple(inputVal,registryVal)) then add simple score
  else (compareViaAlgorithm(inputVal,registryVal,algorithm), and add score
  
*/
def fuzzyMatch(){
      println request
      def failure = [reason : "failed authentication"];
      if(securityService.login(request) == false) render failure as JSON; 
      def jsonDataMap = JSON.parse(request).data;
      def response  =  fuzzyMatchService.executeRulesV1(jsonDataMap);
      render response as JSON;

}


   /*
    * debug configuration
    */
   def debug(){
      def jsonDataMap = JSON.parse(request).data; //request has data as body field
      def canonicalMatchRuleSet = grailsApplication.config.idMatch.canonicalMatchRuleSet;
      def schemaMap = grailsApplication.config.idMatch.schemaMap;
      render "${jsonDataMap} ${canonicalMatchRuleSet} ${schemaMap}" 
    }

  
  /*
   * deprecated
   *
   */ 
   def json(){
      def failure = [reason : "failed authentication"];
      if(securityService.login(request) == false) render failure as JSON; 
      def persons = Person.list();
      render persons as JSON;
   }


}

