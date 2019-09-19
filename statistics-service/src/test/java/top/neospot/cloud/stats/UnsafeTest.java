package top.neospot.cloud.stats;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/10/24.
 */
public class UnsafeTest {
    private static Unsafe unsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }


//    @Test
    public void test1() throws InstantiationException {
        InitializationOrdering o = (InitializationOrdering) unsafe.allocateInstance(InitializationOrdering.class);

        assertEquals(o.getA(), 0);
    }

//    @Test
    public void test2() throws InstantiationException, NoSuchFieldException {
        InitializationOrdering ordering = new InitializationOrdering();

        Field f = ordering.getClass().getDeclaredField("SECRET_VALUE");
        unsafe.putInt(ordering, unsafe.objectFieldOffset(f), 1);

        assertTrue(ordering.secretIsDisclosed());
    }

//    @Test
    public void testCasCounter() throws Exception {
        int NUM_OF_THREADS = 1_000;
        int NUM_OF_INCREMENTS = 10_000;
        ExecutorService service = Executors.newFixedThreadPool(NUM_OF_THREADS);
        CASCounter casCounter = new CASCounter();

        IntStream.rangeClosed(0, NUM_OF_THREADS - 1)
                .forEach(i -> service.submit(() -> IntStream
                        .rangeClosed(0, NUM_OF_INCREMENTS - 1)
                        .forEach(j -> casCounter.increment())));

        assertEquals(NUM_OF_INCREMENTS * NUM_OF_THREADS, casCounter.getCounter());
    }
}


class InitializationOrdering {
    private long a;

    public InitializationOrdering() {
        this.a = 1;
    }

    public long getA() {
        return this.a;
    }

    private int SECRET_VALUE = 0;

    public boolean secretIsDisclosed() {
        return SECRET_VALUE == 1;
    }
}

class CASCounter {
    private Unsafe unsafe;
    private volatile long counter = 0;
    private long offset;

    private Unsafe getUnsafe() throws IllegalAccessException, NoSuchFieldException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }

    public CASCounter() throws Exception {
        unsafe = getUnsafe();
        offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
    }

    public void increment() {
        long before = counter;
        while (!unsafe.compareAndSwapLong(this, offset, before, before + 1)) {
            before = counter;
        }
    }

    public long getCounter() {
        return counter;
    }
}