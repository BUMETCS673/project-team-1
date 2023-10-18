# Pennywise Budgeting Application

## Overview
Pennywise is a budgeting application that aims to give users a platform that allows them to visualize their spending and have a better understanding and have better control over it.

We've utilized Spring Boot, React and Material UI (MUI) to build.

## Setup
To setup a local environment to run this application, developers will need to utilize the following:
- Java
- Maven
- Docker
- Postman
- IntelliJ IDE
- Visual Studio Code
- NPM
- MariaDB

# Installation
1. Clone our repository
````terminal
git clone https://github.com/BUMETCS673/project-team-1.git
cd project-team-1
````
2. Install frontend dependencies
````terminal
cd code/pennywise-ui
npm install
````
3. Install backend dependencies
````terminal
cd code/back-end
./mvnw clean install
````

## Version Control
GitHub is used as the SVN. Do not work from the dev or master branch, only front-end or back-end depending on the code change.

## Run in IDE
In order to run the code, the developer will need to open the repository in Visual Studio Code, open the terminal window, enter 'cd code/pennywise-ui' then run 'npm i' followed by 'npm start'. This will open the react local project in a tab on your browser. Next, you'll want to open the repository in IntelliJ, and build the Maven project there. Finally, ensure that Docker is running and the application will be ready to go.

# Frontend
The application is built in the frontend utilizing ReactJS and MUI framework. On load, the user will open the 'Sign In' page where they are prompted to sign in via google. If this is the first sign in for a user, they will then be prompted to set up their profile. Once the profile is set, the user will be brought to the dashboard page where they can view all of their existing expenses, see how they compare to their income, and view a chart that displays how much of their total income is going to each of those expenses. The navigation allows users to add expenses, add income, and edit their profile. At any point, the user is able to log out of the application.

With using React and JSX, we are able to utilize the following HTML template in order to build out our components:
````html
<!DOCTYPE html>
<html xmlns:th="https://www.thymeleaf.org">
<head lang="en">
    <meta charset="UTF-8"/>
    <title>Trackr</title>
</head>
<body>

<div id="react"></div>

<script src="built/bundle.js"></script> // Load JS for the app

</body>
</html>
````

## NPM
In order to work with the frontend and test on a local environment, the developer will need to ensure that npm is installed:
```
npm install
```

## MUI Framework
Material UI features a host of components that are prebuilt and ready for use that make the front end development process far quicker and more efficient. More information can be found on the framework's site - https://mui.com/material-ui/

# Backend
To set up the backend, open the project files in IntelliJ and navigate to the pom.xml file where you can then build the project. Once the project is done building you can run it in your IDE.

## Builds
- Automated Build will be launched after pull request and merged to dev branch
- Automated Build will be launched after tested and merged to master branch
- Maven will be used to handle the build process

# Docker
Container Name: pennywise_container
• The pennywise_container in Docker is meant to be used to run both the database and backend code for local testing purposes

### Install and Setup
• Install Docker on your machine
• Run the pre-built image provided on Docker Hub, or, build the image yourself from the source code

