FROM maven:3.9.11-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY calculator/pom.xml calculator/
COPY rest/pom.xml rest/
COPY shared/pom.xml shared/

RUN mvn dependency:go-offline -B

# Copy source
COPY . .

RUN mvn clean install

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/rest/target/rest-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]