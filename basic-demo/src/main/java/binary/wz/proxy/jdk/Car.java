package binary.wz.proxy.jdk;

import java.util.Random;

/**
 * @author binarywz
 * @date 2022/3/13 12:58
 * @description:
 */
public class Car implements Vehicle {

    @Override
    public void move() {
        try {
            System.out.println("汽车行驶中...");
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
