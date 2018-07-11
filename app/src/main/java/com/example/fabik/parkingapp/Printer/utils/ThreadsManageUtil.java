package com.example.fabik.parkingapp.Printer.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vincent on 2017/8/22.
 * description: app thread manage
 */

public class ThreadsManageUtil {

    private static int FIXED_THREAD_NUMBER = 6;
    private static ExecutorService cachedPool;//cache pool
    private static ExecutorService signalCachedPool;//cache pool with only one thread
    private static ExecutorService fixThreadPool;//cache pool with explicit munber


    //cache pool
    public static void runOnCachedPool(Runnable thread){
        if (cachedPool ==null) {
            synchronized (ThreadsManageUtil.class) {//synchronize
                cachedPool = Executors.newCachedThreadPool();
            }
        }
        cachedPool.submit(thread);
    }
    public static void shutDownCachedPool(){
        cachedPool.shutdownNow();
    }

    public static void runOnSignalPool(Runnable thread){
        if (signalCachedPool == null) {
            synchronized (ThreadsManageUtil.class){
                signalCachedPool = Executors.newSingleThreadExecutor();
            }
        }
        signalCachedPool.submit(thread);
    }
    public static void shutDownSignalPool(){
        signalCachedPool.shutdownNow();
    }

    public static void runOnFixThreadPool(Runnable thread)
    {
        if (fixThreadPool ==null){
            synchronized (ThreadsManageUtil.class){
                fixThreadPool = Executors.newFixedThreadPool(FIXED_THREAD_NUMBER);
            }
        }
        fixThreadPool.submit(thread);
    }
    public static void shutDownFixPool(){
        fixThreadPool.shutdownNow();
    }

    public static void releaseAllThread(){
        if (fixThreadPool != null)
            fixThreadPool.shutdown();
        if (signalCachedPool != null)
            signalCachedPool.shutdown();
        if (cachedPool != null)
            cachedPool.shutdown();
    }
}
