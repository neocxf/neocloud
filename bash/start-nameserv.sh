PID=`ps -ef | grep java | grep NamesrvStartup | awk '{print $2}'`
if [ -n "$PID" ]; then      
    kill -9 $PID
fi 
nohup sh /home/neo/pub/rocketmq/bin/mqnamesrv > mqnameserv.log &
