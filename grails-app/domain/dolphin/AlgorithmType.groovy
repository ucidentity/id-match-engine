package dolphin

/*
Software License and Copyright notice:
http://ipira.berkeley.edu/software-copyright-notice-and-disclaimer
*/

class AlgorithmType {

    static constraints = {
      name blank: false, unique: true, nullable: false
    }
  
   String name 
   String description
}
