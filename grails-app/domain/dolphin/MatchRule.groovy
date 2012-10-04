package dolphin
/*
Software License and Copyright notice:
http://ipira.berkeley.edu/software-copyright-notice-and-disclaimer
*/

class MatchRule {

    static constraints = {
     attribute blank: false, unique: true, nullable: false
     algorithm blank: false, nullable: false
     score blank: false, nullable: false
     distanceLength nullable: true
    }


    String attribute
    String algorithm
    int score
    int distanceLength //length used by edit distance algorithms
}
