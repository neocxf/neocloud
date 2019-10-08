package top.neospot.cloud.common.util;

public class MeasuredRate {
    private static final Logger logger = LoggerFactory.getLogger(MeasuredRate.class);
    private final AtomicLong lastBucket = new AtomicLong(0);
    private final AtomicLong currentBucket = new AtomicLong(0);

    private final long sampleInterval;
    private final Timer timer;

    private volatile boolean isActive;

    /**
     * @param sampleInterval in milliseconds
     */
    public MeasuredRate(long sampleInterval) {
        this.sampleInterval = sampleInterval;
        this.timer = new Timer("Eureka-MeasureRateTimer", true);
        this.isActive = false;
    }

    public synchronized void start() {
        if (!isActive) {
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    try {
                        // Zero out the current bucket.
                        lastBucket.set(currentBucket.getAndSet(0));
                    } catch (Throwable e) {
                        logger.error("Cannot reset the Measured Rate", e);
                    }
                }
            }, sampleInterval, sampleInterval);

            isActive = true;
        }
    }

    public synchronized void stop() {
        if (isActive) {
            timer.cancel();
            isActive = false;
        }
    }

    /**
     * Returns the count in the last sample interval.
     */
    public long getCount() {
        return lastBucket.get();
    }

    /**
     * Increments the count in the current sample interval.
     */
    public void increment() {
        currentBucket.incrementAndGet();
    }
}
