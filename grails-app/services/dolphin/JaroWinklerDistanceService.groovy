/* 
 * Copyright ©2012. The Regents of the University of California (Regents).
 * All Rights Reserved. Permission to use, copy, modify, and distribute this
 * software and its documentation for educational, research, and not-for-profit
 * purposes, without fee and without a signed licensing agreement, is hereby
 * granted, provided that the above copyright notice, this paragraph and the
 * following two paragraphs appear in all copies, modifications, and
 * distributions. Contact The Office of Technology Licensing, UC Berkeley, 2150
 * Shattuck Avenue, Suite 510, Berkeley, CA 94720-1620, (510) 643-7201, for
 * commercial licensing opportunities.
 * 
 * Created by Venu Alla, CalNet, IST, University of California, Berkeley
 * 
 * IN NO EVENT SHALL REGENTS BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
 * SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
 * ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * REGENTS HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * REGENTS SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY, PROVIDED
 * HEREUNDER IS PROVIDED "AS IS". REGENTS HAS NO OBLIGATION TO PROVIDE
 * MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 */

package dolphin;


/*
 * http://en.wikipedia.org/wiki/Jaro-Winkler_distance
 * 
 */
class JaroWinklerDistanceService {
/*
 * the following implementation is copied from FRIL project by Emory Univ + CDC
 * ref: fril.sourceforge.net
 * The higher the Jaro–Winkler distance for two strings is, the more similar the strings are. 
 * The Jaro–Winkler distance metric is designed and best suited for short strings such as person names. 
 * The score is normalized such that 0 equates to no similarity and 1 is an exact match.
 */

/*
 * allow these to be set via constructor?
 */
private int maxPrefLength = 4; //also called as length of common prefix, is 2 for Dixon and Dicson, is 3 for MARTHA and MARHTA
private double weight = 0.1; //also called scaling_factor p, defaulted to 0.1


        def setPrefixLength(int _maxPrefLength){
                println "max prefix length is ${maxPrefLength} ";
                maxPrefLength = _maxPrefLength;

        }
   
        def setScalingFactor(double _weight){weight = _weight;
              println "scaling factor p is ${weight} ";
        }
/*
 *  the Jaro-Winkler score/distance
 */    
public double getDistance(String s1, String s2) {
                s1 = s1.toLowerCase();
                s2 = s2.toLowerCase();
                double dist = score(s1, s2);
                dist = dist + commonPrefix(s1, s2, maxPrefLength) * weight * (100 - dist);
                if (dist < 0) dist = 0;
                if (dist > 100) dist = 100;
                return dist;
        }
/* 
 * the Jaro score
 */
private double score(String s1,String s2) {

                /**
                 * TODO: Maybe the commented one is better to solve switched first/last names
                 * Is there any paper to acknowledge that?
                 */
                int limit = (s1.length() > s2.length()) ? s2.length()/2 + 1 : s1.length()/2 + 1;
                //int limit = (s1.length() > s2.length()) ? s2.length() : s1.length();

                String c1 = commonChars(s1, s2, limit);
                String c2 = commonChars(s2, s1, limit);

                if ((c1.length() != c2.length()) || c1.length() == 0 || c2.length() == 0) {
                        return 0;
                }
                int transpositions = transpositions(c1, c2);
                return (c1.length() / ((double)s1.length()) + c2.length() / ((double)s2.length()) + (c1.length() - transpositions) / ((double)c1.length())) / 3.0 * 100;
        }



        private String commonChars(String s1, String s2, int limit) {

                StringBuilder common = new StringBuilder();
                StringBuilder copy = new StringBuilder(s2);

                for (int i = 0; i < s1.length(); i++) {
                        char ch = s1.charAt(i);
                        boolean foundIt = false;
                        for (int j = Math.max(0, i - limit); !foundIt && j < Math.min(i + limit, s2.length()); j++) {
                                if (copy.charAt(j)==ch) {
                                        foundIt = true;
                                        common.append(ch);
                                        copy.setCharAt(j, '*' as char);
                                }
                        }
                }
                return common.toString();
        }

  private int transpositions(String c1, String c2) {
                int transpositions = 0;
                for (int i = 0; i < c1.length(); i++) {
                        if (c1.charAt(i) != c2.charAt(i)) {
                                transpositions++;
                        }
                }
                return transpositions / 2;
        }

        private static int commonPrefix(String c1,String c2, int maxPref) {
                int n = Math.min(maxPref, Math.min(c1.length(), c2.length()) );
                for (int i = 0; i < n; i++) {
                        if (c1.charAt(i) != c2.charAt(i)) {
                                return i;
                        }
                }
                return n;
        }



}
