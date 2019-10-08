package top.neospot.cloud.inventory.service;

import top.neospot.cloud.inventory.request.Request;
import top.neospot.cloud.inventory.request.RequestQueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {
    @Override
    public void process(Request request) {
        ArrayBlockingQueue<Request> queue = getRouteQueue(request.getProductCode());

        try {
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private ArrayBlockingQueue<Request> getRouteQueue(String productCode) {
        RequestQueue requestQueue = RequestQueue.getInstance();

        String key = String.valueOf(productCode);

        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

        int index = (requestQueue.queueSize() -1) & hash;

        return requestQueue.get(index);
    }
}
