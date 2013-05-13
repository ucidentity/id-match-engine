package edu.berkeley.ucic.idmatch

/**
 * this class has methods that validate the input schema
 * as well as rule schema
 */
class SchemaService {

    def grailsService;
    def configService;

    /*
     * if input has attributes that are not recognized by the schema
     * return false, else return true
     * for example, if input has dob attr, but dob is not declared in schema config,
     * then reject the input
     */
    def isInputSchemaValid(java.util.Map jsonDataMap) {
        log.debug("Enter isInputSchemaValid");
        def result = true;
        log.debug("jsonDataMap is"+jsonDataMap);
        def schemaMap = configService.getSchemaMap();
        def schemaMapKeySet = schemaMap.keySet();
        log.debug("schemaMap is "+schemaMap);
        jsonDataMap.each() { key, value ->
           if(!schemaMap.containsKey(key)) { log.debug(key + " containsKey check is false");
               result = false; }
        } 
        log.debug("Exit isInputSchameValid with return "+result);
        return result;
    }


   /**
    * if a rule has attributes that have no values in the request,
    * then remove this rule from the rules to execute
     * rule is skipped if there is no incoming request value for any of the attributes in the rule
     */
     def java.util.List getValidatedRules(java.util.Map jsonDataMap){
      def validatedRules = [];
      def canonicalRules = configService.getCanonicalRules();
      //def canonicalRules = grailsApplication.config.idMatch.canonicalMatchRuleSet;
      log.debug( "rules is "+canonicalRules);
      def schemaMap = configService.getSchemaMap();
      //def schemaMap = grailsApplication.config.idMatch.schemaMap;
      log.debug( "schemaMap is "+schemaMap);
      def jsonDataMapKey = jsonDataMap.keySet();
      log.debug( "json data key is "+jsonDataMapKey);
      //filter the rules and keep only those that have attr values in the request
      java.util.List validatedFuzzyRules = [];
      canonicalRules.each(){ rule ->
          int emptyAttributeCount = 0;
          rule.each() { attr ->
            def properAttr; //attr name after removing prefixes like != etc
                    if(attr.contains(NOT_EQUALS_FLAG)) {
                       properAttr = attr.substring(2);
                    }else {
                       properAttr = attr;
                    }

            log.debug("${jsonDataMap.has(properAttr)}");
            if(!jsonDataMap.has(properAttr)){log.debug("found ${attr} empty in request"); emptyAttributeCount = emptyAttributeCount+1; }
          }
          if(emptyAttributeCount == 0) validatedRules.add(rule);
      }

      return validatedRules;

     }


     /**
      * validate rule schema, if rules are constructed using attributes not present in the attr schema
      * return false and abort rule execution
      * TODO: implement in next release?
      */
      def isRuleSchemaValid(){ def result = false; return result;}

}
