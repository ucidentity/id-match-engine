package dolphin

class TestConfigController {

    def grailsApplication;

    def index() { 
       def ruleMap = grailsApplication.config.idMatch.fuzzyMatchRuleSet2;
       def blockingFilterAttrs = ruleMap[0].blockingFilter;
       println blockingFilterAttrs.size;
       println blockingFilterAttrs[0];
       println blockingFilterAttrs[1];

       render ruleMap.each() { it.blockingFilter+"-"+it.matchAttributes};   


    }
}
