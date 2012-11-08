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


import edu.ualr.oyster.utilities.*;

class MatchingService {
 

    def soundexService; //TODO: Remove this as this is dynamically loaded 
    def grailsApplication;


     /* takes ruleConfigMap, jsonInputValue, registryValue 
      * and returns scores as set in the ruleConfigMap 
      */ 
     def executeRule(java.util.Map ruleConfigMap, String jsonValue, String registryValue ) {  
       println "entered executeRule, ${ruleConfigMap}, ${jsonValue}, ${registryValue} "      
       def exactScore = ruleConfigMap.exactMatchScore  as int;
       def likeScore = ruleConfigMap.likeMatchScore as int;
       def algorithm = ruleConfigMap.algorithm;
       def distance = ruleConfigMap.distance;
       
       def isExact = jsonValue.equals(registryValue);
       if(isExact) return exactScore;
       def serviceName = algorithm+"Service";
       println "derived serviceName is "+serviceName;
       def isSimilar = false;
       def className = "dolphin."+serviceName;
       def myService = this.class.classLoader.loadClass(className.toString(), true)?.newInstance()
       //def myService = grailsApplication.classLoader.loadClass("dolphin.${serviceName}").newInstance();
       println myService.toString();
       if(distance == null) {
        isSimilar = myService.compare(jsonValue,registryValue);
       }else 
       { isSimilar = myService.compare(jsonValue,registryValue, distance); }
       println "compare returned isSimilar as "+isSimilar;
       if(isSimilar) return likeScore;  else return 0;

     }

   /*
    * deprecate this one, not used any more
    */
   //pass Algorithm, stringA, stringB
   def runAlgorithm(String stringA, String stringB,String algorithm){
 
         log.info  "the passed in attr are  ${stringA} and ${stringB} and ${algorithm}"
       switch (algorithm) {
         case "Soundex":
               def result = soundexService.compare(stringA,stringB);
               println "the result of compare is "+result;
               return result;
         case "NYSIIS":
               return NYSIISService.compare(stringA,stringB);
         case "DaitchMokotoff":
              return  DaitchMokotoffService.compare(stringA,stringB);
         case "Levenstein":
               return new OysterEditDistance().computeDistance(stringA,stringB);
         case "JaroWinkler":
              return JarowinklerDistanceService.computeDistance(stringA,stringB)
         default:
             return 0;



   }
}

}
