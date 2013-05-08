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
     * only attributes that have constraints declared here are attr2 and attr3
     * attr2 = sorId, attr3 = SOR
     * attr2(unique: 'attr3') is grails way of saying sorId+SOR are unique 
     * note: by default grails does not allow nullable values, hence set to true
    */
    static constraints = {
      
      attr1 nullable: false
      attr2(unique:'attr3', nullable: false)
      attr3 nullable: false
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
    String attr1

    /* reserved for SOR and sorId */
    String attr2 
    String attr3

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
