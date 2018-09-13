FROM openjdk:8-jdk-alpine
#FROM java:8-jre

MAINTAINER Neo Chen <neocxf@gmail.com>

ENV JAVA_OPTS="-Xss256k -Xmx60m -XX:MetaspaceSize=140m -XX:CompressedClassSpaceSize=35m -XX:ReservedCodeCacheSize=15m"

RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*

ADD ./target/config.jar /app.jar

CMD exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8888/actuator/health || exit 1

EXPOSE 8888