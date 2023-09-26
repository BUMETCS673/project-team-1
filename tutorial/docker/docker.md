This is a short tutorial how the docker works

First, you will need to install docker, for instance, if you are using a mac

https://docs.docker.com/desktop/install/mac-install/

Download the dmg and install, follow the instruction to install docker, just accept the default.

After you have installed, you should see docker in launchpad.

In this directory, you will see a Dockerfile and entrypoint.sh

Launch a terminal on your mac and run

docker -v
Docker version 24.0.6, build ed223bc 


Now run the command below
chmod 755 Dockerfile
chmod 755 entrypoint.sh
docker build . -t sonarqube:latest

After it is completed, run the following command

docker images

and you should see it returns
REPOSITORY   TAG       IMAGE ID       CREATED             SIZE
sonarqube    latest    80a9f9574ddc   57 minutes ago      716MB

Now you can launch the container, run
docker run -p 9000:9000 sonarqube:latest

open your browser and go to

https://localhost:9000

You should see sonarqube login page
and you can login with admin and password admin






