package top.neospot.cloud.inventory.service;

import top.neospot.cloud.inventory.request.Request;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
public interface RequestAsyncProcessService {
    void process(Request request);
}
