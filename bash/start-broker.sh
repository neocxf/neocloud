PID=`ps -ef | grep java | grep BrokerStartup | awk '{print $2}'`
if [ -n "$PID" ]; then       
       	kill -9 $PID
fi 
nohup sh /home/neo/pub/rocketmq/bin/mqbroker -n $NAMESRV_ADDR autoCreateTopicEnable=true > mqbroker.log &
