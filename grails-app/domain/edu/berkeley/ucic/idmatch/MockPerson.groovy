package edu.berkeley.ucic.idmatch

/**
 * Domain Model to save mock User profile for Matching purposes
 * remove this after testing?
 * @author venu.alla@berkeley.edu
 * 
 */
class MockPerson {

    static constraints = {
      
      jsonPerson nullable: false 

    }
   
    String jsonPerson 

}
