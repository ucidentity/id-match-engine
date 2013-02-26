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


    /*
     * this is a test to time users.count invocations of transpositionService.compare
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

  
   /*
    * same as above except that it involves multi-threading
    */ 
   def transposeOptimized() {
      def result =  fuzzyMatchService.getTransposeMatches(params.source);
      render result; 

    }
 

    /*
     * this is a test to time N invocations of editDistanceService.compare 
     */
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
 
    /*
     * same as the above except multi-threaded 
     */
    def editDistanceOptimized() {
          def result = fuzzyMatchService.getEditDistanceMatches(params.source);
          render result;
     }


    /*
     * test to time Soundex.compare invocations for N times
     * sample results on mac laptop for 200K users: 29263, 29029, 28980, 28992
     * with grails restart and 3G free memory avl: 14607,11673,14408,
     */
    def groovyUserIter() {
      def input = params.source;
      def users = userService.getCache();
      def userCount = users.size();
      long start =  new Date().getTime();
      def matchList = [];
      users.each { user ->
          println "running soundex on ${input} and ${user.attr2}"
          if(soundexService.compare(input, user.attr2)) matchList.add(user);
       }
      long end = new Date().getTime();
      render "${new Date() }: soundex for ${userCount} users took ${end-start} ms with results ${matchList} "

    }

   /*
    * same as above except that multi-threading is involved
    * sample test results with 8 threads and 200K db: 13127, 12941, 12937,
    * after restart of grails and with 3G free memory avl: 11921, 11783,11776 
    */ 
    def soundexOptimized(){
     def result = fuzzyMatchService.getSoundexMatches(params.source);
          render "${new Date()}"+ result;
     }


    /* 
     * ignore this for now, was meant to execute a Fuzzy Match Rule as configured
     */
    def fuzzyRule(){
         def result = fuzzyMatchService.getMatchesByRule(params.source);
         render result;
     }


    /*
     * a test to time a simple while for 200K
     * for each iteration, call Soundex.compare method
     * sample results on my laptop in millisecs: 1290,1413,1406,1391 
     */
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

  /*
   * a test to time the for loop instead of the groovy users.each loop
   * for each user, call Soundex.compare method
   * sample results on my laptop for 200K users in db: 
   * when about 3.2G mem avl: 3094, 2985,3007,3005
   */
   def javaUserIter(){
     java.util.List matchList = [];
     Soundex soundex = new Soundex();
     String source = params.source;
     java.util.List users = userService.getCache();
     long start = new java.util.Date().getTime();
     for(User user: users) { log.debug("say something ${user.attr2}"); 
                             if(soundex.compareSoundex(source, user.attr2)) matchList.add(user); }
     long end = new java.util.Date().getTime();
     log.debug("done ${end-start} and ${matchList}");
     render " ${new Date()} : done ${end-start}";

   }
 
  /*
   * not implemented
   * this method is similar to javaUserIter except that concurrency is involved
   */ 
  def javaUserIterOptimized(){
             def result = fuzzyMatchService.getMatchesByJavaIter(params.source);
             render result;
   }

}

