FROM openjdk:11
ARG JAR_FILE_PATH=build/libs/\*.jar
COPY startup.sh .
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["/bin/bash", "-c", "./startup.sh"]


