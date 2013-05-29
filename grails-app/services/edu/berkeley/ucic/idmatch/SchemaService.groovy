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
      String method = "getValidatedCanonicalRules"
      log.debug("Enter: ${method} with ${jsonDataMap}");

      def asIsRules = configService.getCanonicalRules();
      log.debug("asIsRules found are "+asIsRules);

      def schemaMap = configService.getSchemaMap();
      log.debug( "schemaMap is "+schemaMap);

      java.util.List validatedRules = [];

      //filter the rules and keep only those that have attr values in the request
      asIsRules.each(){ rule ->
          log.debug("validating rule ${rule}");
          int emptyAttributeCount = 0;
          rule.each() { attr ->
            log.debug("validating ${attr}");
            def properAttr; //attr name after removing prefixes like != etc
            if(attr.contains(NOT_EQUALS_FLAG)) {
                  properAttr = attr.substring(2);
            }else {
                  properAttr = attr;
            }
            log.debug("is ${properAttr} in jsonDataMap -> ${jsonDataMap.containsKey(properAttr)}");
            if(!jsonDataMap.containsKey(properAttr)){
                     log.debug("${properAttr} is missing in jsonDataMap, will skip this rule"); 
                     emptyAttributeCount = emptyAttributeCount+1; 
            }
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
      String method = "getValidatedFuzzyRules";
      log.debug("Enter: ${method} with jsonDataMap ${jsonDataMap}");

      def asIsRules = configService.getFuzzyRules();
      log.debug("${asIsRules.size()} fuzzy rules found ->  ${asIsRules}");

      def schemaMap = configService.getSchemaMap();
      log.debug( "schemaMap is "+schemaMap);

      //filter the rules and keep only those that have attr values in the request
      java.util.List validatedRules = [];
      asIsRules.each(){ rule ->
          log.debug("rule being validated is ${rule}");
          int emptyAttributeCount = 0;
          rule.matchAttributes.each() { attr ->
            log.debug("checking prefix in matchAttribute "+attr);
            def properAttr; //attr name after removing prefixes like != etc
                    if(attr.contains(NOT_EQUALS_FLAG)) {
                       properAttr = attr.substring(2);
                    }else {
                       properAttr = attr;
                    }
            if(!jsonDataMap.containsKey(properAttr)){
               log.debug("found ${properAttr} empty in request"); 
               emptyAttributeCount = emptyAttributeCount+1; 
               rule.matchAttributes = [] //empty the rule so .each can exit
             }
          }//matchAttributes.each
          if(emptyAttributeCount == 0) validatedRules.add(rule);
      }//asIsRules.each
      log.debug("Exit: ${method} with ${validatedRules.size()} validatedRules");
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
           String method = "isFuzzyAttributeAlgorithmConfigured";
           log.debug("Enter: ${method} for ${fuzzyAttributes}");
            def result = true;
            def fuzzyAttributeAlgorithmMap = configService.getFuzzyAttributeAlgorithmMap();
            log.debug("fuzzyAttributeAlgorithmMap is"+fuzzyAttributeAlgorithmMap);
            fuzzyAttributes.each(){ attr ->
                log.debug("finding matchType for "+attr);
                log.debug(fuzzyAttributeAlgorithmMap.get(attr));
                if( fuzzyAttributeAlgorithmMap.get(attr)?.matchType == null) result = false;
            }
            log.debug("Exit: ${method} returning with ${result}");
            return result;

      }

     /**
      * transforms entries in the results into entries that have only visible attributes
      * 
      */
      def java.util.List personSummaryAdapter(java.util.List results){
         String method = "personSummaryAdapter"
         log.debug("Enter: ${method} with input list of size "+results?.size());
         def summaryResults = [];
         results.each(){ person ->
               java.util.Map summaryPerson = [:];
               summaryPerson.id = person.id;
               summaryPerson.SOR = person.SOR;
               summaryPerson.sorId = person.sorId;
               //TODO: remove the following lines and add code to pull from schame mapping
               summaryPerson.lName = person.attr5; //TODO: change attr5 to lName in domain class
               summaryPerson.fName = person.attr6; //TODO : change attr6 to fName in domain class
               summaryPerson.dobMM = person.attr8;
               summaryPerson.dobDD = person.attr9;
               summaryResults << summaryPerson;

        }
        log.debug("Exit: ${method} with return list of size "+summaryResults?.size());
        return summaryResults;

     }


}
