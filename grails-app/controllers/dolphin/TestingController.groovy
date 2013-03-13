package dolphin

class TestingController {


    def testingService;


    def javaWhileType1(){
        String result =  testingService.javaWhileWithNewInstance(params.source);
        render result;
     }

    def javaWhileType2(){
        render testingService.javaWhileWithGrailsService(params.source);
    }

    def javaUserIter(){
        render testingService.javaUserIter(params.source);

     }

    def groovyUserIter(){
         render testingService.groovyUserIter(params.source);
    }
 
    
   def index(){

      render "available actions <br>javaWhileType1 <br>javaWhileType2 <br>javaUserIter <br>groovyUserIter <br> with params.source"

   }
 
}

