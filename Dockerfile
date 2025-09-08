# Etapa 1: Construcción
FROM gradle:8.4-jdk17 AS build
WORKDIR /app

# Copiamos los archivos necesarios para la compilación
COPY build.gradle settings.gradle ./
COPY src ./src

# Compilamos el proyecto
RUN gradle build --no-daemon

# Etapa 2: Ejecución
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiamos el .jar generado desde la etapa de construcción
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]