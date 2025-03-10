FROM openjdk:21

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} orderservice.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/orderservice.jar"]

