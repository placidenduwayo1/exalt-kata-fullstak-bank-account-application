FROM maven:3-eclipse-temurin-21-alpine AS builder
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src
RUN mvn package -DskipTest
WORKDIR /build/target/
RUN java -Djarmode=layertools -jar microservices-configuration-server.jar extract

FROM maven:3-eclipse-temurin-21-alpine
COPY --from=builder /build/target/dependencies/ ./
COPY --from=builder /build/target/spring-boot-loader/ ./
COPY --from=builder /build/target/snapshot-dependencies/ ./
COPY --from=builder /build/target/application/ ./

EXPOSE 8100
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "org.springframework.boot.loader.launch.JarLauncher"]
