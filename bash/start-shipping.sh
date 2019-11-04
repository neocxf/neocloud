java_opts="
-Xms300M
-Xmx300M
-Xmn200M
-XX:SurvivorRatio=6
-XX:MetaspaceSize=130M
-XX:MaxMetaspaceSize=130M
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

PROGRAM_NAME=shipping-service

cd ..

mvn --projects $PROGRAM_NAME  spring-boot:run -Dspring-boot.run.jvmArguments="-D$java_opts"
