package sample.trigger;

/**
 * Created by Cookie on 15/10/29.
 */
public class SampleMain {

    public static void main(String[] args) {

        Trigger trigger;

        System.out.println("========== Sample 1 output ==========");

        // Create a new Trigger
        trigger = SampleMasker.newTrigger();

        // Open the option 02 and 05
        trigger.on(SampleMasker.OPTION_02, SampleMasker.OPTION_05);

        // show all option
        for (Masker masker: SampleMasker.values()) {
            System.out.println(masker.toString() + " => " + trigger.isOn(masker) );
        }

        // show the trigger value
        System.out.println("Get Trigger Value => " + trigger.getValue());
        System.out.println();

        System.out.println("========== Sample 2 output ==========");

        // Create a new Trigger,and all option be on
        trigger = SampleMasker.newTrigger(Trigger.ALL_ON);

        // Close the option 02 and 05
        trigger.off(SampleMasker.OPTION_04);

        for (Masker masker: SampleMasker.values()) {
            System.out.println(masker.toString() + " => " + trigger.isOn(masker) );
        }

        // show the trigger value
        System.out.println("Get Trigger Value => " + trigger.getValue());
        System.out.println();

        int value = trigger.getValue();

        System.out.println("========== Sample 3 output ==========");

        // Using a trigger value to create a new Trigger
        trigger = SampleMasker.newTrigger(value);

        // show all option
        for (Masker masker: SampleMasker.values()) {
            System.out.println(masker.toString() + " => " + trigger.isOn(masker) );
        }

        // show the trigger value
        System.out.println("turn to Int Value => " + trigger.getValue());
        System.out.println();

    }
}
