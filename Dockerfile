FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x gradlew

RUN ./gradlew clean bootJar -x test

RUN cp build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
