package dolphin;

/*
Software License and Copyright notice:
http://ipira.berkeley.edu/software-copyright-notice-and-disclaimer
*/

import edu.ualr.oyster.utilities.NYSIISCode;


/*
*http://en.wikipedia.org/wiki/NYSIIS
*/
class NYSIISService {

    def getCode(String s1){ 
         return new NYSIISCode().getNYSIISCode(s1);}

    def compare(String s1, String s2){ 
         return new NYSIISCode().compareNYSIISCodes(s1,s2);}
}
