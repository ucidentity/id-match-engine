package dolphin

class UserController {
 
    def userService;

    static  scaffold = true;

    def renewCache(){
       userService.renewCache();
       render "users size in cache is userService.users.size()";
    }
}
