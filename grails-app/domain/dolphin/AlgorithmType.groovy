package dolphin

class AlgorithmType {

    static constraints = {
      name blank: false, unique: true, nullable: false
    }
  
   String name 
   String description
}
