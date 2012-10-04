/* 
 * Copyright ©2012. The Regents of the University of California (Regents).
 * All Rights Reserved. Permission to use, copy, modify, and distribute this
 * software and its documentation for educational, research, and not-for-profit
 * purposes, without fee and without a signed licensing agreement, is hereby
 * granted, provided that the above copyright notice, this paragraph and the
 * following two paragraphs appear in all copies, modifications, and
 * distributions. Contact The Office of Technology Licensing, UC Berkeley, 2150
 * Shattuck Avenue, Suite 510, Berkeley, CA 94720-1620, (510) 643-7201, for
 * commercial licensing opportunities.
 * 
 * Created by Venu Alla, CalNet, IST, University of California, Berkeley
 * 
 * IN NO EVENT SHALL REGENTS BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
 * SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
 * ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * REGENTS HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * REGENTS SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY, PROVIDED
 * HEREUNDER IS PROVIDED "AS IS". REGENTS HAS NO OBLIGATION TO PROVIDE
 * MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 */
package dolphin;

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
