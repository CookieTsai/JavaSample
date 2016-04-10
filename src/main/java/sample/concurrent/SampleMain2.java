package sample.concurrent;

import java.util.*;
import java.util.concurrent.*;

import static java.lang.System.out;

/**
 * Created by Cookie on 16/3/1.
 */
public class SampleMain2 {
    public final static int POOL_SIZE = 5;

    public static Map<String, Integer> hashtableObj = null;
    public static Map<String, Integer> syncMapObj = null;
    public static Map<String, Integer> concurrentHashMapObj = null;

    public static void main(String[] args) throws Exception {

        // Test with Hashtable Object
        hashtableObj = new Hashtable<String, Integer>();
        performTest(hashtableObj);

        // Test with synchronizedMap Object
        syncMapObj = Collections.synchronizedMap(new HashMap<String, Integer>());
        performTest(syncMapObj);

        // Test with ConcurrentHashMap Object
        concurrentHashMapObj = new ConcurrentHashMap<String, Integer>();
        performTest(concurrentHashMapObj);

    }

    public static void performTest(final Map<String, Integer> m) throws Exception {

        out.println("Test started for: " + m.getClass());
        long avgTime = 0;
        for (int i = 0; i < 5; i++) {

            long startTime = System.nanoTime();
            ExecutorService exService = Executors.newFixedThreadPool(POOL_SIZE);

            for (int j = 0; j < POOL_SIZE; j++) {
                exService.execute(new Runnable() {
                    @SuppressWarnings("unused")
                    public void run() {
                        for (int i = 0; i < 500000; i++) {
                            Integer randomNum = (int) Math.ceil(Math.random() * 550000);
                            // Retrieve value. We are not using it anywhere
                            Integer value = m.get(String.valueOf(randomNum));
                            // Put value
                            m.put(String.valueOf(randomNum), randomNum);
                        }
                    }
                });
            }

            // Make sure executor stops
            exService.shutdown();
            // Blocks until all tasks have completed execution after a shutdown request
            exService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

            long entTime = System.nanoTime();
            long totalTime = (entTime - startTime) / 1000000L;
            avgTime += totalTime;
            out.println("500K entried added/retrieved in " + totalTime + " ms");
        }
        out.println("For "+ m.getClass()+ " the average time is "+ avgTime / 5+ " ms\n");
    }
}
