package top.neospot.cloud.inventory.request;

import top.neospot.cloud.inventory.thread.RequestProcessorThreadPool;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
public class InventoryRequestContext {
    private static ThreadLocal<RequestProcessorThreadPool> threadPoolThreadLocal = new ThreadLocal<>();
}
