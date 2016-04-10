package sample.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by Cookie on 16/3/17.
 */
public class SampleMain {

    public final MyLock lock = new MyLock();

    public static void main(String[] args) throws InterruptedException {

        final SampleMain sampleMain = new SampleMain();

        new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("signalAll");
                sampleMain.lock.getCondition().signalAll();
            }
        }).start();

        System.out.println("await");
        sampleMain.lock.getCondition().await();
        System.out.println("wake up");

    }
}
