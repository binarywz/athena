package binary.wz.zk.listener;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;

/**
 * @author binarywz
 * @date 2022/5/3 18:23
 * @description:
 */
@Slf4j
public class DataChangedListener implements IZkDataListener {
    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        log.info("node data changed, dataPath: {}, data: {}", data, dataPath);
    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        log.info("node data deleted, dataPath: {}", dataPath);
    }
}
