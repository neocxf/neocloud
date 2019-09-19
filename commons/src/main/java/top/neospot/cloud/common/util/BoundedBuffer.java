package top.neospot.cloud.common.util;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * By neo.chen{neocxf@gmail.com} on 2017/11/3.
 */
public class BoundedBuffer<T> {
    private Lock lock = new ReentrantLock();

    private Condition notFull = lock.newCondition();

    private Condition notEmpty = lock.newCondition();

    private T[] backArray;

    private int front;

    private int rear;

    private int count;

    private int cap;


    public BoundedBuffer(Class<T[]> type, int cap) {
        backArray = CollectionUtils.newArray(type, cap);
        this.cap = cap;
    }

    public void put(T num) throws InterruptedException {
        lock.lock();
        try {
            while (count == this.cap) {
                notFull.await();
            }

            backArray[rear] = num;

            rear = (rear + 1) % cap;

            count++;

            notEmpty.signal();
        } catch (InterruptedException e) {
            notFull.signal();
            throw e;
        } finally {
            lock.unlock();
        }
    }

    public T fetch() {
        lock.lock();
        T result = null;
        try {
            while (count == 0) {
                notEmpty.await();
            }

            result = backArray[front];
            front = (front + 1) % cap;
            count--;
            notFull.signal();

            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return null;
    }

}
