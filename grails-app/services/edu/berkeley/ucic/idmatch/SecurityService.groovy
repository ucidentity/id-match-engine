package edu.berkeley.ucic.idmatch

class SecurityService {

    def  configService;

    def boolean login(javax.servlet.http.HttpServletRequest request) {
      log.debug "Enter: login";
      def result = false; //defaulting to false
      def clientId = request.getHeader("clientId");
      def password = request.getHeader("password");
      if((clientId == null)|| (password == null)) return result;
      println "clientId and password are " +clientId+" "+password;
      def securityKeySet = configService.getSecurityKeys();
      def realPassword = securityKeySet.get(clientId.trim());
      log.debug("realPassword and password are "+realPassword+"-"+password);
      if((realPassword == null) || (!realPassword.equals(password)) ) result = false;
      else result = true;
      log.debug("Exit: login with result "+result);
      return result;
    }

}
