java_opts="
-Xms400M
-Xmx400M
-Xmn300M
-XX:SurvivorRatio=6
-XX:MetaspaceSize=128M
-XX:MaxMetaspaceSize=128M
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

PROGRAM_NAME=order-service


cd ..

#mvn --projects $PROGRAM_NAME  spring-boot:run -Dspring-boot.run.jvmArguments="-D$java_opts"
mvn -pl $PROGRAM_NAME  -am spring-boot:run -Dspring-boot.run.jvmArguments="-D$java_opts"
