#!/bin/sh

#this is test data from Sondra's xls 
#05/2013
#venu alla
#
CMD=./put-mockPending.sh

T1='{"SOR" : "SIS", "sorId" : "23421935", "lName" : "Madrid", "fName" : "Anne Jilliane Lising", "ssn" : "24983" , "dobMM" : "04", "dobDD" : "24"}'
T2='{"SOR" : "HR", "sorId" : "012274916", "lName" : "Chen", "fName" : "Yiwen", "ssn" : "29643" , "dobMM" : "12", "dobDD" : "13" }'
T3='{"SOR" : "HR", "sorId" : "012483029", "lName" : "Lee", "fName" : "John", "ssn" : "17401" , "dobMM" : "07", "dobDD" : "11" }'
T4='{"SOR" : "SIS", "sorId" : "24569123", "lName" : "Smith", "fName" : "Mary", "ssn" : "03877" , "dobMM" : "10", "dobDD" : "22" }'
T5='{"SOR" : "ADVCON", "sorId" : "012592842", "lName" : "Arnaud", "fName" : "Jean-Pierre", "dobMM" : "04", "dobDD" : "11" }'
T6='{"SOR" : "HR", "sorId" : "011763541", "lName" : "Mbebe", "fName" : "Joseph", "ssn" : "61333" , "dobMM" : "01", "dobDD" : "02" }'
T7='{"SOR" : "ADVCON", "sorId" : "5301456", "lName" : "Wunderbar", "fName" : "Kelly", "ssn" : "54019" , "dobMM" : "11", "dobDD" : "06" }'
T8='{"SOR" : "SIS", "sorId" : "20914408", "lName" : "Granite", "fName" : "Abigail", "ssn" : "69781" , "dobMM" : "05", "dobDD" : "12" }'
T9='{"SOR" : "ADVCON", "sorId" : "012683025", "lName" : "Haas", "fName" : "Mary Ann", "ssn" : "12345" , "dobMM" : "04", "dobDD" : "04" }'
T10='{"SOR" : "HR", "sorId" : "012612345", "lName" : "Smith", "fName" : "Kristine", "ssn" : "76021" , "dobMM" : "08", "dobDD" : "01" }'
T11='{"SOR" : "ADVCON", "sorId" : "cads928304", "lName" : "Goetsch", "fName" : "Alma"}'
T12='{"SOR" : "SIS", "sorId" : "25198367", "lName" : "Chen", "fName" : "Yiwen", "ssn" : "29643" , "dobMM" : "12", "dobDD" : "13" }'
#T13='{"SOR" : "ADVCON", "sorId" : "cads3021132", "lName" : "Yvette", "fName" : "Marais"}'
T13='{"SOR" : "ADVCON", "sorId" : "cads3021132", "lName" : "Yvvetteeee", "fName" : "Marias"}'


sh $CMD SIS 23421935 "$T1"
sh $CMD HR 012274916 "$T2"
sh $CMD HR 012483029 "$T3"
sh $CMD SIS 24569123 "$T4"
sh $CMD ADVCON 0125922842 "$T5"
sh $CMD HR 011763541  "$T6"
sh $CMD ADVCON 5301456 "$T7"
sh $CMD SIS 20914408 "$T8"
sh $CMD ADVCON 012683025 "$T9"
sh $CMD HR 012612345 "$T10"
sh $CMD ADVCON cads928304 "$T11"
sh $CMD SIS 25198367 "$T12"
sh $CMD ADVCON cads3021132 "$T13"
