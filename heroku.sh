#!/bin/bash

app_root=`pwd`

client_app=iplusplus-client
if ! echo "$(git remote -v)" | grep -q heroku-$client_app; then
  heroku create $client_app --remote heroku-iplusplus-client
fi

clientUri="https://$client_app.herokuapp.com"
server_app=iplusplus-server

# Deploy server
cd $app_root/backend

# Set origin for client on server
sed -i -e "s|http://localhost:4200|$clientUri|g" src/main/java/com/iplusplus/BackendApplication.java

./mvnw clean package -DskipTests
heroku deploy:jar target/backend-0.0.1-SNAPSHOT.jar  -a $server_app
heroku config:set FORCE_HTTPS="true" -a $server_app

# reset and remove changed files
sed -i -e "s|$clientUri|http://localhost:4200|g" src/main/java/com/iplusplus/BackendApplication.java
rm -rf src/main/src/main/java/com/iplusplus/BackendApplication.java-e

#deploy cleint
cd $app_root/frontend

git push heroku-$client_app master