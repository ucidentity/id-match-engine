package edu.berkeley.ucic.idmatch

/**
 * Domain Model to save User profile for Matching purposes
 * @author venu.alla@berkeley.edu
 * 
 */
class Person {

    /**
     * notice that all attrs are nullable : true, that is because
     * we do not know which properties go into which columns and is decided outside of the domain class
     * see config for idMatch.schemaMap
     * Note: if you want unique constraints, I suggest it be enforced at db level as the domain class has no idea
     * which attributes need to be unique beyond what is configured here
     * note: by default grails does not allow nullable values, hence set to true
     * sorId in SOR should be unique (sorId unique:SOR)
     * referenceId in SOR should be unique (referenceId unique:SOR)
    */
    static constraints = {
      
      referenceId nullable: true
      SOR(nullable: false)
      sorId(unique : 'SOR', nullable: false)
      lastName nullable: true
      firstName nullable: true
      middleInitial nullable: true
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
    String SOR
    String sorId 
    String lastName
    String firstName 
    String middleInitial 

    /** generic and configured in idMatch.schemaMap */
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
