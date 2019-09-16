# exam-maker
[![Build Status](https://travis-ci.org/hezaaron/online-test-exam-maker.svg?branch=master)](https://travis-ci.org/hezaaron/online-test-exam-maker)

The ***exam-maker*** **App** can be used to test for basic programming skills. I am continuously improving it,
and I've deployed it on Heroku: https://iplusplus-client.herokuapp.com. **To** **_login_**, use the credentials below:

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
cd exam
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
cd web && npm install
ng serve
```

Fire up a web browser and navigate to `http://localhost:4200`. You will be redirected to the login page.

***_Use_*** the credentials above to login.

The app is integrated with Okta's cloud service API for user authentication, so you need to be on the internet to **_login_** successfully.