package dolphin

class ConfigService {
 
    def grailsApplication;

    def getCanonicalRules() {
       return grailsApplication.idMatch.canonincalRules;
    }
    
}
