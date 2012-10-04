package dolphin;

/*
Software License and Copyright notice:
http://ipira.berkeley.edu/software-copyright-notice-and-disclaimer
*/

import edu.ualr.oyster.utilities.DaitchMokotoffSoundex;

/*
 * http://en.wikipedia.org/wiki/Daitch%E2%80%93Mokotoff_Soundex
 */

class DaitchMokotoffSoundexService {

    def compare(String s1, String s2) {
     return new DaitchMokotoffSoundex().compareSoundex(s1,s2); }


    def getCode(String s1){
      return new DaitchMokotoffSoundex().getDMSoundex(s1); }
}
