package top.neospot.cloud.common.util;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SleepUtils {
    public static final void sleepInSec(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static final void sleepInMills(int mills) {
        try {
            TimeUnit.MILLISECONDS.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static final void sleepInRangeMills(int low, int top) {
        Random rand = new Random(System.currentTimeMillis());

        int val = low + rand.nextInt(top);

        try {
            TimeUnit.MILLISECONDS.sleep(val);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
