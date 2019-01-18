# Apache-Flink-Batch-Jobs


## Informations

* Based on Alpine official Image [openjdk:8-jre-alpine]
* Install [Docker](https://www.docker.com/) and [Sbt](https://www.scala-sbt.org/)


### Build and Run with a Command

        ./deploy-docker.sh 

 and follow jobs via [Apache Flink Web UI](http://localhost:8081)

#### Build Docker 

        docker build --rm=true -t apache-flink-batch-jobs:lastest .

#### Run Docker Image
        
        docker-compose -f docker-compose.yml up -d


#### Submit Any Job

        docker exec -it $(docker ps --filter name=apache-flink-batch-jobs --format={{.ID}}) flink run -c com.github.alikemalocalan.flink.MainFlow /usr/src/app/apache-flink-batch-jobs-assembly-0.1.jar


#### Others

* Open Terminal inside Docker image

        docker exec -it  $(docker ps --filter name=flink-jobs --format={{.ID}}) bash
        

* With Root Permission

        docker exec -it -u 0 $(docker ps --filter name=flink-jobs --format={{.ID}}) bash
 
* Scale the number of workers

        docker-compose scale worker=5
