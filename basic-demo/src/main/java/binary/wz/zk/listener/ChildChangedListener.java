package binary.wz.zk.listener;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;

import java.util.List;

/**
 * @author binarywz
 * @date 2022/5/3 18:23
 * @description:
 */
@Slf4j
public class ChildChangedListener implements IZkChildListener {
    @Override
    public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
        log.info("child node changed, parentPath: {}, childs: {}", parentPath, currentChilds);
    }
}
