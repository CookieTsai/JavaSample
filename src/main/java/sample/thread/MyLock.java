package sample.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Cookie on 16/3/17.
 */
public class MyLock extends ReentrantLock {

    private final Condition condition = newCondition();

    public Condition getCondition() {
        return condition;
    }
}
