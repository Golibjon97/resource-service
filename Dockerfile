# Assign a base image
FROM amazoncorretto:17-alpine-jdk

# Maintainer / author
LABEL maintainer="golibjon_davlatov@epam.com"

# Specify the argument for the jar file
ARG JAR_FILE=build/libs/resource-service-1.0-SNAPSHOT.jar

# Copy the jar file to the docker image
COPY ${JAR_FILE} app.jar

# Set the start point
ENTRYPOINT ["java","-jar","/app.jar"]