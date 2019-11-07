PID=$(ps -ef | grep java | grep rocketmq-console | awk '{print $2}')
if [ -n "$PID" ]; then
	kill -9 $PID
fi
nohup java -Xmx500m -Xmn350m -jar /home/neo/pub/rocketmq-console/target/rocketmq-console-ng-1.0.0.jar -n $NAMESRV_ADDR  > mqconsole.log &
