# neo cloud

## Quick start
1. start the app docker way
```bash
    docker-compose -f docker-compose.yaml --build
```
2. 


## resources
1. [Comparing API Gateway Performances: NGINX vs. ZUUL vs. Spring Cloud Gateway vs. Linkerd](https://engineering.opsgenie.com/comparing-api-gateway-performances-nginx-vs-zuul-vs-spring-cloud-gateway-vs-linkerd-b2cc59c65369)
2. [Quick Guide to Microservices with Spring Boot 2.0, Eureka and Spring Cloud](https://piotrminkowski.wordpress.com/2018/04/26/quick-guide-to-microservices-with-spring-boot-2-0-eureka-and-spring-cloud/)
3. [MicroServices using Spring Boot & Spring Cloud](https://sivalabs.in/2018/03/microservices-using-springboot-spring-cloud-part-1-overview/)
4. [Hystrix](https://github.com/Netflix/Hystrix/wiki)
5. [Vault config](https://sivalabs.in/2018/03/microservices-part-2-configuration-management-spring-cloud-config-vault/)

## config details

1. docker ram size according to the [equation](https://github.com/dsyer/spring-boot-memory-blog/blob/master/cf.md)

2. spring-cloud docker-compose structure refer from [resource](https://github.com/sqshq/PiggyMetrics)

## TODO task

1. write a rule to load-balance the user's deduce inventory request to separate servers.
    > Hint: try using client load-balancing such as ribbon to resolve this : https://howtodoinjava.com/spring-cloud/spring-boot-ribbon-eureka/
    
    > Run ```mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8099```                                                                                                                                                                              
2.                                                                                            
                                                                                          