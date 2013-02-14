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

package dolphin

/*
Software License and Copyright notice:
http://ipira.berkeley.edu/software-copyright-notice-and-disclaimer
*/

import edu.ualr.oyster.utilities.OysterEditDistance;

class EditDistanceService {
 
   static OysterEditDistance  singleton = new OysterEditDistance();

   def computeDistance(java.lang.String s, java.lang.String t) { 
   
      println "computeDistance called on service"; 
      return singleton.computeDistance(s,t);
   }


   def computeNormalizedScore (String s, String t){
      println "computeNormalizedScore called on service"
      int distance = singleton.computerDistance(s,t);
      return oed.computeNormalizedScore(); 

   }

   def compare(String s, String t, String distance) {
      println "EditDistance compare called"
      def distanceInt = distance as int;
      def realDistance = singleton.computeDistance(s,t) as int;
      println "RealDistance computed is ${realDistance}"
      if(realDistance.intValue() > distanceInt.intValue() ) return false;
      else return true;
   }

}
