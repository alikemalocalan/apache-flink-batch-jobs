#!/usr/bin/env bash

sbt clean assembly
cp ./target/scala-2.1*/ty-flink-jobs-assembly-0.1.jar ty-flink-jobs-assembly-0.1.jar
rm -rf ./target/ ./project/target/ ./project/project/

docker build --rm=true -t ty-flink-1.7:lastest .
docker-compose -f docker-compose.yml up -d