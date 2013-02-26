package dolphin

import edu.ualr.oyster.utilities.OysterUtilityTranspose;

class TranspositionService {
 
    static OysterUtilityTranspose singleton = new OysterUtilityTranspose();

   /**
     * One string differs from the other string by only one adjacent transposition (John - Jhon)
     * @param s1 source String
     * @param t1 target String
     * @return true if single position transpose, otherwise false
     */
    def boolean compare(String s1, String t1){
       //log.debug("Enter for "+s1+" and "+t1);
       if(s1.equalsIgnoreCase(t1)){return true}; //transposition not needed, just return true
       def result = singleton.differByTranspose(s1,t1);
       log.debug("Exit with  ${result} for "+s1+" and "+t1);
       return result;
    }
}
