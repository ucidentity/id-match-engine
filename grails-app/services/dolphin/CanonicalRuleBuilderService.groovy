package dolphin

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;

import groovy.transform.Synchronized;

class CanonicalRuleBuilderService {

  //get this from configuration
  private static final int NTHREDS = 10;
  static scope = "request";
  def transpositionService;



  def getMatches(String input) {
    log.debug("Enter");
      AtomicInteger atomicCounter = new AtomicInteger();
      ConcurrentHashMap atomicMap = new ConcurrentHashMap();
      atomicMap.clear();
      ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
      def users = User.list();
      def start =  new Date();
      users.each { user -> 
      //anonymous runnable
      Runnable worker = new Runnable(){
          public void run() {
            def i = atomicCounter.incrementAndGet(); 
            log.debug("calling run for "+i+" times");
            def compareResult = transpositionService.compare(input, user.attr2);
            if(compareResult) {
               log.debug("adding user to matchList");
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
    return "transpose for ${atomicCounter.intValue()} out of  ${User.count} started at ${start} ,and ended at ${end} with results ${atomicMap.size()} "
   
  }

 @Synchronized
  def updateMatchedResults(User user ){
       this.matchedList.add(user)
  }

  @Synchronized
  def updateThreadCounter(int i){
       this.i = i + 1;
  }


} 
