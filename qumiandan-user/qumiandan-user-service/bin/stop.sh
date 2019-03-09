echo "开始停止..."
pid=`ps -ef | grep qumiandan-user-service-2.0.6.RELEASE.jar | grep -v grep | awk '{print $2}'`
echo “旧应用进程id：$pid”
if [ -n "$pid" ]
then
kill -9 $pid
fi
