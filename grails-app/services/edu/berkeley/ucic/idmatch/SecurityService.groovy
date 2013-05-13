package edu.berkeley.ucic.idmatch

class SecurityService {
    def grailsApplication;

    def login(javax.servlet.http.HttpServletRequest request) {
      log.debug "Enter: login";
      def success = true;
      def failed = false;
      def clientId = request.getHeader("clientId");
      def password = request.getHeader("password");
      if((clientId == null)|| (password == null)) return failed;
      println "clientId and password are " +clientId+" "+password;
      def securityKeySet = grailsApplication.config.idMatch.securityKeys;
     
      def realPassword = securityKeySet.get(clientId.trim());
      if(realPassword == null ) return failed;
      println "realpassword is "+realPassword;
      println "SecurityService.login exit";
      log.debug("Exit: login with result "+realPassword.equals(password));
      if(realPassword.equals(password)) return success else return failed;

    }
}
