#!/usr/bin/env bash

case "$1" in
auth-service|cart-service|catalog-service|inventory-service|messaging|order-service|shipping-service|reward-service|gateway|eureka-registry)
  mvn --projects $1 clean package
  scp $1/target/$1.jar upload@52.34.176.89:/home/upload/wars/
  ssh upload@52.34.176.89 << ENDSSH

    cd /home/upload/wars/

    JAVA_OPTS="
    -Xms300M
    -Xmx300M
    -Xmn200M
    -XX:SurvivorRatio=6
    -XX:MetaspaceSize=120M
    -XX:MaxMetaspaceSize=120M
    -XX:+UseParNewGC
    -XX:+UseConcMarkSweepGC
    -XX:CMSInitiatingOccupancyFraction=92
    -XX:+UseCMSCompactAtFullCollection
    -XX:CMSFullGCsBeforeCompaction=0
    -XX:+CMSParallelInitialMarkEnabled
    -XX:+CMSScavengeBeforeRemark
    -XX:+DisableExplicitGC
    -XX:+HeapDumpOnOutOfMemoryError
    -XX:HeapDumpPath=.
    -XX:+PrintGCDetails
    -XX:+PrintGCTimeStamps
    "

    nohup java -jar $JAVA_OPTS -DHOST=$HOST $1.jar >> $1.log &

    echo 'work done'
ENDSSH
  ;;

*)
  echo "Usage: $0 {auth-service|cart-service|catalog-service|inventory-service|messaging|order-service|shipping-service|reward-service|gateway|eureka-registry}" >&2
  exit 3
  ;;
esac