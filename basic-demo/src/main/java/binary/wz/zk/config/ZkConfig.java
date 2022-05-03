package binary.wz.zk.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author binarywz
 * @date 2022/5/3 17:47
 * @description: Zookeeper配置参数
 */
public class ZkConfig {

    public final static String zkAddress;

    static {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("zk.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        zkAddress = properties.getProperty("zk.address", "127.0.0.1:2181");
    }
}
