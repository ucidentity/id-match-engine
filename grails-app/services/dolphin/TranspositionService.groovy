package dolphin

import edu.ualr.oyster.utilities.OysterUtilityTranspose;

class TranspositionService {

    def serviceMethod() {
    }

   /**
     * One string differs from the other string by only one adjacent transposition (John - Jhon)
     * @param s1 source String
     * @param t1 target String
     * @return true if single position transpose, otherwise false
     */
    def boolean compare(String s1, String t1){
       return new OysterUtilityTranspose.differByTranspose(s1,t1);
    }
}
