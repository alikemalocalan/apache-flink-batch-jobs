#!/bin/sh

###############################################################################
#  Licensed to the Apache Software Foundation (ASF) under one
#  or more contributor license agreements.  See the NOTICE file
#  distributed with this work for additional information
#  regarding copyright ownership.  The ASF licenses this file
#  to you under the Apache License, Version 2.0 (the
#  "License"); you may not use this file except in compliance
#  with the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
# limitations under the License.
###############################################################################

# If unspecified, the hostname of the container is taken as the JobManager address
#JOB_MANAGER_RPC_ADDRESS=${JOB_MANAGER_RPC_ADDRESS:-$(hostname -f)}
JOB_MANAGER_RPC_ADDRESS="127.0.0.1"
FLINK_MASTER_PORT_6123_TCP_PORT="6123"

drop_privs_cmd() {
    if [ -x /sbin/su-exec ]; then
        # Alpine
        echo su-exec
    else
        # Others
        echo gosu
    fi
}


echo "Starting Job Manager"
sed -i -e "s/jobmanager.rpc.address: localhost/jobmanager.rpc.address: ${JOB_MANAGER_RPC_ADDRESS}/g" "$FLINK_HOME/conf/flink-conf.yaml"
echo "blob.server.port: 6124" >> "$FLINK_HOME/conf/flink-conf.yaml"
echo "query.server.port: 6125" >> "$FLINK_HOME/conf/flink-conf.yaml"

echo "config file: " && grep '^[^\n#]' "$FLINK_HOME/conf/flink-conf.yaml"

$FLINK_HOME/bin/jobmanager.sh start
sleep 10s


TASK_MANAGER_NUMBER_OF_TASK_SLOTS=${TASK_MANAGER_NUMBER_OF_TASK_SLOTS:-$(grep -c ^processor /proc/cpuinfo)}

sed -i -e "s/jobmanager.rpc.address: localhost/jobmanager.rpc.address: ${JOB_MANAGER_RPC_ADDRESS}/g" "$FLINK_HOME/conf/flink-conf.yaml"
sed -i -e "s/taskmanager.numberOfTaskSlots: 1/taskmanager.numberOfTaskSlots: $TASK_MANAGER_NUMBER_OF_TASK_SLOTS/g" "$FLINK_HOME/conf/flink-conf.yaml"
echo "blob.server.port: 6124" >> "$FLINK_HOME/conf/flink-conf.yaml"
echo "query.server.port: 6125" >> "$FLINK_HOME/conf/flink-conf.yaml"

sleep 5s
echo "Starting Task Manager"
echo "config file: " && grep '^[^\n#]' "$FLINK_HOME/conf/flink-conf.yaml"
$FLINK_HOME/bin/taskmanager.sh start

sleep 5s
echo "Submit starting"
$FLINK_HOME/bin/flink run -c ${FLINK_APPLICATION_MAIN_CLASS} ${FLINK_APPLICATION_JAR_LOCATION} ${FLINK_APPLICATION_ARGS}
echo "Submit finish"

while true
do
  # (1) prompt user, and read command line argument
  read -p "For Exit, enter any key... " answer
done