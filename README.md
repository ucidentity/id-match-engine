#Date: Sept 2012
#Author: venu.alla@gmail.com
#Desc: id match grails app install/run/test instructions
#ref: https://spaces.internet2.edu/display/cifer/ID+Match+Engine

#Dependencies
1. Grails 2.0 ( http://grails.org/ )
2. Java 1.6+
3. Needs MySql 6+ or any other RDBMS

#Setup
1. cd to the folder where you find this README.txt
2. cd grails-app/conf


A.Aegistry Setup
1. Edit DataSource.groovy to point grails app to the database that contains the Person Registry
Note: Current version supports only one table. 

B.SchamaMap Setup
1. Edit Config.groovy, 
2. left-side is for external facing request attribute names, right-side is for internal database column names.
ex:
idMatch.schemaMap = [
     fName : 'firstName',
     lName : 'lastName',
     dob : 'dateOfBirth',
     ssn : 'social'
}

C. Match Rules Setup 
1. Edit Config.groovy
2. For each attribute that is part of the search filter, add a rule, assign scores for exact and like matches. provide algorithm to use for like matching.
ex:
idMatch.ruleSet = [
    ssn : [exactMatchScore:"50", likeMatchScore : "40", algorithm: "EditDistance", distance:"2"],
    fName : [ exactMatchScore:"20", likeMatchScore : "15", algorithm: "Soundex"],     
    lName : [ exactMatchScore:"30", likeMatchScore : "25", algorithm: "Soundex"],     
]

D. Cut Off Score Setup
1. Edit Config.groovy
2. Provide cutoffs for exact match and recon match. 
ex:
idMatch.cutOffScoreMap = [ exact : '100', recon : '80' ]


#Search
1. Curl
curl -X POST -d "{"data": {"fName": "venu", "lName": "alla", "ssn": "111222333", "dob" : "123456", "city" : "Berkeley"}}" -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" http://localhost:8080/dolphin/person/match
NOTE: pipe for formatting the response { | python -m json.tool }

2.Response is similar to :
{
    "exact": [], 
    "input": {
        "fName": "venu", 
        "lName": "allo", 
        "ssn": 111222333
    }, 
    "recon": [
        {
            "fName": "venu", 
            "lName": "alla", 
            "personMatchScore": 95, 
            "ssn": "111222333", 
            "uid": "1111"
        }
    ]
}
