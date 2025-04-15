FROM maven:3.9.4-amazoncorretto-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY --from=build /app/target/itevents-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
