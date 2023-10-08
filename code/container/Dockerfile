# Use a base image that supports Java
#FROM openjdk:11-jre-slim
#FROM tomcat:9.0
#FROM tomcat:8.0-alpine
FROM amazoncorretto:21.0.0-al2023-headful
#FROM amazoncorretto:17.0.8-al2023-headless

# Set environment variables for Tomcat
ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH

#WORKDIR "${CATALINA_HOME}/webapps"

# Create a directory for your WAR file
#RUN mkdir -p /usr/local/pennywise
RUN mkdir -p /usr/local/pennywise-ui

# Copy your WAR file into the container
#COPY pennywise.war .
#ADD pennywise.war . 
ADD pennywise.jar /app/pennywise.jar
RUN chmod 755 /app/pennywise.jar


# Copy the Front end files
ADD pennywise-ui/ /usr/local/pennywise-ui/

# Copy start script
ADD start_service.sh .
RUN chmod 755 start_service.sh

# Expose the default Tomcat port (8080)
#EXPOSE 8080

# Start Tomcat and deploy your WAR file
#CMD ["catalina.sh", "run"]
#CMD ["java", "-jar", "/app/pennywise.jar", "--server.port=$PORT"]
#CMD ["start_service.sh"]
CMD java -jar app/pennywise.jar