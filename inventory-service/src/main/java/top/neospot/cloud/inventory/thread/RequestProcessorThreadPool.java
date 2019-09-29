package top.neospot.cloud.inventory.thread;

import lombok.extern.slf4j.Slf4j;
import top.neospot.cloud.inventory.request.Request;
import top.neospot.cloud.inventory.request.RequestQueue;
import top.neospot.cloud.inventory.request.StoppableArrayBlockingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
@Slf4j
public class RequestProcessorThreadPool {

    private final ExecutorService executorService;

    private final RequestQueue requestQueue;

    private final List<RequestProcessor> activeProcessors;

    private final CountDownLatch latch;

    private RequestProcessorThreadPool(int count) {
        executorService = Executors.newFixedThreadPool(count);
        requestQueue = RequestQueue.getInstance();
        activeProcessors = new ArrayList<>();
        latch = new CountDownLatch(10);

        for (int i = 0; i < count; i++) {
            StoppableArrayBlockingQueue<Request> queue = new StoppableArrayBlockingQueue<>(5000);
            requestQueue.addQueue(queue);
            RequestProcessor processorThread = new RequestProcessor(queue, latch);
            activeProcessors.add(processorThread);
            executorService.submit(processorThread);
        }
    }

    public static class Singleton {
        private static RequestProcessorThreadPool requestProcessorThreadPool;
        static {
            requestProcessorThreadPool = new RequestProcessorThreadPool(100);
        }

        public static RequestProcessorThreadPool getRequestProcessorThreadPool() {
            return requestProcessorThreadPool;
        }

    }

    public static RequestProcessorThreadPool getInstance() {
        return Singleton.getRequestProcessorThreadPool();
    }

    public void init() {
        log.debug("init requestProcessorThreadPool ...");

        RequestProcessorThreadPool.getInstance();
    }

    public void shutdownNow() {
        log.debug("shutdown requestProcessorThreadPool ...");
        RequestProcessorThreadPool processorThreadPool = getInstance();
        processorThreadPool.executorService.shutdown();
        processorThreadPool.requestQueue.clear();
    }

    public void shutdown() throws InterruptedException {
        log.debug("shutdown requestProcessorThreadPool ...");
        activeProcessors.forEach(RequestProcessor::stopServ);
        latch.await();
        executorService.shutdown();

        log.debug("clean all the queue work done");
    }

    public void dispatch(Request request) {
        requestQueue.pushRequest(request);
    }

}
