package dolphin

import edu.berkeley.ucic.idmatch.*;
import org.codehaus.groovy.grails.web.json.JSONObject;
import grails.converters.JSON;

class ResetController {
   
    def personService;
    def pendingMatchService;
    def securityService;

    def index() {
       String method = "reset";
       log.debug("Enter: ${method}");
       if(!securityService.login(request)) {render(status : 401, text : http401Message ); return; }
       if(MockPerson.count()>0) {
          personService.deleteAll();
          java.util.List mockPersons = MockPerson.findAll();
          log.debug("Found ${mockPersons.size()} mockPersons");
          mockPersons.each(){ mockPerson ->
            def jsonDataMap = new JSONObject(mockPerson.jsonPerson);
            personService.createOrUpdate(jsonDataMap.SOR, jsonDataMap.sorId, jsonDataMap);
         }
       }
       if(MockPendingMatch.count()>0) {
            pendingMatchService.deleteAll();
            java.util.List mockPendingMatches = MockPendingMatch.findAll();
            log.debug("Found ${mockPendingMatches.size()} mockPending");
          mockPendingMatches.each(){ mockPendingMatch ->
            def jsonDataMap = new JSONObject(mockPendingMatch.jsonPendingMatch);
            pendingMatchService.createOrUpdate(jsonDataMap);
         }
       }
       log.debug("Exit:${method}");
       render("person is ${Person.count()} and pendingMatch is ${PendingMatch.count()}");
     }
   
    def createMockPerson() {
       String method = "createMockPerson"
       log.debug("Enter ${method}");
       def jsonDataMap = JSON.parse(request).toString();
       def mockPerson = new MockPerson("jsonPerson" : jsonDataMap).save(flush: true,failOnError : true);
       log.debug("Exit ${method} with ${mockPerson}")
       if(mockPerson == null){
          render(status : 409, text : "failed to create");
          return;}
       render(status : 200, text : "success creating");
    }

    def createMockPendingMatch() {
        String method = "createMockPendingMatch";
        log.debug("Enter ${method}");
        def jsonDataMap = JSON.parse(request).toString();
        def mockPM = new MockPendingMatch("jsonPendingMatch" : jsonDataMap).save(flush : true,failOnError : true);
        log.debug("Exit ${method} with ${mockPM}");
        if(mockPM == null) { 
          render(status : 409, text : "failed to create");
          return;}
        render(status : 200, text : "success creating");
    }

}
