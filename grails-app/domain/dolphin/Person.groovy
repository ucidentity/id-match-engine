package dolphin

/*
Software License and Copyright notice:
http://ipira.berkeley.edu/software-copyright-notice-and-disclaimer
*/

class Person {

    static constraints = {
      uid unique: true, blank: false
      social unique: true
      firstName blank: false
      lastName blank: false
      
    }

    String uid
    String firstName
    String lastName
    String middle
    String dateOfBirth
    String social
    String other1
    String other2
    
}
