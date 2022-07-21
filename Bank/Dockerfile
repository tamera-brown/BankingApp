FROM openjdk:11
LABEL maintainer="tamerabrown642@gmail.com"
VOLUME /main-app
ADD build/libs/Bank-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]
