package edu.berkeley.ucic.idmatch

/**
 * Attribute corresponds to a property in Person profile
 * A Person shall have one or more Attributes
 *
 * @author venu.alla@berkeley.edu
 */
class Attribute {

   static belongsTo =  Person; 
   static constraints = {
    }
    String name;
    String value;
}
