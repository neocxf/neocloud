FROM java:8-jre
MAINTAINER Neo Chen <neocxf@gmail.com>

ADD ./target/customer-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/customer-service.jar"]

EXPOSE 4000