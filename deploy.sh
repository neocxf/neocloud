#!/usr/bin/env bash

case "$1" in
auth-service|cart-service|catalog-service|inventory-service|messaging|order-service|shipping-service|reward-service|gateway|eureka-registry)
  mvn --projects $1 clean package
  export ZK_HOST=192.168.2.104
  export REDIS_HOST=192.168.2.104
  export MY_HOST=192.168.2.104
  export MQ_HOST=192.168.2.104
  export HOST=192.168.2.104
  export JAVA_OPTS="-Xms300M -Xmx300M -Xmn200M -XX:SurvivorRatio=6 -XX:MetaspaceSize=120M -XX:MaxMetaspaceSize=120M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=92 -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:+CMSParallelInitialMarkEnabled -XX:+CMSScavengeBeforeRemark -XX:+DisableExplicitGC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=. -XX:+PrintGCDetails -XX:+PrintGCTimeStamps"
  scp $1/target/$1.jar neo@192.168.2.104:/home/neo/pub/

  ssh neo@192.168.2.104 << ENDSSH

    cd /home/neo/pub/

    PID=$(ps -ef | grep java | grep $1 | awk '{print $2}')

    if [ -n "$PID" ]; then
      kill -9 $PID
    fi

    echo "nohup java $JAVA_OPTS -jar  -DHOST=$HOST -DZK_HOST=$ZK_HOST -DREDIS_HOST=$REDIS_HOST -DMY_HOST=$MY_HOST -DMQ_HOST=$MQ_HOST $1.jar >> $1.log &" > $1.sh

    chmod u+x $1.sh

    sh $1.sh

    echo 'work done'
ENDSSH
  ;;

*)
  echo "Usage: $0 {auth-service|cart-service|catalog-service|inventory-service|messaging|order-service|shipping-service|reward-service|gateway|eureka-registry}" >&2
  exit 3
  ;;
esac