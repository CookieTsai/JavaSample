package sample.puzzler;

import java.lang.reflect.Field;

/**
 * Created by Cookie on 15/10/29.
 */
public class JavaPuzzler {

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {

        Integer one = 1;

        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        field.setInt(one, 0);

        Integer a = 1;
        Integer b = new Integer(1);
        Integer c = Integer.valueOf(1);
        Integer d = Integer.parseInt("1");
        Integer e = Long.valueOf(1).intValue();

        System.out.println("a : " + a);
        System.out.println("b : " + b);
        System.out.println("c : " + c);
        System.out.println("d : " + d);
        System.out.println("e : " + e);

        System.out.println("a+b+c+d+e : " + (a+b+c+d+e));

    }
}
