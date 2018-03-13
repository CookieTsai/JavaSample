package sample.referance;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

// 会报空指针:WeakReference中的referent被设置成null,之后加入到ReferenceQueue
public class TestWeakReference
{
    private static volatile boolean isRun = true;

    private static volatile ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();

    public static void main(String[] args) throws Exception {
        String abc1 = new String("abc1");
        String abc2 = new String("abc2");
        System.out.println(abc1.getClass() + "@" + abc1.hashCode());
        System.out.println(abc2.getClass() + "@" + abc2.hashCode());

        new Thread() {
            public void run() {
                while (isRun) {
                    Object o = referenceQueue.poll();
                    if (o != null) {
                        try {
                            Field referent = Reference.class.getDeclaredField("referent");
                            referent.setAccessible(true);
                            Object result = referent.get(o);
                            Class aClass = result.getClass();
                            int hashCode = result.hashCode();
                            System.out.println("gc will collect:" + aClass + "@" + hashCode);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();

        // 对象是弱可达的
        WeakReference<String> weak = new WeakReference<String>(abc1, referenceQueue);
        SoftReference<String> soft = new SoftReference<String>(abc2, referenceQueue);
        System.out.println("weak=" + weak);
        System.out.println("soft=" + soft);

        // 清除强引用,触发GC
        abc1 = null;
        abc2 = null;
        System.gc();

        Thread.sleep(1000);
        isRun = false;
    }
}

