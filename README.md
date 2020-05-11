# Example of the Layered JAR in Spring Boot 2.3.x

## Key Pieces
build.gradle
```gradle
bootJar {
  layered() 
}
```

Dockerfile
```dockerfile
FROM azul/zulu-openjdk-alpine:14 AS builder
WORKDIR application
COPY build/libs/layered-docker-image.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM azul/zulu-openjdk-alpine:14
WORKDIR application
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
```
Note: Not all layers will appear. For my example, the resources and snapshot-dependencies aren't present. I would 
recommend running `java -Djarmode=layertools -jar <name>.jar` regardless to understand what layers your application is
producing.

You'll need to also copy the `spring-boot-loader` layer over, otherwise you'll see the following errors
```bash
~/spring-boot-layered-jar (master) $ docker run a36f42785344
Error: Could not find or load main class org.springframework.boot.loader.JarLauncher
Caused by: java.lang.ClassNotFoundException: org.springframework.boot.loader.JarLauncher
```

# References
[Spring Blog: Creating Docker images with Spring Boot 2.3.0.M1](https://spring.io/blog/2020/01/27/creating-docker-images-with-spring-boot-2-3-0-m1)
