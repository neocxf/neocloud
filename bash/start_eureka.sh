java_opts="
-Xms256M
-Xmx512M
-XX:MetaspaceSize=128M
-XX:MaxMetaspaceSize=256M
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
-Xloggc:eureka-gc.log
"

PROGRAM_NAME=eureka-registry

cd ../$PROGRAM_NAME

mvn clean package -DskipTests

java -jar $java_opts target/${PROGRAM_NAME}.jar