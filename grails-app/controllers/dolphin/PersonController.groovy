package dolphin

/*
Software License and Copyright notice:
http://ipira.berkeley.edu/software-copyright-notice-and-disclaimer
*/



import grails.converters.JSON
import grails.converters.XML
import org.codehaus.groovy.grails.web.servlet.mvc.*;


class PersonController {
  
  def matchingService;

  def scaffold = true;

  def search(){
       def persons = Person.list();
       return [ persons: persons ]
   }
   
  def match(){
     println "params passed are ${params}"
     def persons = Person.list();
     def rules = MatchRule.list();
     def exactMatchMap= [:];
     def reconMatchMap = [:];
     persons.each(){person ->

        def personScore = 0;
        rules.each() { rule ->
            personScore = personScore + matchingService.executeRule(rule,person,params);
        }
       if(personScore >= 90 ) exactMatchMap.put(person.firstName+"-"+person.lastName,personScore);
       if(personScore < 90)  reconMatchMap.put(person.firstName+"-"+person.lastName,personScore);

      }
     return [ persons: persons, exactMatchMap: exactMatchMap, reconMatchMap: reconMatchMap]
   }


  def match2(){
     println "params passed are ${params}"
     def jsonElement = JSON.parse(request);
     println "json element is ${jsonElement}"
     println "json data element is ${jsonElement.data}"
     def persons = Person.list();
     def rules = MatchRule.list();
     def exactMatchMap= [:];
     def reconMatchMap = [:];
     persons.each(){person ->

        def personScore = 0;
        rules.each() { rule ->
            personScore = personScore + matchingService.executeRule(rule,person,jsonElement.data);
        }
       def resultPerson = person;
       if(personScore >= 90 ) exactMatchMap.put(person.uid,resultPerson);
       if(personScore < 90  && personScore > 50)  reconMatchMap.put(person.uid,resultPerson);
      }
      def resultList = [];
      resultList.add(exactMatchMap);
      resultList.add(reconMatchMap);
      render resultList as JSON;
  }

   

   def xml(){
      def persons = Person.list();
      render persons as XML;
   }
 
   def json(){
      def persons = Person.list();
      render persons as JSON;
   }

}
