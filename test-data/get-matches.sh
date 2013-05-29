#!/bin/sh
#calls engine api with json payload
HOST=http://localhost:8080/dolphin/v1/engine
#HOST=http://idmatch-t1.calnet.berkeley.edu/dolphin/v1/engine

#test data from Sondra's xls
declare -a testcases
T1='{"SOR" : "SIS", "sorId" : "23421935", "lName" : "Madrid", "fName" : "Anne Jilliean Lising", "ssn" : "24983" , "dobMM" : "04", "dobDD" : "24"}'
T2='{"SOR" : "HR", "sorId" : "012274916", "lName" : "Chen", "fName" : "Yiwen", "ssn" : "29643" , "dobMM" : "12", "dobDD" : "13" }'
T3='{"SOR" : "HR", "sorId" : "012483029", "lName" : "Lee", "fName" : "John", "ssn" : "17401" , "dobMM" : "07", "dobDD" : "11" }'
T4='{"SOR" : "SIS", "sorId" : "24569123", "lName" : "Smith", "fName" : "Mary", "ssn" : "03877" , "dobMM" : "10", "dobDD" : "22" }'
T5='{"SOR" : "ADVCON", "sorId" : "0125922842", "lName" : "Arnaud", "fName" : "Jean-Pierre", "dobMM" : "04", "dobDD" : "11" }'
T6='{"SOR" : "HR", "sorId" : "011763541", "lName" : "Mbebe", "fName" : "Joseph", "ssn" : "61333" , "dobMM" : "01", "dobDD" : "02" }'
T7='{"SOR" : "ADVCON", "sorId" : "5301456", "lName" : "Wunderbar", "fName" : "Kelly", "ssn" : "54019" , "dobMM" : "11", "dobDD" : "06" }'
T8='{"SOR" : "SIS", "sorId" : "20914408", "lName" : "Granite", "fName" : "Abigail", "ssn" : "69781" , "dobMM" : "05", "dobDD" : "12" }'
T9='{"SOR" : "ADVCON", "sorId" : "012683025", "lName" : "Haas", "fName" : "Mary Ann", "ssn" : "12345" , "dobMM" : "04", "dobDD" : "04" }'
T10='{"SOR" : "HR", "sorId" : "012612345", "lName" : "Smith", "fName" : "Kristine", "ssn" : "76021" , "dobMM" : "08", "dobDD" : "01" }'
T11='{"SOR" : "ADVCON", "sorId" : "cads928304", "lName" : "Goetsch", "fName" : "Alma"}'
T12='{"SOR" : "SIS", "sorId" : "25198367", "lName" : "Chen", "fName" : "Yiwen", "ssn" : "29643" , "dobMM" : "12", "dobDD" : "13" }'
T13='{"SOR" : "ADVCON", "sorId" : "cads3021132", "lName" : "Yvette", "fName" : "Marais"}'

MATCH_TYPE=$2;
ID=$1
CMD='curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE'
eval echo \${$ID}

echo "\nTest Starts"
echo "\nTest T1: $T1"
curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE -d "$T1"
echo "\n\nTest T2: $T2"
curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE -d "$T2"
echo "\n\nTest T3: $T3"
curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE -d "$T3"
echo "\n\nTest T4: $T4"
curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE -d "$T4"
echo "\n\nTest T5: $T5"
curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE -d "$T5"
echo "\n\nTest T6: $T6"
curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE -d "$T6"
echo "\n\nTest T7: $T7"
curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE -d "$T7"
echo "\n\nTest T8: $T8"
curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE -d "$T8"
echo "\n\nTest T9: $T9"
curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE -d "$T9"
echo "\n\nTest T10: $T10"
curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE -d "$T10"
echo "\n\nTest T11: $T11"
curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE -d "$T11"
echo "\n\nTest T12: $T12"
curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE -d "$T12"
echo "\n\nTest T13: $T13"
curl -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$MATCH_TYPE -d "$T13"
echo "\n\nTest Complete"

