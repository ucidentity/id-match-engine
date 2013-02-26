package dolphin

/*
 * this class is a copy and paste of LED code from Oyster
 * just wanted to check if I could change the code to exit if the distance exeeded maxDistance
 * instead of computing the final distance say 5, why not exist as soon as it is apparent that it exceeded the maxDistance
 * 
 */
class BerkeleyEditDistanceService {


def static boolean compare(String s, String t, int maxDistance){
        boolean result = false;
        int cost;
        int lengthOfShort, lengthOfLong;

        if (s == null && t != null){
            lengthOfShort = 0;
            lengthOfLong = t.length();
            return maxDistance > t.length();
        }
        else if (s != null && t == null){
            lengthOfLong = s.length();
            lengthOfShort = 0;
            return maxDistance >  s.length();
        }
        else if (s != null && t != null){
            String sTemp = s.toUpperCase();
            String tTemp = t.toUpperCase();

            lengthOfShort = Math.min(sTemp.length(), tTemp.length());
            lengthOfLong = Math.max(sTemp.length(), tTemp.length());
            int m = sTemp.length()+1;
            int n = tTemp.length()+1;

            // d is a table with m+1 rows and n+1 columns
            int [][] d = new int[m][n];

            for (int i = 0; i < m; i++)
               {  d[i][0] = i; println "m col is "+d[i][0];}
            for (int j = 0; j < n; j++)
                { d[0][j] = j; println "n col is "+d[0][j]; }

            for (int j = 1; j < n; j++){
                for (int i = 1; i < m; i++) {
                    if (sTemp.charAt(i-1) == tTemp.charAt(j-1))
                        cost = 0;
                    else
                        { cost = 1; println  " ${sTemp.charAt(i-1)} and ${tTemp.charAt(j-1)}" }
                    d[i][j] = Math.min(Math.min(d[i-1][j] + 1,     // insertion
                                                d[i][j-1] + 1),    // deletion
                                                d[i-1][j-1] + cost // substitution
                              );
                   //println "computed distance so far is " +d[i][j];
                   //if (d[i][j] > maxDistance) return false; //if the computed distance exceeds max desired distance, then return
                }
            }
            return maxDistance >  d[m-1][n-1];
        }

      return result;

    } //custom method isDistanceRange done

}
