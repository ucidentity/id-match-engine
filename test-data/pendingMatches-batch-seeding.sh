#!/bin/sh

#this is test data from Sondra's xls 
#05/2013
#venu alla
#
CMD=./put-pendingMatches.sh

sh $CMD SIS 23412344 '{"referenceId" : "1","SOR" : "SIS", "sorId" : "23412344", "lName" : "Madrid", "fName" : "Anne Jilliean Lising", "ssn" : "24983" , "dobMM" : "04", "dobDD" : "24"}'
#
sh $CMD SIS 22912304 '{"referenceId" : "2","SOR" : "SIS", "sorId" : "22912304", "lName" : "Smith", "fName" : "Richard", "ssn" : "12349" , "dobMM" : "03", "dobDD" : "17" }'
#
sh $CMD SIS 24294032 '{"referenceId" : "3","SOR" : "SIS", "sorId" : "24294032", "lName" : "Chen", "fName" : "Yiwen", "ssn" : "29463" , "dobMM" : "12", "dobDD" : "13" }'
#
sh $CMD HR 01211038475 '{"referenceId" : "4","SOR" : "HR", "sorId" : "01211038475", "lName" : "Lee", "fName" : "John", "ssn" : "12049" , "dobMM" : "07", "dobDD" : "11" }'
#
sh $CMD ADVCON 011763541 '{"referenceId" : "5","SOR" : "ADVCON", "sorId" : "011763541", "lName" : "Mbebe", "fName" : "Joseph", "dobMM" : "01", "dobDD" : "02" }'
#
sh $CMD SIS 20664748 '{"referenceId" : "6","SOR" : "SIS", "sorId" : "20664748", "lName" : "Wunderbar", "fName" : "Kelly", "ssn" : "54019" , "dobMM" : "11", "dobDD" : "06"}'
#
sh $CMD SIS 20914408 '{"referenceId" : "7","SOR" : "SIS", "sorId" : "20914408", "lName" : "Granite", "fName" : "Abigail", "dobMM" : "05", "dobDD" : "12"}'
#
sh $CMD HR 012491824 '{"referenceId" : "8","SOR" : "HR", "sorId" : "012491824", "lName" : "Haas", "fName" : "Mary Ann", "ssn" : "12345" , "dobMM" : "04", "dobDD" : "09" }'
#
sh $CMD SIS 20812345 '{"referenceId" : "9","SOR" : "SIS", "sorId" : "20812345", "lName" : "Richardson", "fName" : "Kristine", "ssn" : "76021" , "dobMM" : "08", "dobDD" : "01" }'
#
sh $CMD SIS 17831291 ' {"referenceId" : "10","SOR" : "SIS", "sorId" : "17831291", "lName" : "Goetsch", "fName" : "Alma", "ssn" : "33323" , "dobMM" : "10", "dobDD" : "31" }'
#
sh $CMD HR 012947652 ' {"referenceId" : "11","SOR" : "HR", "sorId" : "012947652", "lName" : "Lee", "fName" : "John", "ssn" : "23456", "dobMM" : "07", "dobDD" : "11"}'
#
sh $CMD HR 012612093 ' {"referenceId" : "12","SOR" : "HR", "sorId" : "012612093", "lName" : "Marias", "fName" : "Yvette", "ssn" : "40041" , "dobMM" : "04", "dobDD" : "22" }'
