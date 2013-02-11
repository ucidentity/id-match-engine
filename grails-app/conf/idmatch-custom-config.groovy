//custom configuration venu alla
//this is where the incoming form params to registry columns is mapped
//this allows to hide the internal column names and provide for a json api that is user friendly
//most important, this decouples the internal column names from external api, so if a column name changes,
//api users do not need to change anything, admin needs to just update this map.

idMatch.schemaMap = [
       ssn : 'attr1',
       dob : 'attr4',
     fName : 'attr2',
     lName : 'attr3'
]

//this is where the rules will go
//map keys are registry columns, not incoming form parameters
//use the same keys as in schemaMap
//this decouples it from database column name changes
idMatch.ruleSet = [
    ssn : [exactMatchScore:"50", likeMatchScore : "30", algorithm: "BerkeleyEditDistance", distance:"1"],
    dob: [exactMatchScore:"10", likeMatchScore : "5", algorithm: "BerkeleyEditDistance", distance:"1"],
    fName : [ exactMatchScore:"20", likeMatchScore : "10", algorithm: "Soundex"],     
    lName : [ exactMatchScore:"30", likeMatchScore : "20", algorithm: "Soundex"],     
]

idMatch.cutOffScoreMap = [ exact : '100', recon : '80' ]

idMatch.algorithmSet = ["Soundex","NYSIIS","EditDistance","DaitchMakotoff"]

//Used for authn via Http Basic
idMatch.securityKeys = [

   tester1 : '123456',
   tester2 : '234567',
   tester3 : '345678'

]

//a list of rules, each rule by itself is canonical
//execute all the rules and collate the results
//do not execute rules that have no request values
//example: if ssn is missing in request, do not run rules that have ssn
idMatch.canonicalMatchRuleSet = [
"ssn",
"fName,lName,dob"
]
//run these rules to find similar person entries
//send them under similar 
idMatch.fuzzyMatchRuleSet = [
  ["confidence" : 90, "attr" : "ssn,lName,dob" ],
  ["confidence" : 80, "attr" : "ssn,fName,dob" ] 
]

//configure the fuzzy logic to use for each attribute
//since phonetic only works compared strings have same starting char,
//fetch 
idMatch.attributeFuzzyMatchAlgorithm = [
fName : [algorithm : "Soundex" ],
lName : [algorithm : "Soundex" ],
  ssn : [algorithm : "EditDistance", distance: 1 ],
  dob : [algorithm : "EditDistance", distance : 1 ]

]
