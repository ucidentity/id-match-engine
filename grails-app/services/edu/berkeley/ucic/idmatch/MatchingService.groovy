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
package edu.berkeley.ucic.idmatch


import edu.ualr.oyster.utilities.*;

class MatchingService {
 

    def grailsApplication;


     /* 
      *
      * takes ruleConfigMap, jsonInputValue, registryValue 
      * and returns scores as set in the ruleConfigMap 
      * DEPRECATED as scores are no longer computed, instead admin will give fuzzy match rules
      */ 
     def executeRule(java.util.Map ruleConfigMap, String jsonValue, String registryValue ) {  
       def ctx = grailsApplication.mainContext;
       println "grailsApplication object is "+grailsApplication;
       println "ctx object is "+ctx;
       //def myService = ctx.getBean(serviceName)
       println "entered executeRule, ${ruleConfigMap}, ${jsonValue}, ${registryValue} "      
       def exactScore = ruleConfigMap.exactMatchScore  as int;
       def likeScore = ruleConfigMap.likeMatchScore as int;
       def algorithm = ruleConfigMap.algorithm;
       def distance = ruleConfigMap.distance;
       
       def isExact = jsonValue.equals(registryValue);
       if(isExact) return exactScore;
       def serviceName = "edu.berkeley.ucic.idmatch."+algorithm+"Service";
       println "derived serviceName is "+serviceName;
       def  myService = this.class.classLoader.loadClass(serviceName, true)?.newInstance()
       def isSimilar = false;
       if(distance == null) {
        isSimilar = myService.compare(jsonValue,registryValue);
        println "compare for null distance returned " +isSimilar;
       }else 
       { isSimilar = myService.compare(jsonValue,registryValue, distance as int); 
       println "compare returned isSimilar as "+isSimilar; }
       if(isSimilar) return likeScore;  else return 0;

     }

}
