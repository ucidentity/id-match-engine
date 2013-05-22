#Date: Sept 2012
#Author: venu.alla@gmail.com
#NOTE: pipe for formatting the response { | python -m json.tool }



#Search
1. Curl
CANONICAL:
curl -v -X GET -d "{ "person": {""fName"": "venu", "lName": "alla", "ssn": "111222333", "dobYYYY" : "1234"}}" -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" http://idmatch-d1.calnet.berkeley.edu/dolphin/v1/engine/canonical


FUZZY:
curl -v -X GET -d "{"person": {""fName"": "venu", "lName": "alla", "ssn": "111222333", "dobYYYY" : "1234" }}" -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" http://idmatch-d1.calnet.berkeley.edu/dolphin/v1/engine/fuzzy





#PEOPLE API
curl -v -X PUT -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" http://idmatch-d1.calnet.berkeley.edu/dolphin/v1/people/SIS/12345 -d "replace-this-with-sample-data-below"

{"person" : {"referenceId" : "2", "lName" : "SMITH", "fName" : "RICHARD", "ssn" : "12349" , "dobMM" : "03", "dobDD" : "17", "sorId" : "22912304", "SOR" : "SIS" }}
{"person" : {"referenceId" : "3", "lName" : "CHEN", "fName" : "YIWEN", "ssn" : "29463" , "dobMM" : "12", "dobDD" : "13", "sorId" : "24294032", "SOR" : "SIS" }}
{"person" : {"referenceId" : "4", "lName" : "Lee", "fName" : "John", "ssn" : "12049" , "dobMM" : "07", "dobDD" : "11", "sorId" : "0121038475", "SOR" : "HR" }}
{"person" : {"referenceId" : "5", "lName" : "MBEBE", "fName" : "Joseph", "dobMM" : "01", "dobDD" : "02", "sorId" : "011763541", "SOR" : "HR" }}
{"person" : {"referenceId" : "6", "lName" : "Wunderbar", "fName" : "Kelly", "ssn" : "54019" , "dobMM" : "11", "dobDD" : "06", "sorId" : "20664748", "SOR" : "SIS" }}
{"person" : {"referenceId" : "7", "lName" : "Granite", "fName" : "Abigail", "ssn" : "" , "dobMM" : "05", "dobDD" : "12", "sorId" : "20914408", "SOR" : "SIS" }}
{"person" : {"referenceId" : "8", "lName" : "Haas", "fName" : "Mary", "ssn" : "12345" , "dobMM" : "04", "dobDD" : "09", "sorId" : "012391824", "SOR" : "HR" }}
{"person" : {"referenceId" : "12", "lName" : "Marais", "fName" : "Yvette", "ssn" : "40041" , "dobMM" : "04", "dobDD" : "22", "sorId" : "0126122093", "SOR" : "HR" }}

{"person" : {"referenceId" : "9", "lName" : "Richardson", "fName" : "Kristine", "ssn" : "76021" , "dobMM" : "08", "dobDD" : "01", "sorId" : "20812345", "SOR" : "SIS" }}
{"person" : {"referenceId" : "10", "lName" : "Goestch", "fName" : "Alma", "ssn" : "33323" , "dobMM" : "10", "dobDD" : "31", "sorId" : "17831291", "SOR" : "SIS" }}
{"person" : {"referenceId" : "11", "lName" : "Lee", "fName" : "John", "ssn" : "23456" , "dobMM" : "07", "dobDD" : "11", "sorId" : "012947652", "SOR" : "HR" }}


#GET /v1/people/HR/123
curl -v -X GET  -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" http://idmatch-d1.calnet.berkeley.edu/dolphin/v1/people/HR/123




#PENDING MATCH API
#GET /v1/pendingMatches
#GET /v1/pendingMatches/123
curl -v -X GET  -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" http://idmatch-d1.calnet.berkeley.edu/dolphin/v1/pendingMatches/1

#PUT /v1/pendingMatches
curl -v -X PUT -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" http://idmatch-d1.calnet.berkeley.edu/dolphin/v1/pendingMatches -d "replace-this-with-sample-data-below" 

{ "SOR" : "HR", "sorId" : "20812345" , "person" : {"referenceId" : "9", "lName" : "Richardson", "fName" : "Kristine", "ssn" : "76021" , "dobMM" : "08", "dobDD" : "01", "sorId" : "20812345", "SOR" : "SIS" }}
{"SOR" : "SIS", "sorId" : "17831291", "person" : {"referenceId" : "10", "lName" : "Goestch", "fName" : "Alma", "ssn" : "33323" , "dobMM" : "10", "dobDD" : "31", "sorId" : "17831291", "SOR" : "SIS" }}
{"SOR" : "HR", "sorId" :"012947652", "person" : {"referenceId" : "11", "lName" : "Lee", "fName" : "John", "ssn" : "23456" , "dobMM" : "07", "dobDD" : "11", "sorId" : "012947652", "SOR" : "HR" }}


