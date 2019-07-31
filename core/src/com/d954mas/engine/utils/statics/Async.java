package com.d954mas.engine.utils.statics;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;


public class Async {
    public static boolean await(CyclicBarrier cb) {
        try {
            cb.await();
        } catch (Exception e) {
        }
        return true;
    }

    public static boolean acquire(Semaphore sm) {
        try {
            sm.acquire();
        } catch (Exception e) {
        }
        return true;
    }

    public static boolean sleep(long count) {
        try {
            Thread.sleep(count);
        } catch (Exception e) {
        }
        return true;
    }
}
