FROM openjdk:8-jdk-alpine

MAINTAINER Neo Chen <neocxf@gmail.com>

ENV JAVA_OPTS="-Xss256k -Xmx60m -XX:MetaspaceSize=140m -XX:CompressedClassSpaceSize=35m -XX:ReservedCodeCacheSize=15m"

RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*

ADD ./target/cart-service.jar /app.jar

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar

EXPOSE 9002