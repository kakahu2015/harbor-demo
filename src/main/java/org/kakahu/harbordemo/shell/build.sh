#!/bin/bash
cd /data/build/$1
docker build -t 172.16.100.105:81/c87e2267-1001-4c70-bb2a-ab41f3b81aa3/$2:$3 .
docker push 172.16.100.105:81/c87e2267-1001-4c70-bb2a-ab41f3b81aa3/$2:$3
#curl  -u "admin:Harbor12345"  -X GET -H "Content-Type: application/json" "http://172.16.100.105:81/api/repositories/c87e2267-1001-4c70-bb2a-ab41f3b81aa3/$2/tags/$3" | grep push
