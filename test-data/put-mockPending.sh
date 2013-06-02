#!/bin/sh
#HOST=http://localhost:8080/dolphin/v1/mockPending
HOST=http://idmatch-t1.calnet.berkeley.edu/dolphin/v1/mockPending

SOR=$1
sorId=$2
PAYLOAD=$3

echo "$1 $2 $3 ";
curl -v -X PUT -H "clientId:tester1" -H "password:123456" -H "content-type: application/json" $HOST/$1/$2 -d "$PAYLOAD"

