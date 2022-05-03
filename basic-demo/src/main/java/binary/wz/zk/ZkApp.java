package binary.wz.zk;

import binary.wz.zk.manager.NodeManager;

/**
 * @author binarywz
 * @date 2022/5/3 18:03
 * @description:
 */
public class ZkApp {
    public static void main(String[] args) {
        NodeManager nodeManager = new NodeManager("/binaryNode");
        nodeManager.createNoe();
        nodeManager.updateNode();
        nodeManager.deleteNode();
    }
}
