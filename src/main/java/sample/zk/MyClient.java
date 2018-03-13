package sample.zk;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cookie on 16/3/4.
 */
public class MyClient implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    private static final int SESSION_TIMEOUT = 3000;

    private ZooKeeper zk;
    private String hostPort;
    private boolean connected = false;
    private boolean expired = false;

    MyClient(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort, SESSION_TIMEOUT, new Watcher() {
            public void process(WatchedEvent e) {
                System.out.println("1" + e);
                if(e.getType() == Event.EventType.None){
                    switch (e.getState()) {
                        case SyncConnected:
                            setStatus(true, false);
                            break;
                        case Disconnected:
                            setStatus(false, false);
                            break;
                        case Expired:
                            setStatus(false, true);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    private synchronized void setStatus(boolean connected, boolean expired) {
        this.connected = connected;
        this.expired = expired;
    }

    boolean isConnected(){
        return connected;
    }

    boolean isExpired(){
        return expired;
    }

    public void close() throws IOException {
        LOG.info("Closing");
        try{
            zk.close();
        } catch (InterruptedException e) {
            LOG.warn("ZooKeeper interrupted while closing");
        }
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        final MyClient client = new MyClient("master:2181");
        client.startZK();

        Watcher watcher = new Watcher() {
            public void process(WatchedEvent e) {
                try {
                    System.out.println("2" + e);

                    if (e.getType() == Event.EventType.NodeCreated || e.getType() == Event.EventType.NodeDeleted) {
                        client.getZk().exists(e.getPath(), this);
                    } else if (e.getType() == Event.EventType.NodeDataChanged) {
                        client.getZk().getData(e.getPath(), this, null);
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };

        while (!client.isConnected()) {
            TimeUnit.SECONDS.sleep(2);
        }


        String path2 = "/qmi/test";
        client.getZk().exists(path2, watcher);
        byte[] data = client.getZk().getData("/qmi/sync/cache1", watcher, null);

        System.out.println(new String(data));


        client.getZk().create("/tmp", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        TimeUnit.HOURS.sleep(1);
        // client.close();
    }
}
