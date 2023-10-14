FROM node:latest
FROM node:16.15.0

    RUN apt-get update \
     && apt-get install default-jre -y

RUN mkdir -p /usr/local/pennywise-ui

#ADD pennywise.jar /app/pennywise.jar
#RUN chmod 755 /app/pennywise.jar

# Copy the Front end files
WORKDIR "/usr/local/pennywise-ui"
COPY ./ /usr/local/pennywise-ui/
RUN npm install @mui/x-date-pickers
RUN npm install date-fns
RUN npm install dayjs
RUN npm install luxon
RUN npm install moment
RUN npm install @babel/plugin-proposal-private-property-in-object
RUN npm install
RUN npm run build
#RUN npm build
RUN npm install -g serve

# Copy start script
#ADD start_service.sh .
#RUN chmod 755 start_service.sh

# Expose the default port (3000)
EXPOSE 3000

#ENTRYPOINT ["./start_service.sh"]
#ENTRYPOINT ["serve", "-s", "build"]
ENTRYPOINT ["npm", "start"]