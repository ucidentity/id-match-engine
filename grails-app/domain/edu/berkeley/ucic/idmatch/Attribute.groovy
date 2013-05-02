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
    String key;
    String value;
    String SOR; //source of this attribute ex: hr, sis, alumni etc?
}
