package dolphin

class TestingController {


    def testingService;


    def javaWhileNewSoundex(){
        String result =  testingService.javaWhileWithNewInstance(params.source, params.maxCount as int);
        render result;
     }

    def javaWhileSoundexService(){
        render testingService.javaWhileWithGrailsService(params.source, params.maxCount as int);
    }

    def javaForLoop(){
        render testingService.javaForLoop(params.source);

     }

    def groovyEachLoop(){
         render testingService.groovyEachLoop(params.source);
    }

   def groovyEachLoopWithGrailsService(){
        render testingService.groovyEachLoopWithGrailsService(params.source);
   }
 
    
   def index(){
      render "available actions <br>javaWhileType1 <br>javaWhileType2 <br>javaUserIter <br>groovyEachLoop <br> groovyEachLoopWithGrailsService</br> with params.source"
             
   }
 
}

