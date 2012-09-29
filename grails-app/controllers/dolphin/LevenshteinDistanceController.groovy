package dolphin

class LevenshteinDistanceController {

  def editDistanceService;

    def index() { render "also called EditDistance </b> actions avl are computeDistance(s1,t1) </br> and computeNormalizedScore(s1,t1)"; }
    
    def computeDistance(String s1, String t1){
      render "Levenshtein distance for ${params.s1} to ${params.t1} is ${editDistanceService.computeDistance(params.s1,params.t1)}";

    }

    def computeNormalizedScore(String s1, String t1){

      render "Levenshtein normalized score for ${params.s1} to ${params.t1} is ${editDistanceService.computeNormalizedScore(params.s1,params.t1)}";

    }

}
