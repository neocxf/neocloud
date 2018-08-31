FROM java:8-jre
MAINTAINER Neo Chen <neocxf@gmail.com>

ADD ./target/order-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/order-service.jar"]

EXPOSE 4000