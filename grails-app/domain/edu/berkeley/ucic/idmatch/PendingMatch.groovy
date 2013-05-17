package edu.berkeley.ucic.idmatch

/**
 * /v1/pendingmatches
 * if a match query results in one or more matches 
 * and if the requestor sends a async flag, then store the pending match here
 * and send the pendingMatch id for future retrieval
 */
class PendingMatch {


    //uses grails id as primary key
    //if done for same user, then just update the timestamp if there are matches,
    //dont add another pending match entry for same SOR-sorId combo
    String SOR;
    String sorId;
    String matchFilterJson;
    Date createDate;


    static constraints = {
      SOR(nullable: false)
      sorId(unique : 'SOR', nullable: false)
      matchFilterJson nullable: false;
      createDate nullable: false;
    }

}
