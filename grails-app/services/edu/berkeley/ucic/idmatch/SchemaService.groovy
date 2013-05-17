package edu.berkeley.ucic.idmatch

/**
 * this class has methods that validate the input schema
 * as well as rule schema
 */
class SchemaService {

    def grailsService;
    def configService;
    final String EQUALS = "=";
    final String NOT_EQUALS="!=";
    final String NOT_EQUALS_FLAG=NOT_EQUALS;
    final int PREFIX_LENGTH = 2;

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
   def java.util.List getValidatedCanonicalRules(java.util.Map jsonDataMap){
      log.debug("Enter getValidatedCanonicalRules");
      log.debug( "json data is "+jsonDataMap);

      def asIsRules = configService.getCanonicalRules();
      log.debug("asIsRules found as "+asIsRules);

      def schemaMap = configService.getSchemaMap();
      log.debug( "schemaMap is "+schemaMap);

      java.util.List validatedRules = [];

      //filter the rules and keep only those that have attr values in the request
      asIsRules.each(){ rule ->
          log.debug("validating rule "+rule);
          int emptyAttributeCount = 0;
          rule.each() { attr ->
            log.debug("checking prefix in "+attr);
            def properAttr; //attr name after removing prefixes like != etc
                    if(attr.contains(NOT_EQUALS_FLAG)) {
                       properAttr = attr.substring(2);
                    }else {
                       properAttr = attr;
                    }
            if(jsonDataMap."${properAttr}" == null){log.debug("found ${properAttr} empty in request"); 
                                                    emptyAttributeCount = emptyAttributeCount+1; }
          }
          if(emptyAttributeCount == 0) validatedRules.add(rule);
      }
      log.debug("Exit getValidatedRules with validatedRules size of "+validatedRules.size());
      return validatedRules;
    }
  

    /**
     * remove fuzzy rules that have no attr values in the request
     *
     */ 
    def java.util.List getValidatedFuzzyRules(java.util.Map jsonDataMap){
      log.debug("Enter getValidatedFuzzyRules");
      log.debug( "json data is "+jsonDataMap);
      def schemaMap = configService.getSchemaMap();
      log.debug( "schemaMap is "+schemaMap);
      def asIsRules = configService.getFuzzyRules();
      log.debug("fuzzy rules as found are "+asIsRules);
      java.util.List validatedRules = [];
      //filter the rules and keep only those that have attr values in the request
      asIsRules.each(){ rule ->
          log.debug("rule being validated is "+rule);
          int emptyAttributeCount = 0;
          rule.matchAttributes.each() { attr ->
            log.debug("checking prefix in "+attr);
            def properAttr; //attr name after removing prefixes like != etc
                    if(attr.contains(NOT_EQUALS_FLAG)) {
                       properAttr = attr.substring(2);
                    }else {
                       properAttr = attr;
                    }

            if(jsonDataMap.get(properAttr) == null){log.debug("found ${properAttr} empty in request"); emptyAttributeCount = emptyAttributeCount+1; }
          }
          if(emptyAttributeCount == 0) validatedRules.add(rule);
      }
      log.debug("Exit getValidatedFuzzyRules with validatedRules size of "+validatedRules.size());
      return validatedRules;
    }
     

     /**
      * validate rule schema, if rules are constructed using attributes not present in the attr schema
      * return false and abort rule execution
      * TODO: implement in next release?
      */
      def isRuleSchemaValid(){ def result = false; return result;}

   
     /**
      * validate if fuzzy rule attrs have corresponding Fuzzy Algorithms set
      * return false even if one attribute in the list has mising Algorithm setup
      */
      def isFuzzyAttributeAlgorithmConfigured(java.util.List fuzzyAttributes){
           log.debug("Enter: isFuzzyAttributeAlgorithmConfigured for "+fuzzyAttributes);
            def result = true;
            def fuzzyAttributeAlgorithmMap = configService.getFuzzyAttributeAlgorithmMap();
            fuzzyAttributes.each(){ attr ->
                if( fuzzyAttributeAlgorithmMap.attr.matchType == null) result = false;
            }
            log.debug("Enter: isFuzzyAttributeAlgorithmConfigured returning with "+result);
            return result;

      }

}
