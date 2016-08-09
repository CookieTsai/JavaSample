package sample.weakmap;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by Cookie on 16/5/20.
 */
public class SampleMain {
    public static void main(String[] args) {
        List<WeakHashMap<byte[][], byte[][]>> maps = new ArrayList<WeakHashMap<byte[][], byte[][]>>();

        for (int i = 0; i < 1000; i++) {
            WeakHashMap<byte[][], byte[][]> d = new WeakHashMap<byte[][], byte[][]>();
            d.put(new byte[1000][1000], new byte[1000][1000]);
            maps.add(d);
            System.gc();
            System.err.println(i);


//            for (int j = 0; j < i; j++) {
//                System.err.println(j+  " size" + maps.get(j).size());
//            }
        }
    }
}
