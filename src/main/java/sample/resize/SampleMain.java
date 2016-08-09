package sample.resize;

import java.util.Random;

/**
 * Created by Cookie on 16/5/19.
 */
public class SampleMain {

    private static final int SIZE = 64;
    private static final int TIMES = 10;
    private static final int COUNT = 5000000;

    private static final StringBuilder STRING_BUILDER = new StringBuilder(SIZE);

    private static final char[] CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890$_".toCharArray();
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        int sum = 0;
        for (int i = 1; i <= TIMES ; i++) {
            System.out.println("===== started " + i + " =====");
            sum += test();
        }
        System.out.println(String.format("StringBuilder's char size: %d, Sum: %d, Avg: %d" , STRING_BUILDER.capacity(), sum, sum / TIMES));
    }

    public static long test() {
        long started = System.nanoTime();
        for (int i = 1; i <= COUNT ; i ++) {
            StringBuilder builder = fill(STRING_BUILDER);
            builder.delete(0, builder.length());
        }
        long spend_1 = (System.nanoTime() - started) /1000;

        started = System.nanoTime();
        for (int i = 1; i <= COUNT ; i ++) {
            fill(STRING_BUILDER).setLength(0);
        }
        long spend_2 = (System.nanoTime() - started) /1000;
        long result = spend_1 - spend_2;
        System.out.println(String.format("Delete Spend Time: %d us, setLength Spend TIme: %d us, Delete - setLength: %d us", spend_1, spend_2, result));
        return result;
    }

    public static StringBuilder fill(StringBuilder builder) {
        if (builder.length() >= SIZE) {
            return builder;
        }
        for (int i = 0; i < builder.capacity(); i++) {
            char c = CHARS[RANDOM.nextInt(CHARS.length)];
            builder.append(c);
        }
        return builder;
    }
}
