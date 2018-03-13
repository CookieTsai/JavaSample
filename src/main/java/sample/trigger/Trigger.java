package sample.trigger;

/**
 * Created by Cookie on 15/10/29.
 */
public class Trigger {
    public static final int ALL_ON  = -1;
    public static final int ALL_OFF = 0;

    private int value;

    public Trigger(Masker... maskers) {
        for (Masker masker: maskers) {
            on(masker);
        }
    }

    public void on(Masker... maskers){
        for (Masker masker: maskers) {
            value = (value | masker.getMask());
        }
    }

    public void off(Masker... maskers){
        for (Masker masker: maskers) {
            if (!isOn(masker)) {
                continue;
            }
            value -= masker.getMask();
        }
    }

    public boolean isOn(Masker masker){
        return (value & masker.getMask()) > 0;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}
