#FROM openjdk:latest
#COPY target/*.jar app.jar
#EXPOSE 8089
#ENTRYPOINT ["java","-jar", "app.jar"]

FROM openjdk:17
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]