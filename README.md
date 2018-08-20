# MusicHub App

MusicHub App is a online Music Streaming app with a functionality to browse millions of tracks, albums, playlist and artists for free.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a local machine.

### Prerequisites

* Node.js
* npm

Please follow the below instruction to install prerequisites on your system:
* Download the Windows installer from the [Nodes.js® web site.](https://nodejs.org/en/)
* Run the installer (the .msi file you downloaded in the previous step.)
* Follow the prompts in the installer (Accept the license agreement, click the NEXT button a bunch of times and accept the default installation settings).
installer
* Restart your computer. You won’t be able to run Node.js® until you restart your computer. 
#### Test the prerequisites:
Make sure you have Node and NPM installed by running simple commands to see what version of each is installed and to run a simple test program:

* Test Node. To see if Node is installed, open the Windows Command Prompt, Powershell or a similar command line tool, and type node -v. This should print a version number, so you’ll see something like this v0.10.35.
* Test NPM. To see if NPM is installed, type npm -v in Terminal. This should print NPM’s version number so you’ll see something like this 1.4.28
* Create a test file and run it. A simple way to test that node.js works is to create a JavaScript file: name it hello.js, and just add the code console.log('Node is installed!');. To run the code simply open your command line program, navigate to the folder where you save the file and type node hello.js. This will start Node and run the code in the hello.js file. You should see the output Node is installed!.

### Installing and Running Application on Local Machine

Download this front-end repo. Navigate into the project folder and execute the below command:

```
ng install
```

And then execute the below command:

```
ng serve
```

Now you should be able to see the apllication running at http://localhost:4200/ 

## Deployment

This repo contains the code for frontend of the MusicHubApp
The backend code is located [here](https://github.com/sreekmatta/music-hub-app-backend) 
The backend code is deployed on [Heroku](https://music-hub-app-springboot.herokuapp.com/api/person) 

## Demo on [Youtube](https://youtu.be/JPWXj6A1Q_s)

## Built With

* [Angular 6.0.8](http://www.dropwizard.io/1.0.2/docs/) - Frontend Framework
* [SpringBoot](https://spring.io/projects/spring-boot) - Backend Framework

## Developers

* **Sree Keerthi Matta** - [GitHub](https://github.com/sreekmatta)
* **Samanjate Sood** - [GitHub](https://github.com/samanjate)

## 3rd Party API

We have used below 3rd party APIs for our project:
* [Napster API](https://developer.napster.com/api/v2.2)
* [Amazon S3](https://aws.amazon.com/s3/)

## Acknowledgments

* Inspired from Spotify, Napster

