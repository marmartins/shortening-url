FROM openjdk:8-jdk-alpine
VOLUME /opt
EXPOSE 8080
USER USRredis_

ARG JAR_FILE=bin/short-url-services-0.0.1-SNAPSHOT.jar
ARG CONFIG_FILE=cfg/application.properties

ADD ${JAR_FILE} /opt/short-url-services.jar
ADD ${CONFIG_FILE} /opt/application.properties

ENTRYPOINT ["/usr/bin/java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/short-url-services.jar", "--spring.config.location=classpath:/opt/application.properties"]