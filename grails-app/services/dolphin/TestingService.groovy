package dolphin

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;

import edu.ualr.oyster.utilities.Soundex;
import edu.ualr.oyster.utilities.*;

import org.apache.commons.lang3.RandomStringUtils;


class TestingService {
 
  def grailsApplication;

  //get this from configuration
  private static final int NTHREDS = 8;//grailsApplication.config.idMatch.THREADS;
  //static scope = "request";
  def transpositionService;
  def editDistanceService;
  def soundexService;
  def userService;


  /*
   * multi-threading
   */
  def runConcurrentMatch(String input) {
    log.debug("Enter");
      AtomicInteger atomicCounter = new AtomicInteger();
      ConcurrentHashMap atomicMap = new ConcurrentHashMap();
      atomicMap.clear();
      ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
      def users = userService.getCache();
      def userCount = users.size();
      def start =  new Date();
      users.each { user ->
      //anonymous runnable
      Runnable worker = new Runnable(){
          public void run() {
            def i = atomicCounter.incrementAndGet();
            def compareResult;
            log.debug("calling run for "+i+" times");
            compareResult = soundexService.compare(input,user.attr2);
            if(compareResult) {
               log.debug("adding ${user.attr2} to matchList");
               atomicMap.putIfAbsent(user.id,user);
            }
          } //run()
      } //Runnable{}
      executor.execute(worker); //add this runnable to the executor service
   }
  
    // This will make the executor accept no new threads
    // and finish all existing threads in the queue
    executor.shutdown();
    // Wait until all threads are finished
    while (!executor.isTerminated()) {}
    def end = new Date();
    log.debug("Exit");
def timeTaken = (end.getTime() - start.getTime())/1000;
    log.debug("Exit");
    return "Soundex for ${atomicCounter.intValue()} out of  ${userCount} started at ${start} ,and took ${timeTaken} with results ${atomicMap.size()} and ${atomicMap} "
 
  }


   /*
     * a test to time a simple while for 200K
     * for each iteration, call Soundex.compare method
     * sample results on my laptop in millisecs: 1290,1413,1406,1391
     */
    def javaWhileWithNewInstance(String source){
     int counter = 0;
     int matchCounter = 0;
                //Soundex soundex  = new Soundex();
                String serviceName = "edu.ualr.oyster.utilities."+"Soundex";
                def  soundex = this.class.classLoader.loadClass(serviceName, true)?.newInstance()
                String randomSt = RandomStringUtils.random(5, "ABCDEFGHIJKLMNOPGRSTUVXYZ");
                long startTime = new Date().getTime();
                while(counter < 500*1000){
                        counter = counter+1;
                        if(soundex.compareSoundex(source, randomSt)){ matchCounter  = matchCounter + 1 };
                }
        long endTime = new Date().getTime();
        System.out.println("Time lapsed for "+counter+" is "+(endTime - startTime) + " found matches "+matchCounter);
        return  "Time lapsed is for "+counter+" is "+(endTime - startTime);
   }

   /*
     * a test to time a simple while for 200K
     * for each iteration, call Soundex.compare method
     * sample results on my laptop in millisecs: 1290,1413,1406,1391
     */
    def javaWhileWithGrailsService(String source){
     int counter = 0;
     int matchCounter = 0;
                String randomSt = RandomStringUtils.random(5, "ABCDEFGHIJKLMNOPGRSTUVXYZ");
                long startTime = new Date().getTime();
                while(counter < 200*1000){
                        counter = counter+1;
                        if(soundexService.compare(source, randomSt)){ matchCounter  = matchCounter + 1 };
                }
        long endTime = new Date().getTime();
        System.out.println("Time lapsed for "+counter+" is "+(endTime - startTime) + " found matches "+matchCounter);
        return "Time lapsed is for "+counter+" is "+(endTime - startTime);
   }

   /*
   * a test to time the for loop instead of the groovy users.each loop
   * for each user, call Soundex.compare method
   * sample results on my laptop for 200K users in db:
   * when about 3.2G mem avl in millisecs: 3094, 2985,3007,3005
   */
   def javaUserIter(String source){
     java.util.List matchList = [];
     String serviceName = "edu.ualr.oyster.utilities."+"Soundex";
     def  soundex = this.class.classLoader.loadClass(serviceName, true)?.newInstance()
     java.util.List users = userService.getCache();
     long start = new java.util.Date().getTime();
     for(User user: users) { 
                             if(soundex.compareSoundex(source, user.attr2)) matchList.add(user); }
     long end = new java.util.Date().getTime();
     log.debug("done ${end-start} and ${matchList}");
     return " ${new Date()} : done ${end-start}";

   }

    /*
     * test to time Soundex.compare invocations for N times
     * sample results on mac laptop for 200K users: 29263, 29029, 28980, 28992
     * with grails restart and 3G free memory avl in millisecs: 14607,11673,14408,
     */
    def groovyUserIter(String source) {
      def users = userService.getCache();
      def userCount = users.size();
      def matchList = [];
      String serviceName = "edu.ualr.oyster.utilities."+"Soundex";
      def  soundex = this.class.classLoader.loadClass(serviceName, true)?.newInstance()
      long start =  new Date().getTime();
      users.each { user ->
          if(soundex.compareSoundex(source, user.attr2)) matchList.add(user);
       }
      long end = new Date().getTime();
      return "${new Date() }: soundex for ${userCount} users took ${end-start} ms with results ${matchList} "

    }


}
