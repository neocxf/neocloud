package top.neospot.cloud.inventory.thread;

import lombok.extern.slf4j.Slf4j;
import top.neospot.cloud.inventory.request.Request;
import top.neospot.cloud.inventory.request.StoppableArrayBlockingQueue;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
@Slf4j
public class RequestProcessor implements Callable<Boolean> {
    private static AtomicLong idGen = new AtomicLong(0);

    private StoppableArrayBlockingQueue<Request> queue;
    private Long id;

    private ReentrantLock mainLock;
    private volatile boolean shouldStopNow;
    private volatile CountDownLatch latch;

    public RequestProcessor(StoppableArrayBlockingQueue<Request> queue, CountDownLatch latch) {
        this.queue = queue;
        this.id = idGen.incrementAndGet();
        this.latch = latch;
        this.mainLock = new ReentrantLock();
    }

    @Override
    @SuppressWarnings("all")
    public Boolean call() throws Exception {
        Request request;
        while (! shouldStopNow) {
            request = queue.poll(1, TimeUnit.SECONDS);
            if (request == null && queue.isRejectRecving())  {
                // use the rewrite method, if we recv the stop signal, we could get a null value
                log.info(String.format("%s --------------------------- stop serving, queue size: %d", dumpThreadInfo(), queue.size()));
                latch.countDown();
                return false;
            }

            if (queue.isRejectRecving()) {
                log.debug(String.format("recv stop signal, but the queue status is %s", queue));
            }

            if (request != null) {
                log.debug("executing a request ...");
                try {
                    mainLock.lock();
                    request.process();
                } finally {
                    mainLock.unlock();
                }
            }

        }

        latch.countDown();
        return true;

    }


    public void stopServ() {
        log.debug("RequestProcessor stop serving");
        this.queue.stopRecv();
    }

    public void stopServNow() {
        this.shouldStopNow = true;
    }

    private String dumpThreadInfo() {
        String processorThreadInfo = String.format("processor[%d] with queue size [%d]", id, queue.size());
        return processorThreadInfo;
    }

    private String dumpQueue() {
        return queue.toString();
    }
}
