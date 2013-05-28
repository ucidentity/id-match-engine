#!/bin/sh
#HOST=http://localhost:8080/dolphin/v1/people
HOST=http://idmatch-d1.calnet.berkeley.edu/dolphin/v1/people

SOR=$1
sorId=$2
PAYLOAD=$3

echo "$1 $2 $3 ";
curl -v -X PUT -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$SOR/$sorId -d "$PAYLOAD"

