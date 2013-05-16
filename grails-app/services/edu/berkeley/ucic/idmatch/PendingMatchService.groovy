package edu.berkeley.ucic.idmatch;


/**
 * service to manage PendingMatch resource
 */
class PendingMatchService {

    /**
     * creates or updates PendingMatch resource  
     */  
    def PendingMatch createOrUpdate(java.util.Map jsonDataMap){
      log.debug("Enter createOrUpdate");
      log.debug("passed in values are "+jsonDataMap+","+SOR+","+sorId);
      def pendingMatch  = PendingMatch.findWhere(SOR : jsonDataMap.SOR, sorId : jsonDataMap.sorId);
      params.SOR = jsonDataMap.SOR;
      params.sorId = jsonDataMap.sorId;
      params.requestJson = jsonDataMap.toString();
      params.createDate = new Date();
      if(pendingMatch == null) { pendingMatch =  new PendingMatch(params); }
      else{ pendingMatch.properties = params; }
      try { pendingMatch.save(flush: true, failOnError : true); }catch(e){log.debug(e.getMessage()); pendingMatch = null; }
      log.debug("EXIT createOrUpdate with pendingMatch object "+pendingMatch?.id);
      return pendingMatch;
    }

 }
