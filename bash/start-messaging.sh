java_opts="
-Xms300M
-Xmx300M
-Xmn200M
-XX:SurvivorRatio=4
-XX:MetaspaceSize=100M
-XX:MaxMetaspaceSize=100M
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

PROGRAM_NAME=messaging

# cd ../$PROGRAM_NAME

# mvn clean package -DskipTests

# java -jar $java_opts target/${PROGRAM_NAME}.jar

cd ..

mvn --projects $PROGRAM_NAME  spring-boot:run -Dspring-boot.run.jvmArguments="-D$java_opts"
# mvn --projects $PROGRAM_NAME  spring-boot:run