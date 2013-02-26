package dolphin

class UserService {
 
    static def users;

    def getCache() {
      warmUpCache();
      return users;
    }

   def warmUpCache(){
      if(users == null) users = User.list();
   }
}
