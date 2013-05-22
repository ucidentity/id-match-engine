//custom configuration venu alla
//this is where the incoming request params are mapped to registry columns
//this allows to hide the internal column names and provide for a json api that is user friendly
//most important, this decouples the internal column names from external api, so if a column name changes,
//api users do not need to change anything, admin needs to just update this map.


//limited to 15 columns for this release
//sorId is empId,stuId,affId where SOR is one of sis,hr,advcon
//referenceId could be UID or UUID, more than one record can have it, 
//referenceId helps identify all the records belonging to the same user
//referenceId, sorId and SOR are not customizable, they are allotted to columns with same name
//referenceId,sorId,SOR
idMatch.schemaMap = [
 referenceId : 'referenceId',
       sorId : 'sorId',
         SOR : 'SOR',
         ssn : 'attr4',
       lName : 'attr5',
       fName : 'attr6',
      middle : 'attr7',
       dobMM : 'attr8',
       dobDD : 'attr9',
     dobYYYY : 'attr10',
       email : 'attr11',
      attr12 : 'attr12',
      attr13 : 'attr13',
      attr14 : 'attr14',
      attr15 : 'attr15'
     
]

//a list of rules, each rule by itself is canonical
//execute all the rules and collate the results
//do not execute rules that have no request values
//example: if ssn is missing in request, do not run rules that have ssn
idMatch.canonicalMatchRuleSet = [
["dobYYYY","lName"],
["ssn"],
["fName","lName"]
]


//new format, notice the blockingFilter, this reduces the potential candidates for matching
//where there is wild card, then run the match against all users
idMatch.fuzzyMatchRuleSet = [
[ blockingFilter : ["ssn","dobYYYY"] , matchAttributes : ["lName"] ],
[ blockingFilter : ["ssn"], matchAttributes : ["lName","dobYYYY"] ],
[ blockingFilter :["*"], matchAttributes : ["dobYYYY","fName","lName"] ]
]

//this is where the type of match algorithm to use for a given attribute is specified
//if an attribute is not specified here but is present in the rules, then that rule will be ignored 
idMatch.fuzzyAttributeAlgorithmMap = [
    ssn : [matchType : "EditDistance", distance : "1"],
  lName : [matchType : "Transpose", distance : "1"],
  fName : [matchType : "Soundex"]
]


//Used for authn via Http Basic
idMatch.securityKeys = [
   tester1 : '123456',
   tester2 : '234567',
   tester3 : '345678'
]

//only for testing phase
idMatch.test.createUsers = true //true or false
idMatch.test.size = 2
