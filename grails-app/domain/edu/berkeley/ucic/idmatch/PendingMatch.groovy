package edu.berkeley.ucic.idmatch

/**
 * /v1/pendingmatches
 * if a match query results in one or more matches 
 * and if the requestor sends a async flag, then store the pending match here
 * and send the pendingMatch id for future retrieval
 */
class PendingMatch {


    //uses grails id as primary key
    //SOR and sorId will help identify if the search is being done again for the same user,
    //if done for same user, then just update the timestamp if there are matches,
    //dont add another pending match entry for same SOR-sorId combo
    //if 
    String SOR; 
    String sorId;
    String requestJson;
    Date lastRunTimeStamp;


    static constraints = {
      lastRunTimeStamp nullable: false;
      requestJson nullable: false;
      sorId(SOR: unique, nullable: false)
      SOR(nullable: false)
    }

     //def p = new PendingMatch(SOR : sor, sorId : sorId, requestJson: "{}", lastRunTimeStamp: new Date());
}
