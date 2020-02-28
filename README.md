# Spring Rest Client For GoLang REST Server
SpringBoot Rest Client that calls a REST endpoint exposed by a GoLang executable. Both REST client and server run in same Docker container. [The source for GoLang Server can be found here][1].

## Maven plugin to download executable from S3 bucket (Optional)
When execuated with `mvn package`, it will download a GoLang executable called `main` from specified AWS S3 Bucket using a Maven Plugin described in [this DZone article][2]. This is an optional step. 

## Dockerfile
The `Dockerfile` will copy downloaded executable `main` from host to `/app/bin/main` in the Docker image. 

```
FROM amazoncorretto:8
ARG DEPENDENCY=target/dependency

COPY ./main /app/bin/
RUN chmod 777 /app/bin/main 

COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.test.Main"]
```

## Spawning `main` from the REST client

This client uses `ProcessBuilder` to excute the GoLang REST server from SpringBoot Application.

## Running multiple processes inside one Docker container
As [per this document][3], it appears that it's an anti-pattern to run multiple services in one Docker container. But after re-reading the document, my understanding is that we should have one _service_ per container. This services can spawn multiple _processes_ like Apache web server starts multiple worker processes. So I think it's ok to have multiple processes in a Docker container in case our use case really really needs it.

[1]: https://github.com/kunupat/GoRestServer/tree/master
[2]: https://dzone.com/articles/upload-and-download-files-to-s3-using-maven
[3]: https://docs.docker.com/config/containers/multi-service_container/
