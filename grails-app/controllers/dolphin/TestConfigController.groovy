package dolphin

import grails.converters.JSON;
import edu.berkeley.ucic.idmatch.User;

/**
 * testing only, discard after testing
 */
class TestConfigController {

    def grailsApplication;
    def testConfigService;

    def debugRules() { 
       def ruleMap = grailsApplication.config.idMatch.fuzzyMatchRuleSet2;
       def blockingFilterAttrs = ruleMap[0].blockingFilter;
       println blockingFilterAttrs.size;
       println blockingFilterAttrs[0];
       println blockingFilterAttrs[1];

       render ruleMap.each() { it.blockingFilter+"-"+it.matchAttributes};   


    }

    def getUsers(){
        
       def jsonDataMap = JSON.parse(request).data; 
       def rules = grailsApplication.config.idMatch.fuzzyMatchRuleSet2;
       rules.each() { rule ->
          def blockingFilterAttrs = rule.blockingFilter;
         testConfigService.getUsersForFilter(blockingFilterAttrs,jsonDataMap); 
       }
       render "getUsers done"

    }

    def testSql(){

       def mySql = "from User where attr4 != '12445'";
       println mySql;
       println User.findAll(mySql);
       render "what is going on";

    }
    
}
