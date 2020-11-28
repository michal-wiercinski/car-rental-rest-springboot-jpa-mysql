FROM openjdk:11.0.9-slim
VOLUME /tmp
EXPOSE 5000
ARG JAR_FILE
COPY ${JAR_FILE} car-rental-0.0.1-SNAPSHOT.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /car-rental-0.0.1-SNAPSHOT.jar" ]
