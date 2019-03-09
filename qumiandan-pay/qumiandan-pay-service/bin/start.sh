#!/bin/bash
export JAVA_HOME=/opt/jdk/jdk1.8.0_191
echo ${JAVA_HOME}
echo "授权当前用户"
chmod -R 777 /opt/deploy/app/qumiandan-pay-service-*.jar
echo "执行...."
cd /opt/deploy/app
nohup ${JAVA_HOME}/bin/java -jar qumiandan-pay-service-*.jar > /dev/null &
echo "启动成功"
