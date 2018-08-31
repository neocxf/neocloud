FROM java:8-jre
MAINTAINER Neo Chen <neocxf@gmail.com>

ADD ./target/shoppingcart-ui.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/shoppingcart-ui.jar"]

EXPOSE 4000