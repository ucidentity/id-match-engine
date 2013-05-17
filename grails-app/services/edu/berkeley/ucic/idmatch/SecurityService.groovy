package edu.berkeley.ucic.idmatch

class SecurityService {

    def  configService;

    def boolean login(javax.servlet.http.HttpServletRequest request) {
      log.debug "Enter: login";
      def success = true;
      def failed = false;
      def result = failed; //defaulting to false
      def clientId = request.getHeader("clientId");
      def password = request.getHeader("password");
      if((clientId == null)|| (password == null)) return failed;
      println "clientId and password are " +clientId+" "+password;
      def securityKeySet = configService.getSecurityKeys();
     
      def realPassword = securityKeySet.get(clientId.trim());
      if((realPassword == null) || (!realPassword.equals(password)) ) result = failed;
      else result = success;
      log.debug("Exit: login with result "+result);
      return result;
    }

}
