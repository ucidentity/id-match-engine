package edu.berkeley.ucic.idmatch;


import org.codehaus.groovy.grails.web.json.JSONObject;
import grails.converters.JSON;

import edu.berkeley.ucic.idmatch.*;

/**
 * service to manage PendingMatch resource
 */
class PendingMatchService {


    def canonicalMatchService;
    def fuzzyMatchService;

    /**
     * creates or updates PendingMatch resource  
     */  
    def PendingMatch createOrUpdate(java.util.Map jsonDataMap){
      log.debug("Enter createOrUpdate");
      log.debug("passed in values are "+jsonDataMap);
      def queryParams = [:];
      queryParams.SOR = jsonDataMap.SOR;
      queryParams.sorId = jsonDataMap.sorId
      def pendingMatch  = PendingMatch.findWhere(queryParams);
      def params = [:];
      params.SOR = jsonDataMap.SOR;
      params.sorId = jsonDataMap.sorId;
      params.sorPerson = jsonDataMap.toString();
      params.createDate = new Date();
      log.debug(params);
      try{
     if(pendingMatch == null) { pendingMatch =  new PendingMatch(params); }
     else{ pendingMatch.properties = params; }
     pendingMatch.save(flush: true, failOnError : true); 
      }catch(e){log.debug(e.getMessage()); pendingMatch = null; }
      log.debug("EXIT createOrUpdate with pendingMatch object "+pendingMatch?.id);
      return pendingMatch;
    }


  
    /** delete PendingMatch **/
    def delete(String id){
      PendingMatch.delete(id);
    }

    /**
     * custom get that includes match result candidates in the PendingMatch profile
     */
    def java.util.Map get(String pendingMatchId){
       String method = "get";
       log.debug("Enter: ${method} with resourceId ${pendingMatchId}");
       def result = [:];

       def pendingMatch = PendingMatch.get(pendingMatchId);
       //if no resource found or if resource has sorPerson column empty, then leave now
       if(pendingMatch == null || pendingMatch?.sorPerson == null) { return null;  }
       log.debug("Found pendingMatch ${pendingMatch.sorPerson} for resource id ${pendingMatchId}");
       def jsonDataMap = new JSONObject(pendingMatch.sorPerson);
       java.util.List canonicalMatches = canonicalMatchService.getMatches(jsonDataMap);
       if(canonicalMatches.size() > 0) { result.candidates = canonicalMatches;};
       else { java.util.List fuzzyMatches = fuzzyMatchService.getMatches(jsonDataMap);
              if(fuzzyMatches.size() > 0) result.candidates = fuzzyMatches;
       }
       //TODO:
       //transient properties on domain class do not get returned as part of JSON response
       //hence had to recreate a new Map.
       result.candidates = Person.findAll(); //TODO: remove this after testing
       result.id = pendingMatch.id;
       result.SOR = pendingMatch.SOR;
       result.sorId = pendingMatch.sorId;
       result.sorPerson = pendingMatch.sorPerson;
       log.debug("Exit: ${method} with ${result}");
       return result; 
    }

}
