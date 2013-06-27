#!/bin/sh
HOST=http://localhost:8080/dolphin/reset
#HOST=http://idmatch-t1.calnet.berkeley.edu/dolphin/reset

SOR=$1
sorId=$2

echo "$1 $2 $3 "
curl -v -X GET -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST

