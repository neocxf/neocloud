FROM java:8-jre
MAINTAINER Neo Chen <neocxf@gmail.com>

ADD ./target/inventory-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/inventory-service.jar"]

EXPOSE 4000