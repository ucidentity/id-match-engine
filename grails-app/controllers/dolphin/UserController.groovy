package dolphin

class UserController {
 
    def userService;

    static  scaffold = true;

    def renewCache(){
       userService.renewCache();

    }
}
