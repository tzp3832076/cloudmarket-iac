#!/bin/sh
JAVA=java
if [ "$JAVA_8_HOME" != "" ]; then
        JAVA=$JAVA_8_HOME/bin/java
fi

LOG_DIR=../log
GC_LOG_FILE=$LOG_DIR/gc.log
SERVICE_NAME="mkt-iac-api"
APPLICATION=$SERVICE_NAME.jar
SPRING_CONFIG_FILE=../conf/application.properties
ENDPOTIN_CONFIG_FILE=file:../conf/endpoint.json
MONITOR_DUMP_FILE=../monitor/$SERVICE_NAME.bvar.data
MAX_MEMORY=2048M
MAX_PERM_MEMORY=512M
nohup $JAVA -Dlog.dir=$LOG_DIR -Dservice.name=$SERVICE_NAME -Dbvar.dump.file=$MONITOR_DUMP_FILE \
-Dspring.config.location=$SPRING_CONFIG_FILE -Dendpoint.config=$ENDPOTIN_CONFIG_FILE \
-Dfile.encoding=UTF-8 \
-Xmx$MAX_MEMORY -XX:MaxPermSize=$MAX_PERM_MEMORY -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails \
-XX:+PrintGCDateStamps -Xloggc:$GC_LOG_FILE -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 \
-XX:GCLogFileSize=20M -jar $APPLICATION > /dev/null 2>&1 &
