package dolphin

/*
Software License and Copyright notice:
http://ipira.berkeley.edu/software-copyright-notice-and-disclaimer
*/

import edu.ualr.oyster.utilities.OysterEditDistance;

class EditDistanceService {

   def getDistance(java.lang.String s, java.lang.String t) { 
   
      println "computeDistance called on service"; 
      return new OysterEditDistance().computeDistance(s,t);
   }


   def computeNormalizedScore (String s, String t){
      println "computeNormalizedScore called on service"
      OysterEditDistance oed = new OysterEditDistance();
      int distance = oed.computerDistance(s,t);
      return oed.computeNormalizedScore(); 

   }

}
