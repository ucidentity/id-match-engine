package dolphin

import grails.converters.JSON;

class ConfigController {

    def configService;
    def index() { render "hello Config"}
    def canonicalRules() {
      render configService.getCanonicalRules() as JSON; 
    }
    def fuzzyRules(){
      def rules = configService.getFuzzyRules();
      def algorithms = configService.getFuzzyAttributeAlgorithmMap();
      def responseMap = [:];
      responseMap << rules;
      responseMap << algorithms;
      render responseMap as JSON;
    }

    def schema(){
         render configService.getSchemaMap() as JSON; 

    }
}
