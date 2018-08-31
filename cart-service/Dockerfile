FROM java:8-jre
MAINTAINER Neo Chen <neocxf@gmail.com>

ADD ./target/cart-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/cart-service.jar"]

EXPOSE 4000