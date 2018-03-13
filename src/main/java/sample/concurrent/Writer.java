package sample.concurrent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cookie on 16/3/1.
 */
public class Writer extends Thread{

    private final int index;
    private Map<String, String> map;
    private boolean run = true;

    public Writer(int index, Map<String, String> map) {
        this.index = index;
        this.map = map;
    }

    public void close() {
        run = false;
    }

    public void run() {
        while (run) {
            try {
                String keyAndValue = UUID.randomUUID().toString();
                map.put(keyAndValue, keyAndValue);
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
