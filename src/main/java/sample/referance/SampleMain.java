package sample.referance;

import java.lang.ref.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cookie on 16/4/18.
 */
public class SampleMain {

    public static void main(String[] args) throws InterruptedException {
        WeakHashMap weakHashMap = new WeakHashMap<String, String>();

        int i = 0;
        while (true) {
            i++;
            weakHashMap.put(i, i);
        }
    }
}
