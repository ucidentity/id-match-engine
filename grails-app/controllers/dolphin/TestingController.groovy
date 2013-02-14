package dolphin

class TestingController {

    def transpositionService;
    def editDistanceService;
    def soundexService;
    def canonicalRuleBuilderService;


    def transpose() { 
      def input = params.source;
      def users = User.list();
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
      def result =  canonicalRuleBuilderService.getMatches(params.source);
      render result; 

    }
 
    def editDistance(){

      def input = params.source;
      def users = User.list();
      def start =  new Date();
      def matchList = [];
      users.each { user ->
          println "running editDistance on ${input} and ${user.attr2}"
          if(editDistanceService.compare(input, user.attr2, 2 as String)) matchList.add(user);
       }
      def end = new Date();
      render "editDistance for ${User.count()} users started at ${start} ,and ended at ${end} with results ${matchList} "

     }
 
    def editDistanceOptimized() {}


    def soundex() {
      def input = params.source;
      def users = User.list();
      def start =  new Date();
      def matchList = [];
      users.each { user ->
          println "running soundex on ${input} and ${user.attr2}"
          if(soundexService.compare(input, user.attr2)) matchList.add(user);
       }
      def end = new Date();
      render "soundex for ${User.count()} users started at ${start} ,and ended at ${end} with results ${matchList} "

    }
 
    def soundexOptimized(){}

    def concurrent() {
        canonicalRuleBuilderService.serviceMethod();
        render "finished"

    }
}
