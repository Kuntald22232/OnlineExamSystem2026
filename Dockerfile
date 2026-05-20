FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN ./gradlew clean build -x test

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]