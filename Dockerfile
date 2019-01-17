FROM flink:1.7.1-hadoop28-scala_2.11-alpine

ENV FLINK_APPLICATION_JAR_LOCATION /usr/src/app/apache-flink-batch-jobs-assembly-0.1.jar
ENV FLINK_APPLICATION_JAR_NAME apache-flink-batch-jobs-assembly-0.1.jar
ENV FLINK_APPLICATION_MAIN_CLASS com.github.alikemalocalan.flink.MainFlow
ENV FLINK_APPLICATION_ARGS "--inputcsv /usr/src/app/data/case.csv --outputpath /usr/src/app/result/"
ENV FLINK_MASTER_LOG /usr/local/flink/log

COPY submit.sh /
COPY apache-flink-batch-jobs-assembly-0.1.jar /usr/src/app/

RUN chmod +x /submit.sh
RUN mkdir /usr/src/app/result
RUN mkdir /usr/src/app/data

WORKDIR /usr/src/app

ENTRYPOINT ["/submit.sh"]
EXPOSE 6123 22 8080 8081 9000