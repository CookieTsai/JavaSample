package sample.trigger;

/**
 * Created by Cookie on 15/10/29.
 */
public enum  SampleMasker implements Masker {

    OPTION_01(1),
    OPTION_02(2),
    OPTION_03(3),
    OPTION_04(4),
    OPTION_05(5);

    private final int index;
    private final int mask;

    SampleMasker(int index) {
        this.index = index;
        this.mask = (1 << (index -1));
    }

    public int getIndex() {
        return index;
    }

    public int getMask() {
        return mask;
    }

    public boolean isOn(int value){
        return (value & getMask()) > 0;
    }

    public static Trigger newTrigger() {
        return new Trigger();
    }

    public static Trigger newTrigger(Integer value) {
        if (value == null) {
            return new Trigger(values());
        }
        Trigger result = new Trigger();
        for (Masker masker: values()) {
            if (masker.isOn(value)) {
                result.on(masker);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
