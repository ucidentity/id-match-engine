package dolphin

class UserService {
 
    def users;

    def getCache() {
      if(users==null) users = User.list();
      return users;
    }
}
