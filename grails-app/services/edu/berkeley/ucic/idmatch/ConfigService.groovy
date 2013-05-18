package edu.berkeley.ucic.idmatch;

import org.codehaus.groovy.grails.commons.GrailsApplication;

/*
 * the purpose of this class is to decouple configuration access
 * so the other services that need access to configuration do not need to know how it is
 * fetched, the future version of this will be database based as opposed to config file based
 */

class ConfigService {

    def  grailsApplication;

    def canonicalRules;
    def fuzzyRules;
    def schemaMap;
    def secKeys;
    def fuzzyAttributeAlgorithmMap;

    /*
     * used to create a dynamic sql
     */
    def getCanonicalRules() {
       if(canonicalRules == null) return grailsApplication.config.idMatch.canonicalMatchRuleSet;
       else return canonicalRules;
    }

    def setCanonicalRules(java.util.Map rules){
        canonicalRules = rules;
    }


    /*
     * will pick only one rule out a set of rules configured here
     */
    def getFuzzyRules(){
       if(fuzzyRules == null) return grailsApplication.config.idMatch.fuzzyMatchRuleSet;
       else return fuzzyRules;
    }
    def setFuzzyRules(java.util.Map rules){
        fuzzyRules = rules;
    }


    def getSchemaMap(){
        if(schemaMap == null) return grailsApplication.config.idMatch.schemaMap;
        else return schemaMap;
    }

  
    def getSecurityKeys(){
        if(secKeys == null) return grailsApplication.config.idMatch.securityKeyMap;
        else return secKeys;
    }
    def setSecurityKeys(java.util.Map keys){
         secKeys = keys;
    }

  
   def getFuzzyAttributeAlgorithmMap(){
      if(fuzzyAttributeAlgorithmMap == null) return grailsApplication.config.idMatch.fuzzyAttributeAlgorithmMap;
      else return matchAttributeFuzzyAlgorithmMap;
   }
 
   def setFuzzyAttributeAlgorithmMap(java.util.Map info){
       fuzzyAttributeAlgorithmMap = info;
   }
}
