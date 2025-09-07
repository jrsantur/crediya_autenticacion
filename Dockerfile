FROM gradle:8.8-jdk17 AS build
WORKDIR /code
COPY . .
RUN gradle -x test clean bootJar --no-daemon

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /code/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]