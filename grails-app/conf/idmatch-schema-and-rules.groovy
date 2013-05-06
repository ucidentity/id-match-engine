//custom configuration venu alla
//this is where the incoming request params are mapped to registry columns
//this allows to hide the internal column names and provide for a json api that is user friendly
//most important, this decouples the internal column names from external api, so if a column name changes,
//api users do not need to change anything, admin needs to just update this map.


//limited to 15 columns for this release
//sorId is empId,stuId,affId where SOR is one of sis,hr,advcon
idMatch.schemaMap = [
       uid : 'attr1'
     sorId : 'attr2',
       SOR : 'attr3',
       ssn : 'attr4',
     lname : 'attr5',
     fname : 'attr6',
    middle : 'attr7',
  dobMonth : 'attr8',
    dobDay : 'attr9',
   dobYear : 'attr10',
     email : 'attr11'
  sisAffId : 'attr12',
   hrAffId : 'attr13',
    attr14 : 'attr14',
    attr15 : 'attr15'
     
]

//this is where the rules will go
//use the same keys as in schemaMap
//this decouples it from database column name changes
//Deprecated
idMatch.ruleSetOld = [
    ssn : [exactMatchScore:"50", likeMatchScore : "30", algorithm: "BerkeleyEditDistance", distance:"1"],
    dob: [exactMatchScore:"10", likeMatchScore : "5", algorithm: "BerkeleyEditDistance", distance:"1"],
    fName : [ exactMatchScore:"20", likeMatchScore : "10", algorithm: "Soundex"],     
    lName : [ exactMatchScore:"30", likeMatchScore : "20", algorithm: "Soundex"],     
]
//Deprecated
idMatch.cutOffScoreMapOld = [ exact : '100', recon : '80' ]

idMatch.algorithmSet = ["Soundex","NYSIIS","EditDistance","DaitchMakotoff"]


//a list of rules, each rule by itself is canonical
//execute all the rules and collate the results
//do not execute rules that have no request values
//example: if ssn is missing in request, do not run rules that have ssn
idMatch.canonicalMatchRuleSet = [
["dob","lName"],
["ssn"],
["fName","lName","city" ]
]

//run these rules to find similar person entries
//note: the more rules to match, the more work to do, so make sure u r careful with this configuration
//if incoming request has no values for a given attribute in a rule, that rule is ignored 
//rules with higher priority should be added first
//not sure if all rules can be run, for performance sake.
//for this release only one rule that is found to have values for all attributes in the request will be run
idMatch.fuzzyMatchRuleSetOld = [
["dob","lName"],
["ssn","fName","lName"]
]

idMatch.fuzzyMatchRuleSet = [

[ blockingFilter : ["ssn","dob"] , matchAttributes : ["dob"] ],
[ blockingFilter : ["ssn"], matchAttributes : ["lName"] ]

]

//this is where the type of match algorithm to use for a given attribute is specified
//if an attribute is not specified here but is present in the rules, then that rule will be ignored 
idMatch.fuzzyMatchTypes = [
    ssn : [matchType : "EditDistance", distance : "1"],
  lName : [matchType : "Transpose", distance : "1"],
  fName : [matchType : "Soundex"]
]

//only for testing phase
idMatch.test.createUsers = true
idMatch.test.size = 200

//Used for authn via Http Basic
idMatch.securityKeys = [
   tester1 : '123456',
   tester2 : '234567',
   tester3 : '345678'
]
