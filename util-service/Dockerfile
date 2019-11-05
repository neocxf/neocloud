FROM openjdk:8-jdk-alpine

MAINTAINER Neo Chen <neocxf@gmail.com>

ENV JAVA_OPTS=" -Xms300M \
    -Xmx300M \
    -Xmn200M \
    -XX:SurvivorRatio=4 \
    -XX:MetaspaceSize=100M \
    -XX:MaxMetaspaceSize=100M \
    -XX:+UseParNewGC \
    -XX:+UseConcMarkSweepGC \
    -XX:CMSInitiatingOccupancyFraction=92 \
    -XX:+UseCMSCompactAtFullCollection \
    -XX:CMSFullGCsBeforeCompaction=0 \
    -XX:+CMSParallelInitialMarkEnabled \
    -XX:+CMSScavengeBeforeRemark \
    -XX:+DisableExplicitGC \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/tmp \
    -XX:+PrintGCDetails \
    -XX:+PrintGCTimeStamps \
    "
ENV HOST="localhost"

VOLUME /tmp

ARG JAR_FILE

ADD target/${JAR_FILE} /app.jar

ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom -jar -Dserver.port=80 /app.jar

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:80/actuator/health || exit 1

EXPOSE 80