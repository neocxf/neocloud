package top.neospot.cloud.cart.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.neospot.cloud.api.model.GreeterGrpc;
import top.neospot.cloud.api.model.Gretter;
import top.neospot.cloud.cart.config.RemoteConfig;
import top.neospot.cloud.cart.remote.InventoryClient;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@RestController("/carts")
public class CartController {
    final InventoryClient inventoryClient;

    final EurekaClient eurekaClient;

    @Autowired
    RemoteConfig remoteConfig;

    @Autowired
    public CartController(InventoryClient inventoryClient, EurekaClient eurekaClient) {
        this.inventoryClient = inventoryClient;
        this.eurekaClient = eurekaClient;
    }

    @GetMapping
    public List<Map<String, String>> getInventories() {
        System.out.println("request coming");

        List<Map<String, String>> carts = Lists.newArrayList();
        List<Map<String, String>> inventories = inventoryClient.inventoriesList();

        for (int i = 0; i < 5; i++) {
            Map<String, String> map = Maps.newHashMap();
            map.put("id", UUID.randomUUID().toString());
            map.put("cart", "cart" + i);
            map.put("inventoryName", inventories.get(i).get("productName"));

            carts.add(map);
        }

        return carts;
    }

    @GetMapping("/greet")
    @NotNull
    public String greet(@RequestParam("name") String name) throws ExecutionException, InterruptedException {
        final InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("neo-util", false);

        log.info(String.format("ip=%s, port=%d", instanceInfo.getIPAddr(), instanceInfo.getPort()));

        final ManagedChannel channel = ManagedChannelBuilder.forAddress(instanceInfo.getIPAddr(), 6565)
                .usePlaintext()
                .build();

        final GreeterGrpc.GreeterFutureStub stub = GreeterGrpc.newFutureStub(channel);
        Future<Gretter.HelloReply> reply = stub.sayHello(Gretter.HelloRequest.newBuilder().setName(name).build());

        System.out.println(reply.get().getMessage());

        return reply.get().getMessage();
    }

    @GetMapping("/config")
    public String config() {
        return this.remoteConfig.getA();
    }

}