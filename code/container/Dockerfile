# Use a base image that supports Java
#FROM openjdk:11-jre-slim
#FROM tomcat:9.0
FROM tomcat:8.0-alpine

# Set environment variables for Tomcat
ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH

WORKDIR "${CATALINA_HOME}/webapps"

# Create a directory for your WAR file
#RUN mkdir -p /usr/local/pennywise

# Copy your WAR file into the container
#COPY pennywise.war .
ADD pennywise.war . 
RUN chmod 755 pennywise.war

# Expose the default Tomcat port (8080)
EXPOSE 8080

# Start Tomcat and deploy your WAR file
CMD ["catalina.sh", "run"]

