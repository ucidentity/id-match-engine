package dolphin

/**
 * service to return results for a given blocking filter
 *
 */
class TestConfigService {

    final String NOT_EQUALS = "<>";
    final String EQUALS = "=";
    final String NOT_EQUALS_FLAG="<>";

    java.util.List  getUsersForFilter(String blockingFilter, java.util.Map jsonDataMap) {
    
        java.util.List results = []; //create a empty result set
       
        //check if all attributes in blocking filter have non-null value in request
          int emptyAttributeCount = 0;
          blockingFilter.each() { attr ->
          if(!jsonDataMap.has(attr)){
             log.debug("found ${attr} empty in request"); 
             emptyAttributeCount = emptyAttributeCount+1; }
          }
        //if an attribute has no value, then return empty list
        if(emptyAttributeCount == 0 ) return results;
        //build a sql string from blocking filter
           if(blockingFilter.size() == 1 ) {
             //if rule has only one attribute
             if(blockingFilter[0].contains(NOT_EQUALS_FLAG);
             def attr = blockingFilter[0].subString(2)
             else attr = blockingFilter[0];
             def realColName = grailsApplication.config.idMatch.schemaMap.get(attr);
             log.debug("real col name ${realColName}");
             def jsonInputValue = jsonDataMap.get(attr);
             log.debug("json value for this attr "+jsonInputValue);
             if(it[0].contains(NOT_EQUALS_FLAG))
             def ruleStmt = "${realColName} ${NOT_EQUALS} '${jsonInputValue}'"
             else
             def ruleStmt = "${realColName} ${EQUALS} '${jsonInputValue}'"
             log.debug("rule stmt is ${ruleStmt}");
             if(rulesCompositeStmt == null) rulesCompositeStmt  =  "(${ruleStmt})"; 
             else {rulesCompositeStmt = "${rulesCompositeStmt} OR (${ruleStmt})";}
             log.debug(rulesCompositeStmt);
          }else{
               //if rule has more than one attribute, construct AND statement
                log.debug( blockingFilter.size() );
                def ruleStmt;
                blockingFilter.each{ attr ->
                    log.debug( "got ${ attr} to make sql");
                    def realAttr;
                    if(attr.contains(NOT_EQUALS_FLAG);
                       realAttr = attr.subString(2); 
                    else
                       realAttr = attr;
                    jsonInputValue = jsonDataMap.get(realAttr);
                    def realColName = grailsApplication.config.idMatch.schemaMap.get(realAttr);
                    if((ruleStmt == null)) ruleStmt = "${realColName} ${EQUALS} '${jsonInputValue}'";
                    else ruleStmt = "${ruleStmt} AND ${realColName} {EQUALS} '${jsonInputValue}'";
                    log.debug(ruleStmt);
                } //for each attr loop
                //append to composite stmt 
                if(rulesCompositeStmt == null) rulesCompositeStmt  =  "(${ruleStmt})";
                else {rulesCompositeStmt = "${rulesCompositeStmt} OR (${ruleStmt})";}
                log.debug(rulesCompositeStmt);
         }
      } // for each rule loop
      //run sql
      def hqlStmt = "from User where ${rulesCompositeStmt}".trim();
      log.debug( hqlStmt );
      results = User.findAll("${hqlStmt}"); // uses HQL
      return results //return results
    }
}
