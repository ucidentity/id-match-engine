package edu.berkeley.ucic.idmatch;


import org.codehaus.groovy.grails.web.json.JSONObject;

/**
 * service to manage PendingMatch resource
 */
class PendingMatchService {

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
       String method = "getDetailedPendingMatch";
       log.debug("Enter: ${method} with resourceId ${pendingMatchId}");

       def pendingMatch = PendingMatch.get(pendingMatchId);
       //if no resource found or if resource has sorPerson column empty, then leave now
       if(pendingMatch == null || pendingMatch?.sorPerson == null) { return null;  }

       def jsonDataMap = new JSONObject(pendingMatch.sorPerson);
       java.util.List canonicalMatches = canonicalMatchService.getMatches(jsonDataMap);
       if(canonicalMatches.size() > 0) { pendingMatch.candidates = canonicalMatches;};
       else { java.util.List fuzzyMatches = fuzzyMatchService.getMatches(jsonDataMap);
              if(fuzzyMatches.size() > 0) pendingMatch.candidates = fuzzyMatches;
       }
       log.debug("Exit: ${method} with {pendingMatch}");
       return pendingMatch; 
    }

}
