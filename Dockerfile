FROM openjdk:14
LABEL maintener="jl.protois.perso@gmail.com"
EXPOSE 8085
ARG JAR_FILE=target/P9_WebApp-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} webapp.jar
ENTRYPOINT ["java","-jar","/webapp.jar"]