FROM openjdk:8-jdk-alpine

MAINTAINER Neo Chen <neocxf@gmail.com>

ENV JAVA_OPTS="-Xss256k -Xmx60m -XX:MetaspaceSize=105m -XX:CompressedClassSpaceSize=25m -XX:ReservedCodeCacheSize=12m"

RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*

ADD ./target/customer-service.jar /app.jar

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:9004/actuator/health || exit 1

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar

EXPOSE 9004