FROM openjdk:22-jdk-slim AS build
WORKDIR /home/gradle/src
COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew build -x test --no-daemon

FROM openjdk:22-jdk-slim
EXPOSE 8082
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]