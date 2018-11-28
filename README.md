# shortening-url
Project to maintenance of Shortenings URLS

###Prerequisites:
* Any IDE
* Java 8
* Docker (follow this link to install https://store.docker.com/search?type=edition&offering=community)

###Development Environment:
* Redis:
    - I'm assuming that no Redis available in your machine, if it is running please stop it to avoid port conflicts, and use following command to run pre-configured redis
      ```docker-compose -f redis.yml up```

* Shorting URL API:
    - To start the application run ShortUrlServicesApplication.java main method from your IDE
    and to check that it is running open **http://localhost:8080 from your browser**.
    - The API documentation can be viewed on **http://localhost:8080/swagger-ui.html**

### Deploy
Execute the steps;
```mvn clean install assembly:single```
- On the _target_ directory you should find the file __short-url-services-0.0.1-SNAPSHOT-distribution.zip__
- Use unzip command
```unzip short-url-services-0.0.1-SNAPSHOT-distribution -d destination-folder```

You'll see the directory tree below.

>- bin
 - bin/short-url-services-0.0.1-SNAPSHOT.jar
- Dockerfile
- redis.yml
- cfg
 - cfg/log4j2.xml
 - cfg/application.properties

Execute from Dockerfile
```
docker build -t short-url-service:1.0 .
```
```
docker run -i -t --name short-url-service -p 8080:8080 -d short-url-service:1.0
```
