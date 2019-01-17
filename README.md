# Apache-Flink-Batch-Jobs


## Informations

* Based on Alpine official Image [openjdk:8-jre-alpine]
* Install [Docker](https://www.docker.com/) and [Sbt](https://www.scala-sbt.org/)


## Run With One Command

        ./deploy-docker.sh 

 and follow jobs via [Apache Flink Web UI](http://localhost:8081)

## Build Docker 

        docker build --rm=true -t apache-flink-batch-jobs:lastest .

## Run Docker Image
        
        docker-compose -f docker-compose.yml up -d


## Submit Any Job

        docker exec -it $(docker ps --filter name=ty-flink-1.7 --format={{.ID}}) flink run -c class.Name /new/job.jar


## Others

* Open Terminal inside Docker image

        docker exec -it  $(docker ps --filter name=flink-jobs --format={{.ID}}) bash
        

* With Root Permission

        docker exec -it -u 0 $(docker ps --filter name=flink-jobs --format={{.ID}}) bash
 
* Scale the number of workers

        docker-compose scale worker=5
