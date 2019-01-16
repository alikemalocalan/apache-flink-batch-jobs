# docker-flink


## Informations

* Based on Alpine official Image [openjdk:8-jre-alpine]
* Install [Docker](https://www.docker.com/)


## Build

        docker build --rm=true -t ty-flink-1.7:lastest .

## Usage
        docker run --name ty-flink-jobs --link flink-master:flink-master -d ty-flink-1.7:lastest


## UI Links

docker exec -it $(docker ps --filter name=ty-flink-1.7 --format={{.ID}}) flink run -c class.Name /new/job.jar


## TRİCKS

* Open Terminal inside Docker image

        docker exec -it  $(docker-compose ps -q ty-flink-1.7) bash
        

* With Root Permission

        docker exec -it -u 0  $(docker-compose ps -q ty-flink-1.7) bash

 
## Scale the number of workers

Easy scaling using docker-compose:

        docker-compose scale worker=5

This can be used to scale to a multi node setup using docker swarm.