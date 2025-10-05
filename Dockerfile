# Stage 1: Build the application using a Maven base image
# This image already has Java 11 and Maven installed.
FROM maven:3.8.5-openjdk-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Run the Maven package command to build the executable JAR.
# The "-DskipTests" flag is good practice to speed up the build.
RUN mvn package -DskipTests


# Stage 2: Create the final, smaller image to run the application
# This uses a much smaller base image that only has the Java Runtime (JRE).
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy ONLY the built JAR file from the 'build' stage into this final image.
# This makes our final image much smaller and more secure.
COPY --from=build /app/target/s3-thumbnail-generator-1.0-SNAPSHOT.jar .

# This is the command that will be executed when the container starts.
# It's the same command you used in your terminal.
ENTRYPOINT ["java", "-jar", "s3-thumbnail-generator-1.0-SNAPSHOT.jar"]