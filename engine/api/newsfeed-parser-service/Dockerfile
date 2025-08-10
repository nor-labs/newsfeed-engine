FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY build/libs/newsfeed-parser-service-fat.jar newsfeed-parser-service.jar

EXPOSE 8888

CMD ["java", "-jar", "newsfeed-parser-service.jar"]
