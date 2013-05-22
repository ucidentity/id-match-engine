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
      log.debug("passed in values are "+jsonDataMap);
      def queryParams = [:];
      queryParams.SOR = jsonDataMap.SOR;
      queryParams.sorId = jsonDataMap.sorId
      def pendingMatch  = PendingMatch.findWhere(queryParams);
      def params = [:];
      params.SOR = jsonDataMap.SOR;
      params.sorId = jsonDataMap.sorId;
      params.matchAttrs = jsonDataMap.getString("matchAttrs");
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

}
