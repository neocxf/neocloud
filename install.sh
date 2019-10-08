mvn clean install -DskipTests

cp eureka-registry/target/eureka-registry.jar workdir/
cp config/target/config.jar workdir/
cp inventory-service/target/inventory-service.jar workdir/
cp messaging/target/messaging.jar workdir/
cp order-service/target/order-service.jar workdir/
cp reward-service/target/reward-service.jar workdir/
cp shipping-service/target/shipping-service.jar workdir/