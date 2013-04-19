package dolphin

import grails.converters.JSON;
import edu.berkeley.ucic.idmatch.User;
/**
 * service to return results for a given blocking filter
 *
 */
class TestConfigService {


    def grailsApplication;

    final String NOT_EQUALS = "<>";
    final String EQUALS = "=";
    final String NOT_EQUALS_FLAG="!=";
    final int PREFIX_LENGTH = 2;

    java.util.List  getUsersForFilter(java.util.List blockingFilter, java.util.Map jsonDataMap) {
        
        log.debug("getUsersForFilter");    
        java.util.List results = []; //create a empty result set
       
        //check if all attributes in blocking filter have non-null value in request
          int emptyAttributeCount = 0;
          blockingFilter.each() { attr ->
          def modifiedAttr; //name of attr after removing flag prefixes
          if(attr.contains(NOT_EQUALS_FLAG)) { 
             log.debug(attr);  modifiedAttr = attr.substring(NOT_EQUALS_FLAG.length()); 
          } else modifiedAttr = attr;
          log.debug("checking to see if ${modifiedAttr} value is missing in request");
          if(!jsonDataMap.has(modifiedAttr)){
             log.debug("found ${attr} empty in request"); 
             emptyAttributeCount = emptyAttributeCount+1; }
          } //done checking if all attr have values in request payload

        log.debug("returning if emptyAttributeCount is zero, and it is ${emptyAttributeCount}");
        //if an attribute has no value, then return empty list
        if(emptyAttributeCount != 0 ) return results;
        
        //if rule has more than one attribute, construct AND statement
        log.debug( blockingFilter.size() );
        def ruleStmt; //empty sql stmt
                blockingFilter.each{ attr ->
                    log.debug( "got ${attr} to make sql");
                    def modifiedAttr;
                    def sqlOperator;
                    if(attr.contains(NOT_EQUALS_FLAG)) {
                       modifiedAttr = attr.substring(2); 
                       sqlOperator = NOT_EQUALS;
                    }else {
                       modifiedAttr = attr;
                       sqlOperator = EQUALS;
                    }
                    def jsonInputValue = jsonDataMap.get(modifiedAttr);
                    def realColName = grailsApplication.config.idMatch.schemaMap.get(modifiedAttr);
                    //if sqlStmt is empty
                    if((ruleStmt == null)) ruleStmt = "${realColName} ${sqlOperator} '${jsonInputValue}'";
                    else ruleStmt = "${ruleStmt} AND ${realColName} ${sqlOperator} '${jsonInputValue}'";
                    log.debug(ruleStmt);
                } //for each attr loop
      //run sql
      def hqlStmt = "from User where ${ruleStmt}".trim();
      log.debug( hqlStmt );
      //results = User.findAll("${hqlStmt}"); // uses HQL
      results = User.findAll("from User where ssn <> '12345'");
      return results //return results
   }  
}
