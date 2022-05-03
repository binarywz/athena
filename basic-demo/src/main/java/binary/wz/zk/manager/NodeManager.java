package binary.wz.zk.manager;

import binary.wz.zk.config.ZkConfig;
import binary.wz.zk.listener.ChildChangedListener;
import binary.wz.zk.listener.DataChangedListener;
import binary.wz.zk.model.User;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * @author binarywz
 * @date 2022/5/3 17:56
 * @description:
 */
@Slf4j
public class NodeManager {

    private ZkClient zkClient;
    private String path;

    public NodeManager(String path) {
        this.path = path;
        this.zkClient =SessionManager.connect(ZkConfig.zkAddress);
        zkClient.subscribeChildChanges(path, new ChildChangedListener());
        zkClient.subscribeDataChanges(path, new DataChangedListener());
    }


    public void createNoe() {
        User user = new User();
        user.setId(1);
        user.setName("binarywz");

        if (zkClient.exists(path)) {
            log.warn("{} has exist...", path);
            return;
        }
        /**
         * /binaryNode: 节点路径
         * user: 节点相关的数据
         * CreateMode.PERSISTENT: 节点类型
         */
        String node = zkClient.create(this.path, user, CreateMode.PERSISTENT);
        log.info("create node: {}", node);
    }

    public void updateNode() {
        User user = new User();
        user.setId(2);
        user.setName("Lebron");
        zkClient.writeData(path, user);
        log.info("update {} success...", path);
    }

    public void deleteNode() {
        zkClient.delete(path);
        log.info("delete {} success...", path);
    }

    public void queryNode() {
        User user = zkClient.readData(path);
        log.info("read data from {}: {}", path, user);
    }

}
