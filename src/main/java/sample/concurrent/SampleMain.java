package sample.concurrent;

import java.util.*;
import java.util.concurrent.*;

import static java.lang.System.out;

/**
 * Created by Cookie on 16/3/1.
 */
public class SampleMain {

    private static final int WRITERS_NUM = 10;
    private static final int READ_TIMES = 500;

    private static void execute(Map<String, String> map) {
        List<Writer> writers = new ArrayList<Writer>();
        try {
            long started = System.currentTimeMillis();
            for (int i = 0; i < WRITERS_NUM; i++) writers.add(new Writer(i, map));
            for (Writer writer: writers) writer.start();
            TimeUnit.SECONDS.sleep(1);
            out.println(String.format("Size:%d ,Spend Time:%d", map.size(),
                                                                (System.currentTimeMillis() - started)));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for (Writer writer: writers) {
                writer.close();
            }
        }
    }

    private static void run(Map<String, String> map) {
        out.println(map.getClass().getSimpleName() + " Start");
        try {
            execute(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println(map.getClass().getSimpleName() + " Stop");
    }

    public static void main(String[] args) {
        run(new ConcurrentHashMap<String, String>());
        run(new Hashtable<String, String>());
        run(Collections.synchronizedMap(new HashMap()));
    }
}