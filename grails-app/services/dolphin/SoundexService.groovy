package dolphin;

import edu.ualr.oyster.utilities.Soundex;

/*
* http://en.wikipedia.org/wiki/Soundex
*
*/

class SoundexService {

    def compare(String s1, String s2) { 
       println("inside SoundexService.compare");
       return new Soundex().compareSoundex(s1,s2); }

    def getCode(String s1){
        return new Soundex().getSoundex(s1);}

    def compareNew(String s1, String s2){
        println("inside SoundexService.newCompare");
       return new Soundex().compareSoundex(s1,s2);
     }
}
