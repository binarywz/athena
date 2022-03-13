package binary.wz.proxy.cglib;

/**
 * @author binarywz
 * @date 2022/3/13 13:42
 * @description:
 */
public class App {

    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        Train train = (Train) proxy.getProxy(Train.class);
        train.move();
    }

}
