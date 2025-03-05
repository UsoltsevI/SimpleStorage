# Simple storage

Made for a university educational project.

A simple monolithic open source web application
that allows registered
users to store their files on a server.

# Description of the technologies used

- [Spring Framework](https://spring.io/)

  To simplify the development and maintenance of the application.
  It also helps with ensuring security in the application.

- [Spring boot 3](https://spring.io/projects/spring-boot)

  Makes it easier to work with the Spring Framework.

- [Thymeleaf](https://www.thymeleaf.org/)

  For dynamic work with html pages.

- [PostgreSQL](https://www.postgresql.org/)

  A database for storing user metadata
  and files uploaded by them.

- [MinIO](https://min.io/)

  Object storage for storing all
  user files.

- [Gradle](https://gradle.org/)

  The project side system. Simplifies management
  dependencies, library downloads, and the project build itself.

- [Docker](https://www.docker.com/)

  Used for application containerization
  and databases. Provides convenience of deployment
  applications.

- [Lombok](https://projectlombok.org/)

  To simplify writing when in Java.


## Usage

### Download
To run the application on your
machine, first clone the repository:
```shell
git clone git@github.com:UsoltsevI/SimpleStorage.git
```

Go to the root folder of the project and start
building it.:
```shell
./gradlew build
```

### Launch

After building the system, make sure that
docker is pre-installed on your machine. If it is not available, then
you can do it using one of the guides from the official website.:
https://docs.docker.com/engine/install/

Launch the app:
```shell
docker compose up sstorage
```

### Stop
```shell
docker compose stop sstorage spostgres minio
```

**The page translated from Russian by yandex neuro-translator (the author was too lazy to translate on his own).*
