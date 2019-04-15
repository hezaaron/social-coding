#!/bin/bash

app_root=`pwd`

# Deploy server
$app_root/backend

# Set origin for client on server
sed -i -e "s|http://localhost:4200|https://iplusplus.cfapps.io|g" src/main/java/com/iplusplus/BackendApplication.java

./mvnw clean package

# Deploy client
cd $app_root/frontend

rm -rf dist
npm install && ng build --prod --aot
cd dist/frontend
touch Staticfile
echo "pushstate: enabled" >> Staticfile

cd $app_root
cf push

# reset and remove changed files
sed -i -e "s|https://iplusplus.cfapps.io|http://localhost:4200|g" $app_root/backend/src/main/java/com/iplusplus/BackendApplication.java
rm -rf $app_root/backend/src/main/src/main/java/com/iplusplus/BackendApplication.java-e

# show apps and URLs
cf apps