package thread.pool.journaldev;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Cookie on 15/10/13.
 */
public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler{

    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.toString() + " is rejected");
    }
}
