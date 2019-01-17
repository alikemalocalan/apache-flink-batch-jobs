#!/usr/bin/env bash

sbt clean assembly
cp ./target/scala-2.1*/apache-flink-batch-jobs-assembly-0.1.jar apache-flink-batch-jobs-assembly-0.1.jar
rm -rf ./target/ ./project/target/ ./project/project/

docker build --rm=true -t apache-flink-batch-jobs:latest .
docker-compose -f docker-compose.yml up -d