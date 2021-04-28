# P9_WEBAPP

Micro-service webApp the UI for the application Mediscreen.
- Manage User, Patient, note.
- visualize the level of risk
 

## Prerequisite to run

- Java 1.8
- Spring Boot 2.2.6
- Docker

## Installation
### Host file

- 127.0.0.1 note
- 127.0.0.1 assessment
- 127.0.0.1 webapp
- 127.0.0.1 patient
- 127.0.0.1 mongodb

### Docker image construction in project directory
~~~
docker build --build -t webapp .
~~~
### Docker execution
if docker-compose is not use
~~~
docker run -p 8085:8085 --name P9-webapp webapp
~~~~
if docker-compose is user (directory patient)
~~~
docker-compose up -d
~~~~
   
## Global architecture
![alt text](schema.jpg)