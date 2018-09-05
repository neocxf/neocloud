FROM java:8-jre
MAINTAINER Neo Chen <neocxf@gmail.com>

ENV JAVA_OPTS="-Xss256k -Xmx60m -XX:MetaspaceSize=105m -XX:CompressedClassSpaceSize=25m -XX:ReservedCodeCacheSize=12m"

ADD ./target/customer-service.jar /app.jar

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar

EXPOSE 9004