package sample.threadpool.putter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Cookie on 15/10/13.
 */
public class HPut {

    static java.util.Hashtable < String, HPut > uaHash = new java.util.Hashtable < String, HPut > ();

    public static LinkedBlockingQueue < HPut > uaMsgQueue = new LinkedBlockingQueue< HPut >();
    LinkedBlockingQueue < String > putQueue = new LinkedBlockingQueue < String > ();

    static boolean run = true;
    public static long INTERVAL = 10L;

    String hTable = "";
    String cf = "";

    public HPut(String hTable, String cf) {
        this.cf = cf;
        this.hTable = hTable;
    }

    public static void proc(String hTable, String cf, String put) {
        if (uaHash.size() >= 5000) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        final HPut ua = HPutFactory(hTable, cf);
        ua.putQueue.add(put);
        uaMsgQueue.add(ua);
    }

    public static HPut HPutFactory(String table, String cf) {
        String baseKey = cf + "_" + table;

        HPut ua = uaHash.get(baseKey);
        if (ua == null) {
            ua = new HPut(table, cf);
            uaHash.put(baseKey, ua);
        }
        return ua;
    }

    public static void init(int size) {
        run = true;
        for (int i = 0; i < size; i++) {
            Thread th = new Thread() {
                public void run() {
                    while (run)
                        message();
                }
            };
            th.start();
        }
    }

    public static void message() {
        try {
            HPut ua = HPut.uaMsgQueue.poll();
            if (ua != null) {
                ua.threadProcess();
            } else {
//                System.out.println(uaMsgQueue.size());
//                Thread.sleep(INTERVAL);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void threadProcess() {
        try {
            List< String > batch = new ArrayList< String >();

            while (true) {
                String ua = putQueue.poll();
                if (ua == null) break;
                batch.add(ua);
                if (batch.size() >= 50) break;
            }
            if (batch.size() > 0) {
                System.out.println(batch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        HPut.init(40);
        for (int i = 0; i < 100000; i++) {
            HPut.proc("table", "cf", String.valueOf(1));
        }

        System.out.println(uaHash.size());
        System.out.println(uaHash.get("cf_table").putQueue.size());
        System.out.println(uaMsgQueue.size());

        Thread.sleep(2000L);

        System.out.println(uaHash.size());
        System.out.println(uaHash.get("cf_table").putQueue.size());
        System.out.println(uaMsgQueue.size());

        System.out.println("finish");
    }
}
