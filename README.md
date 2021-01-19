## social-coding
[![Build Status](https://travis-ci.org/hezaaron/social-coding.svg?branch=master)](https://travis-ci.org/hezaaron/social-coding)

The ***social-coding*** application is a system, based on microservices with an event-driven architecture using Springboot and Angular, that encourages users to train their programming skills and improve their level understanding of the Java language.

#### To install the application locally, run the following commands:

**_Prerequisites_**: [RabbitMQ](https://www.rabbitmq.com/download.html), [Node.js](https://nodejs.org/), and internet connection

```
git clone https://github.com/hezaaron/social-coding.git
cd social-coding
```

#### To install dependencies and start each microservice, follow the instructions below:

Start the `RabbitMQ` Server:

Depending on your operating system, the server might have been started automatically when RabbitMQ is installed. Otherwise, follow the instruction on the installation page for your [operating system](https://www.rabbitmq.com/admin-guide.html)

Start the `coding-quiz` service:

**_bash_**

```
cd coding-quiz
./mvnw spring-boot:run
```

**_cmd_**

```
cd coding-quiz
mvnw spring-boot:run
```

Start the `gamification` service:

```
cd gamification
./mvnw spring-boot:run
```

Start the `gateway` service:

```
cd gateway
./mvnw spring-boot:run
```

Start the `web` client:

```
cd web
npm install -g @angular/cli
ng serve --ssl true
```

Fire up a web browser and point it to https://localhost:4200. You will be directed to a login page where you can sign up and try the application.
The application uses Okta's cloud authentication API for user management functionality, so you have to be connected to the internet and https (http://localhost:4200 will not work) is required to sign up successfully and try the application.

You will get an `invalid security certificate warning`. To workaround this, you may do the following:

**_chrome_** & **_edge_**: navigate to chrome://flags/#allow-insecure-localhost, enable this flag and restart chrome to ignore invalid certificate for localhost.

**_firefox_**: Click 'Advance' then 'Accept risk and continue'