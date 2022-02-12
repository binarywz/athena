package binary.wz.concurrent.util;

import java.util.concurrent.TimeUnit;

/**
 * @author binarywz
 * @date 2022/1/30 17:21
 * @description:
 */
public class Sleeper {
    public static void sleep(double i) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (i * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
