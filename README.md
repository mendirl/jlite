# JHipster Sample Application

## Prerequisites

### Java

You need to have Java 21:

- [JDK 21](https://openjdk.java.net/projects/jdk/21/)

### Node.js and NPM

Before you can build this project, you must install and configure the following dependencies on your machine:

[Node.js](https://nodejs.org/): We use Node to run a development web server and build the project.
Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

```
npm install
```

## Local environment

- [Local server](http://localhost:8080)
- [Local API doc](http://localhost:8080/swagger-ui.html)

<!-- jhipster-needle-localEnvironment -->

## Start up

```bash
./mvnw
```

```bash
docker compose -f src/main/docker/postgresql.yml up -d
```

<!-- jhipster-needle-startupCommand -->

## Documentation

- [Hexagonal architecture](documentation/hexagonal-architecture.md)
- [Front hexagonal architecture](documentation/front-hexagonal-architecture.md)
- [Package types](documentation/package-types.md)
- [Assertions](documentation/assertions.md)
- [sonar](documentation/sonar.md)
- [PostgreSQL](documentation/postgresql.md)
- [Logs Spy](documentation/logs-spy.md)
- [CORS configuration](documentation/cors-configuration.md)
- [Cucumber](documentation/cucumber.md)
- [Kipe authorization](documentation/kipe-authorization.md)
- [Kipe expression](documentation/kipe-expression.md)
- [Cucumber authentication](documentation/cucumber-authentication.md)
- [Sample](documentation/sample.md)
- [Vue](documentation/vue.md)
- [Vue JWT Authentication Components](documentation/vue-jwt-authentication-components.md)
- [Caffeine](documentation/caffeine.md)

<!-- jhipster-needle-documentation -->
