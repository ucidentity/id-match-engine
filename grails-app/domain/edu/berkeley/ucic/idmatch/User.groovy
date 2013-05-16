package edu.berkeley.ucic.idmatch

/**
 * Domain Model to save User profile for Matching purposes
 * @author venu.alla@berkeley.edu
 * 
 */
class User {

    /**
     * notice that all attrs are nullable : true, that is because
     * we do not know which properties go into which columns and is decided outside of the domain class
     * see config for idMatch.schemaMap
     * Note: if you want unique constraints, I suggest it be enforced at db level as the domain class has no idea
     * which attributes need to be unique
     * SOR(unique: 'sorId') is grails way of saying sorId+SOR are unique 
     * note: by default grails does not allow nullable values, hence set to true
    */
    static constraints = {
      
      referenceId nullable: false
      SOR(unique:'sorId', nullable: false)
      sorId nullable: false
      attr4 nullable: true
      attr5 nullable: true
      attr6 nullable: true
      attr7 nullable: true
      attr8 nullable: true
      attr9 nullable: true
      attr10 nullable: true
      attr11 nullable: true
      attr12 nullable: true
      attr13 nullable: true
      attr14 nullable: true
      attr15 nullable: true

    }
   
    /** attr1 corresponds to a unique identifier like uid or some other campus identifier */ 
    //CIFER API refers to this as reference ID
    String referenceId 

    /* reserved for SOR and sorId */
    String SOR
    String sorId 

    /** generic and configured in idMatch.schemaMap */
    String attr4
    String attr5 
    String attr6
    String attr7
    String attr8
    String attr9
    String attr10
    String attr11
    String attr12
    String attr13
    String attr14
    String attr15

}
