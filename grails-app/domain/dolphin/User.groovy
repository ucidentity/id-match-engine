package dolphin

class User {

    //can i force any attribute to be not null
    //uid can be one
    static constraints = {
      attr1 nullable: false
      attr2 nullable: true
      attr3 nullable: true
      attr4 nullable: true
      attr5 nullable: true
      attr6 nullable: true
      attr7 nullable: true
      attr8 nullable: true
      attr9 nullable: true
      attr10 nullable: true

    }
    
    String attr1 //uid or some other unique generic identifier relevant to your campus
    String attr2 //fname
    String attr3 //lname
    String attr4 //dob
    String attr5 //city
    String attr6 //ssn
    String attr7 //sex? M/F
    String attr8 //empid
    String attr9 //stuid
    String attr10 //affid
}
