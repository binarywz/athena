package binary.wz.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author binarywz
 * @date 2022/3/13 13:00
 * @description:
 */
public class TimeLogger implements InvocationHandler {

    private Object target;

    public TimeLogger(Object target) {
        super();
        this.target = target;
    }

    /**
     * 代理增强方法
     * @param proxy 被代理对象
     * @param method 被代理对象的方法
     * @param args 方法的参数
     * @return 方法的返回值
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("旅途开始...");
        method.invoke(target);
        long end = System.currentTimeMillis();
        System.out.println("旅途结束，旅途花费时间: " + (end - start) + "毫秒！");
        return null;
    }
}
