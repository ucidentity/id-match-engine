package dolphin

import edu.ualr.oyster.utilities.*;

class MatchingService {
 

    def soundexService;

   //pass MatchRule, Person and Params object
   //return the score of that rule if match found
    def executeRule(MatchRule rule, Person person, org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap params) {
      def result = 0;
      def attr = rule.attribute
      def score = rule.score
      def algorithm = rule.algorithm
      switch (rule.attribute) {
         case "social":
             if(runAlgorithm(person.social,params.social,algorithm) < 3){ println("returning ${score}"); return score;} else { println "returning zero"; return 0;}
         case "firstname":
             if(runAlgorithm(person.firstName,params.firstName,algorithm)) return score; else return 0;
         case "lastname":
             if(runAlgorithm(person.lastName,params.lastName,algorithm)) return score; else return 0;
         case "dob":
             if(runAlgorithm(person.dateOfBirth,params.dateOfBirth,algorithm)) return score; else return 0;
         default:
             return 0;
    }
    }

   //pass Algorithm, stringA, stringB
   def runAlgorithm(String stringA, String stringB,String algorithm){
 
         log.info  "the passed in attr are  ${stringA} and ${stringB} and ${algorithm}"
       switch (algorithm) {
         case "Soundex":
               def result = soundexService.compare(stringA,stringB);
               println "the result of compare is "+result;
               return result;
         case "NYSIIS":
               return NYSIISService.compare(stringA,stringB);
         case "DaitchMokotoff":
              return  DaitchMokotoffService.compare(stringA,stringB);
         case "Levenstein":
               return new OysterEditDistance().computeDistance(stringA,stringB);
         case "JaroWinkler":
              return JarowinklerDistanceService.computeDistance(stringA,stringB)
         default:
             return 0;



   }
}

}
