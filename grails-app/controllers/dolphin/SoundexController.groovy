package dolphin
/*
Software License and Copyright notice:
http://ipira.berkeley.edu/software-copyright-notice-and-disclaimer
*/

class SoundexController {
  
   def soundexService;

    def index() { render "actions avl are getCode(s1) and compare(s1,s2)"; }
    
    def getCode(String s1){
       render "soundex code for ${params.s1} is ${soundexService.getCode(params.s1)}";
    }

    def compare(String s1, String s2){
       render "soundex compare for ${params.s1} and ${params.s2} is ${soundexService.compare(params.s1,params.s2)}";
    }


}
