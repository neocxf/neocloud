package top.neospot.cloud.inventory.request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
public class StoppableArrayBlockingQueue<T> extends ArrayBlockingQueue<T> {
    private static AtomicLong idGen = new AtomicLong(0);
    private volatile boolean isRejectRecving;
    private final long id;

    public StoppableArrayBlockingQueue(int capacity) {
        super(capacity);
        this.id = idGen.incrementAndGet();
    }

    @Override
    public void put(T o) throws InterruptedException {
        if (isRejectRecving) {
            return;
        }
        super.put(o);
    }

    @Override
    @SuppressWarnings("all")
    public T take() throws InterruptedException {
        if (isRejectRecving) {
            return super.poll();
        }

        return super.take();
    }

    public void stopRecv() {
        this.isRejectRecving = true;
    }

    public boolean isRejectRecving() {
        return  this.isRejectRecving;
    }

    @Override
    public String toString() {
        return "[ queue: " + this.id + ", size: " + size() + " ]";
    }
}
