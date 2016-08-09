package sample.resize;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * Created by Cookie on 16/5/19.
 */
public class SampleMain2 {

    private static final int SIZE = 4;
    private static final int LENGTH = 4;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        Map<String, Integer> map = new HashMap<String, Integer>(SIZE, 1f);

        Field field = HashMap.class.getDeclaredField("table");
        field.setAccessible(true);

        Method hashMethod = HashMap.class.getDeclaredMethod("hash", Object.class);
        hashMethod.setAccessible(true);

        Method indexForMethod = HashMap.class.getDeclaredMethod("indexFor", int.class, int.class);
        indexForMethod.setAccessible(true);

        for (int i = 0; i < LENGTH * 4 ; i++) {
            String uuid = UUID.randomUUID().toString().substring(33);
            map.put(uuid, i);

            Entry<String, Integer>[] entries = (Entry<String, Integer>[]) field.get(map);
            Integer hash = (Integer) hashMethod.invoke(map, uuid);
            Integer index = (Integer) indexForMethod.invoke(map, hash, entries.length);

            out.println(String.format("No: %d, Index: %d, Map Size: %d , Entry Size: %d, Entries: %s", i +1, index, map.size(), entries.length, Arrays.toString(entries)));
        }

        out.println();
        out.println("====== 這是分隔線 ======");
        out.println();

        for (Entry<String, Integer> entry: map.entrySet()) {
            out.println(String.format("Key: %s, Value: %d", entry.getKey(), entry.getValue()));
        }
    }
}
