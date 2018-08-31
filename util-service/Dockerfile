FROM java:8-jre
MAINTAINER Neo Chen <neocxf@gmail.com>

ADD ./target/util-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/util-service.jar"]

EXPOSE 4000