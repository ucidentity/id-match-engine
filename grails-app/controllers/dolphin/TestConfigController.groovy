package dolphin

import grails.converters.JSON;

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
       def blockingFilterAttrs = rules[0].blockingFilter;
       render testConfigService.getUsersForFilter(blockingFilterAttrs,jsonDataMap); 

    }
    
}
