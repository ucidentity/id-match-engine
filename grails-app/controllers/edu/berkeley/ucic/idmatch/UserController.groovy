package edu.berkeley.ucic.idmatch 

/*
 * TODO: this may not ever be called directly as a http request
 * this may have to go, as it is just a web frontend to userService 
 */
class UserController {
 
    def userService;

    static  scaffold = true;

    def renewCache(){
       userService.renewCache();
       render "users size in cache is ${userService.users.size()}";
    }
}
