# JHipster Lite Application

## Node.js and NPM

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js](https://nodejs.org/): We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

```
npm install
```

## Local environment

- [Local server](http://localhost:8080)

<!-- jhipster-needle-localEnvironment -->

## Start up

```bash
./mvnw
```

```bash
docker compose -f src/main/docker/postgresql.yml up -d
```

```bash
docker compose -f src/main/docker/keycloak.yml up -d
```


<!-- jhipster-needle-startupCommand -->

## Documentation

- [Hexagonal architecture](documentation/hexagonal-architecture.md)
- [Package types](documentation/package-types.md)
- [Assertions](documentation/assertions.md)
- [Vue](documentation/vue.md)
- [Logs spy](documentation/logs-spy.md)
- [Module creation](documentation/module-creation.md)
- [Cucumber](documentation/cucumber.md)
- [Postgresql](documentation/postgresql.md)
- [Dev tools](documentation/dev-tools.md)
- [Caffeine](documentation/caffeine.md)
- [Jpa pages](documentation/jpa-pages.md)
- [CORS configuration](documentation/cors-configuration.md)

<!-- jhipster-needle-documentation -->
