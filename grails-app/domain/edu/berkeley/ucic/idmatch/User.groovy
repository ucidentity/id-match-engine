package edu.berkeley.ucic.idmatch

/**
 * Domain Model to save User profile for Matching purposes
 * @author venu.alla@berkeley.edu
 * 
 */
class User {

    static constraints = {
      attr1 nullable: false
      attr2 nullable: true
      attr3 nullable: true
      attr4 nullable: true
      attr5 nullable: true
      attr6 nullable: true
      attr7 nullable: true
      attr8 nullable: true
      attr9 nullable: true
      attr10 nullable: true

    }
   
    /** attr1 corresponds to a unique identifier like uid or some other campus identifier */ 
    String attr1 //uid or some other unique generic identifier relevant to your campus
    /** attr2 to attr10 are placeholders for all other profile attributes useful for matching */
    String attr2 //fname
    String attr3 //lname
    String attr4 //dob
    String attr5 //city
    String attr6 //ssn
    String attr7 //sex? M/F
    String attr8 //empid
    String attr9 //stuid
    String attr10 //affid
}
