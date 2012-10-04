/* 
 * Copyright ©2012. The Regents of the University of California (Regents).
 * All Rights Reserved. Permission to use, copy, modify, and distribute this
 * software and its documentation for educational, research, and not-for-profit
 * purposes, without fee and without a signed licensing agreement, is hereby
 * granted, provided that the above copyright notice, this paragraph and the
 * following two paragraphs appear in all copies, modifications, and
 * distributions. Contact The Office of Technology Licensing, UC Berkeley, 2150
 * Shattuck Avenue, Suite 510, Berkeley, CA 94720-1620, (510) 643-7201, for
 * commercial licensing opportunities.
 * 
 * Created by Venu Alla, CalNet, IST, University of California, Berkeley
 * 
 * IN NO EVENT SHALL REGENTS BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
 * SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
 * ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * REGENTS HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * REGENTS SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY, PROVIDED
 * HEREUNDER IS PROVIDED "AS IS". REGENTS HAS NO OBLIGATION TO PROVIDE
 * MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 */

package dolphin

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
