package top.neospot.cloud.inventory.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.neospot.cloud.common.exceptions.CloudBizException;
import top.neospot.cloud.inventory.entity.InventoryItem;
import top.neospot.cloud.inventory.entity.ProductInventoryHistory;
import top.neospot.cloud.inventory.mapper.InventoryItemMapper;
import top.neospot.cloud.inventory.mapper.ProductInventoryHistoryMapper;
import top.neospot.cloud.inventory.model.ProductInventoryDemand;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class InventoryItemService extends ServiceImpl<InventoryItemMapper, InventoryItem> implements InitializingBean, DisposableBean {
    private LinkedBlockingDeque<ProductInventoryDemand> productInventories;
    private volatile boolean isShutdown;

    @Autowired
    private ProductInventoryHistoryMapper productInventoryHistoryMapper;

    @Transactional
    public void minusInventory(ProductInventoryDemand productInventoryDemand) {
        String errorInfo;

        InventoryItem p1 = getOne(Wrappers.<InventoryItem>lambdaQuery().eq(InventoryItem::getProductCode, productInventoryDemand.getProductCode()).ge(InventoryItem::getQuantity, productInventoryDemand.getProductCnt()));

        if (p1 == null) {
            errorInfo = String.format("insufficient inventory for product: [%s]", productInventoryDemand.getProductCode());
            log.error(errorInfo);
            throw new CloudBizException(errorInfo);
        }

        log.info("before update the cnt: {}, updating cnt: {}, go to be cnt: {}, current inventory is: {} ", p1.getQuantity(), productInventoryDemand.getProductCnt(), p1.getQuantity() - productInventoryDemand.getProductCnt(), p1);

        p1.setQuantity(p1.getQuantity() - productInventoryDemand.getProductCnt());

        boolean succeed = update(p1, Wrappers.<InventoryItem>lambdaQuery().eq(InventoryItem::getProductCode, p1.getProductCode()).gt(InventoryItem::getQuantity, 0));

        if (!succeed) {
            errorInfo = String.format("failed to minus inventory for product: [%s], due to contention, try again. current inventory is: %s ", productInventoryDemand.getProductCode(), p1);
            log.error(errorInfo);
            productInventories.offer(productInventoryDemand);
            return;
        }

        ProductInventoryHistory productInventoryHistory = new ProductInventoryHistory();
        productInventoryHistory.setQuantity(productInventoryDemand.getProductCnt());
        productInventoryHistory.setProductCode(productInventoryDemand.getProductCode());
        productInventoryHistory.setProductId(p1.getId());
        productInventoryHistory.setUserId(productInventoryDemand.getUserId());
        productInventoryHistoryMapper.insert(productInventoryHistory);
    }

    /**
     * 乐观锁方案： 吞吐量较大
     */

    @Override
    public void afterPropertiesSet() throws Exception {
        productInventories = new LinkedBlockingDeque<>();
        Thread daemon = new Thread(() -> {
            try {
                ProductInventoryDemand productInventoryDemand;

                for (; ; ) {
                    if (isShutdown) return;

                    if ((productInventoryDemand = productInventories.poll(1, TimeUnit.SECONDS)) != null) {
                        minusInventory(productInventoryDemand);
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
