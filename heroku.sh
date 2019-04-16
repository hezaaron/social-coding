#!/bin/bash

app_root=`pwd`

client_app=iplusplus-client
if ! echo "$(git remote -v)" | grep -q heroku-$client_app; then
  heroku create $client_app --remote heroku-iplusplus-client
fi
clientUri="https://$client_app.herokuapp.com"

server_app=iplusplus-server
heroku create $server_app --no-remote
serverUri="https://$server_app.herokuapp.com"

# Deploy server
cd $app_root/backend

# Set origin for client on server
sed -i -e "s|http://localhost:4200|$clientUri|g" src/main/java/com/iplusplus/BackendApplication.java

./mvnw clean package -DskipTests
heroku deploy:jar target/backend-0.0.1-SNAPSHOT.jar --app iplusplus-server
heroku config:set $server_app FORCE_HTTPS="true"

# Deploy client
cd $app_root/frontend
npm install
git add .
git commit -m 'initial iplusplus-client deploy'
git push heroku-iplusplus-client master

# reset and remove changed files
sed -i -e "s|$clientUri|http://localhost:4200|g" $app_root/backend/src/main/java/com/iplusplus/BackendApplication.java
rm -rf $app_root/backend/src/main/src/main/java/com/iplusplus/BackendApplication.java-e

# show apps and URLs
heroku open