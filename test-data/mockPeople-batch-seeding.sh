#!/bin/#sh

#this is test data from Sondra's xls 
#05/2013
#venu alla
#
CMD="sh ./put-mockPeople.sh"

P1='{"referenceId" : "1","SOR" : "SIS", "sorId" : "23412344", "lName" : "Madrid", "fName" : "Anne Jilliene Lising", "ssn" : "23456" , "dobMM" : "04", "dobDD" : "24"}'
P2='{"referenceId" : "2","SOR" : "SIS", "sorId" : "22912304", "lName" : "Smith", "fName" : "Richard", "ssn" : "12349" , "dobMM" : "03", "dobDD" : "17" }'
P3='{"referenceId" : "3","SOR" : "SIS", "sorId" : "24294032", "lName" : "Chen", "fName" : "Yiwen", "ssn" : "29463" , "dobMM" : "12", "dobDD" : "13" }'
P4='{"referenceId" : "4","SOR" : "HR", "sorId" : "0121038475", "lName" : "Lee", "fName" : "John", "ssn" : "12049" , "dobMM" : "07", "dobDD" : "11" }'
P5='{"referenceId" : "5","SOR" : "ADVCON", "sorId" : "011763541", "lName" : "Mbebe", "fName" : "Joseph", "dobMM" : "01", "dobDD" : "02" }'
P6='{"referenceId" : "6","SOR" : "SIS", "sorId" : "20664748", "lName" : "Wunderbar", "fName" : "Kelly", "ssn" : "54019" , "dobMM" : "11", "dobDD" : "06"}'
P7='{"referenceId" : "7","SOR" : "SIS", "sorId" : "20914408", "lName" : "Granite", "fName" : "Abigail", "dobMM" : "05", "dobDD" : "12"}'
P8='{"referenceId" : "9","SOR" : "HR", "sorId" : "012491824", "lName" : "Haas", "fName" : "Mary Ann", "ssn" : "12345" , "dobMM" : "04", "dobDD" : "09" }'
P9='{"referenceId" : "9","SOR" : "SIS", "sorId" : "20812345", "lName" : "Richardson", "fName" : "Kristine", "ssn" : "76021" , "dobMM" : "08", "dobDD" : "01" }'
P10='{"referenceId" : "10","SOR" : "SIS", "sorId" : "17831291", "lName" : "Goetsch", "fName" : "Alma", "ssn" : "33323" , "dobMM" : "10", "dobDD" : "31" }'
P11='{"referenceId" : "11","SOR" : "HR", "sorId" : "012947652", "lName" : "Lee", "fName" : "John", "ssn" : "23456", "dobMM" : "07", "dobDD" : "11"}'
P12='{"referenceId" : "12","SOR" : "HR", "sorId" : "012612093", "fName" : "Marais", "lName" : "Yvette", "ssn" : "40041" , "dobMM" : "04", "dobDD" : "22" }'

$CMD SIS 23412344 "$P1"
#
$CMD SIS 22912304 "$P2"
#
$CMD SIS 24294032 "$P3"
#
$CMD HR 0121038475 "$P4"
#
$CMD ADVCON 011763541 "$P5"
#
$CMD SIS 20664748 "$P6"
#
$CMD SIS 20914408 "$P7"
#
$CMD HR 012491824 "$P8"
#
$CMD SIS 20812345 "$P9"
#
$CMD SIS 17831291 "$P10"
#
$CMD HR 012947652 "$P11" 
#
$CMD HR 012612093 "$P12"
