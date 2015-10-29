package sample.trigger;

/**
 * Created by Cookie on 15/10/29.
 */
public interface Masker {
    boolean isOn(int value);
    int getIndex();
    int getMask();
}
