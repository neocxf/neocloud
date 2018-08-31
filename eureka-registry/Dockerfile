FROM java:8-jre
MAINTAINER Neo Chen <neocxf@gmail.com>

ADD ./target/eureka-registry.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/eureka-registry.jar"]

EXPOSE 4000