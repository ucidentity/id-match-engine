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

  def grailsApplication; //config object  
  def matchingService;
  def securityService;


  def scaffold = true;
/*
  for each rule, get key
  get value from json input using this key
  get value from registry using this key and schemaMap
  if(compareSimple(inputVal,registryVal)) then add simple score
  else (compareViaAlgorithm(inputVal,registryVal,algorithm), and add score
  
*/
def match(){
      println request
      def failure = [reason : "failed authentication"];
      if(securityService.login(request) == false) render failure as JSON; 
      def rules = grailsApplication.config.idMatch.ruleSet;
      println "rules is "+rules;
      def ruleKeySet = rules.keySet(); //get keys for the rules
      def schemaMap = grailsApplication.config.idMatch.schemaMap;
      println "schemaMap is "+schemaMap;
      def cutOffScoreMap = grailsApplication.config.idMatch.cutOffScoreMap;
      println "cutOffScoreMap is "+cutOffScoreMap;
      def exactCutOffScore = cutOffScoreMap.get("exact") as int;
      def reconCutOffScore = cutOffScoreMap.get("recon") as int;
      def jsonDataMap = JSON.parse(request).data; //request has data as body field
      println "json map is "+ jsonDataMap;
      def jsonDataMapKey = jsonDataMap.keySet();
      println "json data key is "+jsonDataMapKey;
      def exactResults = [];
      def reconResults = [];
      //
      //get all persons in the registry
      println "pre-fetch "+new Date();
      def persons = User.list();
      println "post fetch "+new Date();
      //for each person in the person list
      persons.each(){ person ->
            println "person is "+person;
            def personMatchScore = 0;
            def personProfile = [:];
            //for each rule in a given rule list
            jsonDataMapKey.each() { ruleKey ->
             println "rule Key is " + ruleKey;
             def jsonDataValue = jsonDataMap.get(ruleKey).toString(); //get value from json
             println "json data value is "+jsonDataValue;
             def dbColName = schemaMap.get(ruleKey); //get real col name from schema map
             println "schemaMap value for ruleKey is ${dbColName}";
             def registryValue = person."${dbColName}";
             println "person col value for this key is ${registryValue}";
             def ruleConfigMap = rules.get(ruleKey); //get the rule configuration for a given key
             println "rule config is "+ruleConfigMap;
             if(ruleConfigMap != null){
             def ruleScore = matchingService.executeRule(ruleConfigMap, jsonDataValue,registryValue);
             println "ruleScore is "+ruleScore;
             personMatchScore = personMatchScore + ruleScore;
             personProfile.put(ruleKey, registryValue)};
 
         }
             println "personMatchScore is "+personMatchScore; 
             if (personMatchScore == exactCutOffScore.intValue() ) {
                personProfile.put("person", person); 
                personProfile.put("personMatchScore", personMatchScore);
                exactResults.add(personProfile);
                println "exact match results are "+exactResults;
             }
             else if ((personMatchScore >= reconCutOffScore.intValue())
                       &&
                       (personMatchScore <  exactCutOffScore.intValue() )
                      )
                     {  personProfile.put("person", person); 
                        personProfile.put("personMatchScore", personMatchScore);
                        reconResults.add(personProfile); 
                        println "recon match results are "+reconResults; 
                     }
      }
       def response = [:];
       response.put("input", jsonDataMap)
       response.put("exact" , exactResults);
       response.put("recon" , reconResults);
       println "Date match complete "+new Date(); 
       render response as JSON;

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


   /*
    * match for new configuration
    */
    def match2() {
      def failure = [reason : "failed authentication"];
      if(securityService.login(request) == false) render failure as JSON;
      def jsonDataMap = JSON.parse(request).data; //request has data as body field
      println "json map is "+ jsonDataMap;
 
      def canonicalMatchRuleSet = grailsApplication.config.idMatch.canonicalMatchRuleSet;
      def selectStmt;
      def lastName = "alla"
      canonicalMatchRuleSet.each {
           if(it.size() == 1 ) { 
             def realColName = grailsApplication.config.idMatch.schemaMap.get(it[0]);
             def jsonInputValue = jsonDataMap.get(it[0]);
             if(jsonInputValue != null) 
             selectStmt = "${realColName} = '${jsonInputValue}'" 
           }
          else { println it.size() ; 
                def subStmt; 
                def isEmpty = false; //making an assumption that json has all required field values
                it.each{ attr -> 
                  println "got ${attr} to make sql"
                  def jsonInputValue = jsonDataMap.get(attr); 
                  if(jsonInputValue == null) { println "${attr} value is empty"; isEmpty = true; }  
                  if((jsonInputValue != null ) && (isEmpty != true) ) {
                  def realColName = grailsApplication.config.idMatch.schemaMap.get(attr);
                  if((subStmt == null)) subStmt = "${realColName} = '${jsonInputValue}'";
                  else subStmt = "${subStmt} AND ${realColName} = '${jsonInputValue}'"; 
                  }
                }
                 if(!isEmpty) { selectStmt = "(${selectStmt}) OR (${subStmt})" }
         }
      }
      def hqlStmt = "from User where ${selectStmt}".trim();
      println hqlStmt;
      def results = User.findAll(hqlStmt); // uses HQL
      println "results are "+results;
      def fuzzyMatchRuleSet  = grailsApplication.config.idMatch.fuzzyMatchRuleSet;         
      def attributeFuzzyMatchAlgorithm = grailsApplication.config.idMatch.attributeFuzzyMatchAlgorithm;
      render "${canonicalMatchRuleSet} ${fuzzyMatchRuleSet} ${attributeFuzzyMatchAlgorithm}" ;

    }

   def debug(){
      def jsonDataMap = JSON.parse(request).data; //request has data as body field
      def canonicalMatchRuleSet = grailsApplication.config.idMatch.canonicalMatchRuleSet;
      def schemaMap = grailsApplication.config.idMatch.schemaMap;
      render "${jsonDataMap} ${canonicalMatchRuleSet} ${schemaMap}" 


    }

}

