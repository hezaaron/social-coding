#!/bin/bash

start=`pwd`

# Set origin for client on server
sed -i -e "s|http://localhost:4200|https://iplusplus.cfapps.io|g" $start/backend/src/main/java/com/iplusplus/BackendApplication.java

./mvnw clean package -f $start/backend/pom.xml

cd $start/frontend
rm -rf dist

npm install && ng build --prod --aot
cd dist/frontend
touch Staticfile
echo "pushstate: enabled" >> Staticfile

cd $start
cf push

# reset and remove changed files
sed -i -e "s|https://iplusplus.cfapps.io|http://localhost:4200|g" $start/backend/src/main/java/com/iplusplus/BackendApplication.java
rm -rf $start/backend/src/main/src/main/java/com/iplusplus/BackendApplication.java-e

# show apps and URLs
cf apps