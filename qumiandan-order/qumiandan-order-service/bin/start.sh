#!/bin/bash
export JAVA_HOME=/opt/jdk/jdk1.8.0_191
echo ${JAVA_HOME}
echo "授权当前用户"
chmod 777 /opt/deploy/app/qumiandan-order-service-*.jar
echo "执行...."
cd /opt/deploy/app
nohup ${JAVA_HOME}/bin/java -jar qumiandan-order-service-*.jar > /dev/null &
echo "启动成功"
