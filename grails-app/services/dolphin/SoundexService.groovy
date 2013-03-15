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

/*
Software License and Copyright notice:
http://ipira.berkeley.edu/software-copyright-notice-and-disclaimer
*/

import edu.ualr.oyster.utilities.Soundex;

/*
* http://en.wikipedia.org/wiki/Soundex
*
* both strings need to start with  same character!! Karl and Carl are not same!!
*/

class SoundexService {
  
    def static singleton = new Soundex();

    /*
     * this method is faster after removing log.debug stmts
     */
    def compare(String s1, String s2) { 
       //log.debug("Enter");
       def result = singleton.compareSoundex(s1,s2); 
       //log.debug("${result} for ${s1} and ${s2}");
       return result;
     }

    def getCode(String s1){
        return singleton.getSoundex(s1);}

    def findMatches(String jsonValue, String registryColName, java.util.List users) {
        log.debug("entering findMatches");
        java.util.List results = [];
        Soundex soundex = new Soundex();
        users.each() { user -> 
           String registryValue = user."${registryColName}"
           //only do soundex if first chars match, else skip
           if(String.valueOf(jsonValue.charAt(0)).equalsIgnoreCase(String.valueOf(registryValue.charAt(0)))) {
           //log.debug("comparing ${jsonValue} and ${registryValue}");
           if(soundex.compareSoundex(jsonValue,registryValue)) results.add(user);}
        }
        log.debug("${results.size()} results found in Soundex match");
        return results;
    
    }

}
