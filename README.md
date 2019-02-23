# online-test-exam-maker
[![Build Status](https://travis-ci.org/hezaaron/online-test-exam-maker.svg?branch=master)](https://travis-ci.org/hezaaron/online-test-exam-maker)

The ***test-exam-maker*** **app** is still *being* *developed*, but has been deployed on Pivotal Web Service.
You can check it out on the website: https://iplusplus.cfapps.io

***_login_*** with the following credentials:

> username: hezaaron+01@gmail.com
> password: h326Otaa

## To run the test-exam-maker project _locally_, _follow_ the instructions below:

```sh
git clone https://github.com/hezaaron/online-test-exam-maker.git && cd online-test-exam-maker
```

This will install a copy of the project on your system.

## Install dependencies and start each app:

### Run the server

```sh
cd backend
```

**_cmd_**

```sh
mvnw spring-boot:run
```

**_bash_**

```sh
./mvnw spring-boot:run
```

### Run the client

```sh
cd frontend && npm install
npm start
```

Fire up a web browser and navigate to `http://localhost:4200`. You will be redirected to the login page.

***_Use_*** the credentials above to login:
