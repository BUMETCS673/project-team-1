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

With using React and MUI, we are able to utilize the following template in order to build out our components:
````html
<div className="app">

   <NavBar/>
   
    <Routes>
      
      <Route path="/" element={<Login/>}></Route>
      <Route path="/dashboard" element={<Dashboard/>}></Route>
  

    </Routes>
    

   </div>
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

## Deployment

The application is deployed on Heroku Cloud Platform which is very simple to use. Although it is no longer free. After evaluation between Render and Heroku, we chose Heroku as it is a better fit for deployment since it provides better support of the components we need for our application.

## Heroku Dyno

Dyno is the Heroku term of container. We are building out 4 dynos for our project.
- Front End (UI)
- OAuth
- Back End (REST API)
- Database
  The Database Dyno contains the mariaDB which is running as a separate instance on its own. We configure it with running the sqlscript   to build the database. The connection string, username, password are all stored in Github actions secret for subsequent deployment.     Secrets are not exposed.

<img width="528" alt="image" src="https://github.com/BUMETCS673/project-team-1/assets/33763916/eef0404f-d3ac-4c93-a2a2-fe21ed0b6a3c">


## Docker Container
Docker container is used as our deployment vechicle. The following shows the Dockerfile for our Front End which is Nodejs/React app.
<img width="618" alt="image" src="https://github.com/BUMETCS673/project-team-1/assets/33763916/9c5da594-58d1-486c-b82d-d9cdbd977bc4">


## Configure Deployment with Git
We use Github action to  orchestrate the build and deployment process. Besides the database, each components has its own Dockerfile. We will build the Dockerfile to make sure there are no issue
<img width="720" alt="image" src="https://github.com/BUMETCS673/project-team-1/assets/33763916/33222489-a538-43ef-8cf3-a9e9432360f4">
The Github Actions are automatically triggered when there is a push on the feture branches or a Pull Request on our dev/main branches. Making sure the latest code is deployed in our Heroku environment.


## Pennywise UI on Heroku
Upon successful deployment, we are able to access our application through the provided url.
<img width="773" alt="image" src="https://github.com/BUMETCS673/project-team-1/assets/33763916/5cc732b1-c1db-4a81-a47f-783912f63194">

## Secret Handling
Sensitive information are all being store in secrets. Not exposed to anyone during build or deployment.

## Tools to test Deployment
- Insomnia
  Used for manual test REST API
- MySQL workbench
  Used for manual testing Database to check if data is being saved properly
