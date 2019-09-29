#!/usr/bin/env bash
mvn clean install



docker run --rm -it -p 8761:8761 -m 320m -e "eureka.instance.hostname=10.200.157.48" $(docker build -t neocloud/eureka -q eureka-registry/)
docker run --rm -it -p 8080:8080 -m 320m -e "eureka.instance.hostname=10.200.157.48" $(docker build -t neocloud/gateway -q gateway/)
docker run --rm -it -p 9002:9002 -m 370m -e "eureka.instance.hostname=10.200.157.48" -e "MQ_ADDR=10.200.157.48" $(docker build -t neocloud/cart -q cart-service/)
docker run --rm -it -p 8888:8888 -m 370m -e "eureka.instance.hostname=10.200.157.48" $(docker build -t neocloud/config -q config/)
docker run --rm -it -p 9005:9005 -m 320m -e "eureka.instance.hostname=10.200.157.48" $(docker build -t neocloud/catlog -q catalog-service/)
docker run --rm -it -p 9001:9001 -m 320m -e "eureka.instance.hostname=10.200.157.48" $(docker build -t neocloud/inventory -q inventory-service/)
docker run --rm -it -p 9006:9006 -m 320m -e "eureka.instance.hostname=10.200.157.48" $(docker build -t neocloud/orderEntity -q orderEntity-service/)
docker run --rm -it -p 9003:9003 -m 370m -e "eureka.instance.hostname=10.200.157.48" $(docker build -t neocloud/util -q util-service/)
docker run --rm -it -p 9004:9004 -m 320m -e "eureka.instance.hostname=10.200.157.48" $(docker build -t neocloud/customer -q customer-service/)

