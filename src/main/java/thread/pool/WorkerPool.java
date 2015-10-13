package thread.pool;

import java.util.concurrent.*;

/**
 * Created by Cookie on 15/10/13.
 */
public class WorkerPool {

    public static void main(String args[]) throws InterruptedException {
        //RejectedExecutionHandler implementation
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
        //Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //creating the ThreadPoolExecutor
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory, rejectionHandler);

        //ThreadPoolExecutor executorPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);

        //start the monitoring thread
        MyMonitorThread monitor = new MyMonitorThread(executorPool, 3);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();
        //submit work to the thread pool
        for (int i = 1; i <= 20; i++) {
            executorPool.execute(new WorkerThread("cmd" + i));
        }
        Thread.sleep(30000);
        //shut down the pool
        executorPool.shutdown();
        //shut down the monitor thread
        Thread.sleep(5000);
        monitor.shutdown();
    }
}
