java_opts="
-Xms512M
-Xmx1024M
-Xmn400M
-Xss512k
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
-Xloggc:inventory-gc.log
"

PROGRAM_NAME=inventory-service

cd ../$PROGRAM_NAME

mvn clean package -DskipTests

java -jar $java_opts target/${PROGRAM_NAME}.jar