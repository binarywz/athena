package binary.wz.zk.manager;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.Watcher;

/**
 * @author binarywz
 * @date 2022/5/3 17:52
 * @description:
 */
@Slf4j
public class SessionManager {
    public static ZkClient connect(String address) {
        ZkClient zkClient = new ZkClient(address, 10_000,
                10_000, new SerializableSerializer());
        zkClient.subscribeStateChanges(new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {
                log.info( "handleStateChanged, stat: {}", keeperState);
            }

            @Override
            public void handleNewSession() throws Exception {
                log.info("handleNewSession");
            }
        });
        log.info("connect to zk success...");
        return zkClient;
    }
}
