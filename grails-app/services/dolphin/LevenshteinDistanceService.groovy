package dolphin;
/*
Software License and Copyright notice:
http://ipira.berkeley.edu/software-copyright-notice-and-disclaimer
*/

import edu.ualr.oyster.utilities.OysterEditDistance;

/*
 * http://en.wikipedia.org/wiki/Levenshtein_distance
 * also called EditDistance
 * The Levenshtein distance can also be computed between two longer strings, 
 * but the cost to compute it, which is roughly proportional to the product of the two string lengths, makes this impractical. 
 * Thus, when used to aid in fuzzy string searching in applications such as record linkage, 
 * the compared strings are usually short to help improve speed of comparisons.
 */

class LevenshteinDistanceService {

   def computeDistance(String s, String t) { 
   
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
