package com.example.utils;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



//单例模式的线程池管理类
public class ThreadManager {


    private ThreadManager() {
    }

    private ThreadPoolProxy netPool;

    private static ThreadManager instance = new ThreadManager();

    public static ThreadManager getInstance() {
        return instance;
    }

    public synchronized ThreadPoolProxy createNetPool() {    //代理类的创建方法

        if (netPool == null) {
            netPool = new ThreadPoolProxy(5, 5, 5000L);
        }
        return netPool;
    }


    //我的线程池代理类
    public class ThreadPoolProxy {

        private ThreadPoolExecutor pool;

        private int corePoolSize;

        private int maxmumPoolSize;

        private long time;

        public ThreadPoolProxy(int corePoolSize, int maxmumPoolSize, long time) {
            this.corePoolSize = corePoolSize;
            this.maxmumPoolSize = maxmumPoolSize;
            this.time = time;
        }

        //执行的方法
        public void execute(Runnable runnable) {
            if (pool == null) {
                pool = new ThreadPoolExecutor(corePoolSize, maxmumPoolSize, time,
                        TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10));
            }
            pool.execute(runnable);


        }

        //取消的方法
        public void cancel(Runnable runnable) {
            if (pool != null && !pool.isShutdown() && !pool.isTerminated()) {
                pool.remove(runnable);
            }
        }


    }


}
