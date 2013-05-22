package edu.berkeley.ucic.idmatch

class SecurityService {

    def  configService;

    def boolean login(javax.servlet.http.HttpServletRequest request) {
      log.debug "Enter: login";
      def result = false; //defaulting to false
      def clientId = request.getHeader("clientId");
      def password = request.getHeader("password");
      if((clientId == null)|| (password == null)) return result;
      log.debug("clientId and password in request are " +clientId+" "+password);
      def securityKeyMap = configService.getSecurityKeys();
      log.debug("securityKeyMap is ${securityKeyMap}");
      def realPassword = securityKeyMap.get(clientId.trim()); //get the password
      log.debug("real password for ${clientId} is ${realPassword}");
      if((realPassword == null) || (!realPassword.equals(password)) ) result = false;
      else result = true;
      log.debug("Exit: login with result "+result);
      return result;
    }

}
