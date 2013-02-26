package dolphin

import edu.berkeley.calnet.ims.OysterClient;

import edu.ualr.oyster.utilities.OysterEditDistance;
import edu.ualr.oyster.utilities.CharacterSubstringMatches;
import edu.ualr.oyster.utilities.Soundex;
import edu.ualr.oyster.utilities.OysterUtilityTranspose;

import org.apache.commons.lang3.RandomStringUtils;

class TestingController {

    def transpositionService;
    def editDistanceService;
    def soundexService;
    def fuzzyMatchService;
    def userService;


    def transposeUnit(){
       if(transpositionService.compare(params.s1, params.t1))
        render "${params.s1} and ${params.t1} compare is true"
       else render "${params.s1} and ${params.t1} compare is false"
     }


   /*
    *
    */
    def transpose() { 
      def input = params.source;
      def users = userService.getCache();
      def start =  new Date();
      def matchList = [];
      users.each { user ->
          println "running transpose on ${input} and ${user.attr2}"
          if(transpositionService.compare(input, user.attr2)) matchList.add(user);      
       }
      def end = new Date();
      render "transpose for ${User.count()} users started at ${start} ,and ended at ${end} with results ${matchList} " 
     }

   
   def transposeOptimized() {
      def result =  fuzzyMatchService.getTransposeMatches(params.source);
      render result; 

    }
 

    def editDistanceUnit(){
    def compareResult = editDistanceService.compare(params.s, params.t,"1");
    render "${compareResult} for ${params.s} and ${params.t}"
    }

  
    def editDistance(){
      def input = params.source;
      def users = userService.getCache();
      def start =  new Date();
      def matchList = [];
      users.each { user ->
          println "running editDistance on ${input} and ${user.attr2}"
          if(editDistanceService.compare(input, user.attr2, 2 as String)) matchList.add(user);
       }
      def end = new Date();
      render "editDistance for ${User.count()} users started at ${start} ,and ended at ${end} with results ${matchList} "

     }
 
    def editDistanceOptimized() {
          def result = fuzzyMatchService.getEditDistanceMatches(params.source);
          render result;
     }


    def soundex() {
      def input = params.source;
      def users = userService.getCache();
      long start =  new Date().getTime();
      def matchList = [];
      users.each { user ->
          println "running soundex on ${input} and ${user.attr2}"
          if(soundexService.compare(input, user.attr2)) matchList.add(user);
       }
      long end = new Date().getTime();
      render "soundex for ${User.count()} users took ${end-start} ms with results ${matchList} "

    }
 
    def soundexOptimized(){
     def result = fuzzyMatchService.getSoundexMatches(params.source);
          render result;
     }


    def fuzzyRule(){
         def result = fuzzyMatchService.getMatchesByRule(params.source);
         render result;
     }

    def javaWhile(){
     int counter = 0;
     int matchCounter = 0;
		Soundex soundex  = new Soundex();
		OysterEditDistance oed = new OysterEditDistance();
		OysterUtilityTranspose transpose = new OysterUtilityTranspose();
		
		long startTime = new Date().getTime();
		while(counter < 200*1000){
			counter = counter+1;
			String randomSt = RandomStringUtils.random(5, "ABCDEFGHIJKLMNOPGRSTUVXYZ");
			if(soundex.compareSoundex("hello", randomSt)){ matchCounter  = matchCounter + 1 };
		}
        long endTime = new Date().getTime();
        System.out.println("Time lapsed for "+counter+" is "+(endTime - startTime) + " found matches "+matchCounter);		
        render "Time lapsed is for "+counter+" is "+(endTime - startTime);
   }

   def javaUserIter(){
     java.util.List matchList = [];
     Soundex soundex = new Soundex();
     java.util.List users = userService.getCache();
     long start = new java.util.Date().getTime();
     for(User user: users) { log.debug("say something ${user.attr2}"); 
                             if(soundex.compareSoundex("karl", user.attr2)) matchList.add(user); }
     long end = new java.util.Date().getTime();
     log.debug("done ${end-start} and ${matchList}");
     render " ${new Date()} : done ${end-start}";

   }
 
   def javaUserIterOptimized(){
             def result = fuzzyMatchService.getMatchesByJavaIter(params.source);
             render result;
   }

}

