package edu.berkeley.ucic.idmatch

import edu.ualr.oyster.utilities.CharacterSubstringMatches;

class SubStringService {


    static CharacterSubstringMatches  singleton = new CharacterSubstringMatches();
   /**
     * Determines if a source and target are equivalent based on if the first x
     * characters starting from the left or right are the same, i.e. SubStrLeft(4) makes
     * MaryAnne ≈ Mary
     * @param s1 is source String
     * @param t1
     * @param length is the number of characters from the start position that
     * will be copied.
     * @return true is a match occurs, otherwise false
     */
   def boolean  compare(String s1, String t1, String startFrom, int length) {
     def result = false;
     if(startFrom == null ) return false;
     if("LAST".equals(startFrom.toUpperCase())){
        return singleton.left(s1,t1,length);
      }
     if("FIRST".equals(startFrom.toUpperCase())) {
        return singleton.right(s1,t1,length);

     }
     return result; 

    }


    /* not yet implemented */ 
    def findMatches(String s1, java.util.List users) {}
   
}
