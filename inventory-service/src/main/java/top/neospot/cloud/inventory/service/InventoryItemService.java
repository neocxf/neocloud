package top.neospot.cloud.inventory.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.neospot.cloud.common.exceptions.CloudBizException;
import top.neospot.cloud.inventory.entity.InventoryItem;
import top.neospot.cloud.inventory.mapper.InventoryItemMapper;
import top.neospot.cloud.inventory.model.ProductInventory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class InventoryItemService extends ServiceImpl<InventoryItemMapper, InventoryItem> implements InitializingBean, DisposableBean {
    private LinkedBlockingDeque<ProductInventory> productInventories;
    private volatile boolean isShutdown;

    @Transactional
    public void minusInventory(ProductInventory productInventory) {
        String errorInfo;

        InventoryItem p1 = getOne(Wrappers.<InventoryItem>lambdaQuery().eq(InventoryItem::getProductCode, productInventory.getProductCode()).ge(InventoryItem::getQuantity, productInventory.getProductCnt()));

        if (p1 == null) {
            errorInfo = String.format("insufficient inventory for product: [%s]", productInventory.getProductCode());
            log.error(errorInfo);
            throw new CloudBizException(errorInfo);
        }

        log.info("before update the cnt: {}, updating cnt: {}, go to be cnt: {}, current inventory is: {} ", p1.getQuantity(), productInventory.getProductCnt(), p1.getQuantity()-productInventory.getProductCnt(), p1);

        p1.setQuantity(p1.getQuantity()-productInventory.getProductCnt());

        boolean succeed = update(p1, Wrappers.<InventoryItem>lambdaQuery().eq(InventoryItem::getProductCode, p1.getProductCode()).gt(InventoryItem::getQuantity, 0));

        if (! succeed) {
            errorInfo = String.format("failed to minus inventory for product: [%s], due to contention, try again. current inventory is: %s ", productInventory.getProductCode(), p1);
            log.error(errorInfo);
            productInventories.offer(productInventory);
        }
    }

    /**
     *  乐观锁方案： 吞吐量较大
     */

    @Override
    public void afterPropertiesSet() throws Exception {
        productInventories = new LinkedBlockingDeque<>();
        Thread daemon = new Thread(() -> {
            try {
                ProductInventory productInventory;

                for (;;) {
                    if (isShutdown) return;

                    if ((productInventory = productInventories.poll(1, TimeUnit.SECONDS)) != null) {
                        minusInventory(productInventory);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        daemon.setDaemon(true);
        daemon.start();
    }

    @Override
    public void destroy() throws Exception {
        this.isShutdown = true;
    }
}
