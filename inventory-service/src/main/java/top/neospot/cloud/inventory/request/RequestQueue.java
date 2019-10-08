package top.neospot.cloud.inventory.request;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
public class RequestQueue {
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();

    public int queueSize() {
        return queues.size();
    }

    public ArrayBlockingQueue<Request> get(int index) {
        return queues.get(index);
    }

    public static class Singleton {
        private static RequestQueue requestQueue;
        static {
            requestQueue = new RequestQueue();
        }

        static RequestQueue getInstance() {
            return requestQueue;
        }

    }


    public static RequestQueue getInstance() {
        return Singleton.getInstance();
    }

    public void addQueue(ArrayBlockingQueue<Request> queue) {
        queues.add(queue);
    }

    public void pushRequest(Request request) {
        RequestQueue requestQueue = RequestQueue.getInstance();

        String key = String.valueOf(request.getProductCode());

        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

        int index = (requestQueue.queueSize() -1) & hash;

        Queue<Request> queue = requestQueue.get(index);

        //TODO: to summarize the request of the same request (ie, order_id)

//        Optional<Request> optional =  queue.stream().filter(q -> Objects.equals(q.getProductCode(), request.getProductCode())).findAny();
//
//        optional.ifPresent(queue::remove);

        boolean success= queue.add(request);

        if (! success) {
            System.out.println("insert queue reject");
        }
    }

    public void clear() {
        queues.forEach(Collection::clear);
    }

}
