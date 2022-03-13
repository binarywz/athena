package binary.wz.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author binarywz
 * @date 2022/3/13 13:04
 * @description:
 */
public class App {

    public static void main(String[] args) {
        Car car = new Car();
        InvocationHandler timeLogger = new TimeLogger(car);
        Class<?> clazz = car.getClass();
        /**
         * ClassLoader loader 被代理对象的类加载器
         * Class<?>[] interfaces 被代理对象类实现的接口
         * InvocationHandler h
         */
        Vehicle vehicle = (Vehicle) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), timeLogger);
        vehicle.move();
    }

}
