package dolphin
/*
Software License and Copyright notice:
http://ipira.berkeley.edu/software-copyright-notice-and-disclaimer
*/

class MatchRuleController {
   
 def scaffold = true;


 def index(){
  redirect (action: "form")
 }
 def add() {
    def m = new MatchRule();
    m.attribute = params.attribute;
    m.algorithm = params.algorithm;
    m.score = (new Integer(params.score)).intValue();
    if(params.distance!= null) m.distanceLength = params.distance;
    m.save(flush: true);
    redirect (action: "index")
    //render " ${rules}  attr selected are ${m.source} ,  ${m.target} ,  ${m.matchType} with score ${m.score} "
  }

 //this will redirect to the views/matchRule/form.jsp   
 def form(){
   def targetList = TargetAttribute.list();
   def targetNameList = [];
   targetList.each() { targetNameList.add(it.name) };
   def algoTypesList = AlgorithmType.list();
   def algoTypeNames = []
   algoTypesList.each() { algoTypeNames.add(it.name) };
   def rules = MatchRule.list();
   return [rules: rules, targetAttrList: targetNameList, algoTypeList : algoTypeNames]

  }

  
}
