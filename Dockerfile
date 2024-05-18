ARG LEISURE=leisure

FROM openjdk:17-jdk-alpine AS builder

ARG LEISURE

COPY ./pom.xml /app
COPY ./.mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .

RUN sed -i -e 's/\r$//' ./mvnw

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

ARG LEISURE

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /target/leisure-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8080
ENV PORT $PORT_APP
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "leisure-0.0.1-SNAPSHOT.jar"]

# docker network create leisure-spring
# docker build -t leisure:latest . -f .\Dockerfile
# docker run -p 8080:8080 --rm -d --name leisure --network leisure-spring afb01/leisure
# Agregar el -d
