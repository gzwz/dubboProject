echo "开始停止..."
pid=`ps -ef | grep qumiandan-web-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}'`
echo “旧应用进程id：$pid”
if [ -n "$pid" ]
then
kill -9 $pid
fi
