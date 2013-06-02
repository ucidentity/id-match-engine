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

package edu.berkeley.ucic.idmatch

/*
Software License and Copyright notice:
http://ipira.berkeley.edu/software-copyright-notice-and-disclaimer
*/

import edu.ualr.oyster.utilities.OysterEditDistance;

/*
 * Also called Levenstein Edit Distance
 */
class EditDistanceService {
 
   static OysterEditDistance  singleton = new OysterEditDistance();

   def computeDistance(java.lang.String s, java.lang.String t) { 
      log.debug("computeDistance called on service"); 
      return singleton.computeDistance(s,t);
   }


   def computeNormalizedScore (String s, String t){
      log.debug("computeNormalizedScore called on service");
      int distance = singleton.computerDistance(s,t);
      return oed.computeNormalizedScore(); 

   }

   def compare(String s, String t, String distance) {
      def distanceInt = distance as int;
      def realDistance = singleton.computeDistance(s,t) as int;
      log.debug("distance computed for ${s} and ${t} is ${realDistance}");
      if(realDistance.intValue() > distanceInt.intValue() ) return false;
      else return true;
   }

   def findMatches(String inputValue, String registryColName, java.util.List users, Integer distance) {
        String method = "findMatches";
        log.info("Enter: ${method} with ${users.size()} users to match");
        java.util.List results = [];
        OysterEditDistance editDistance = new OysterEditDistance();
        users.each() { user ->
           String registryValue = user."${registryColName}"
           int distanceFound = editDistance.computeDistance(inputValue,registryValue);
           log.debug("${inputValue} and ${registryValue} have editDistance of ${distanceFound}");
           if(distanceFound <= distance.intValue()){ results.add(user);}
        }
        log.info("Exit: ${method} with ${results.size()} users matched");
        return results;

   }

}
