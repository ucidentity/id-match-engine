#Date: Sept 2012
#Author: venu.alla@gmail.com
#Desc: id match grails app install/run/test instructions
#ref: https://spaces.internet2.edu/display/cifer/ID+Match+Engine

#Needs
1. Grails 2.0 ( http://grails.org/ )
2. Java 1.6+
3. Needs MySql 6+ or any other RDBMS

#Install
1. cd to the folder where you find this README.txt
2. cd grails-app/conf
3. edit the DataSource.groovy to have proper backend connection info
   the existing DataSource is configured for mysql running on localhost.

#Startup
1. cd to install folder 
2. run grails
3. run-app
4. app will startup on localhost:8080/dolphin

#Configure(Setup)
1. Add Persons (current schema only supports firstname,lastname,ssn,uid,dateOfBirth,Middle)
localhost:8080/dolphin/person

2. Add Attribute Schema (only: ssn,firstname,lastname,dateOfBirth)
localhost:8080/dolphin/targetAttribute

3. Add Match Rule(s)
localhost:8080/dolphin/matchRule/form
ex: Select the attribute, the algorithm to use for matching, and set the score for the match.
Note: the total score can exceed 100, and this is a bug that will be fixed

#Search
1. Browser
http://localhost:8080/dolphin/Person/search
Fill in the search form, and "Run Match".
You shall see the results for exact match and recon match where found.

2. Curl
curl -X POST -d "{"data": {"fName": "venu", "lName": "alla", "ssn": "111222333"}}" -H "content-type: application/json" http://localhost:8080/dolphin/person/match3

response is similar to (only uids are returned):
['exact':[], 'recon':['1111', '3333']]


