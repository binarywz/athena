package binary.wz.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author binarywz
 * @date 2022/3/13 13:38
 * @description:
 */
public class CglibProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
        // 设置创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    /**
     * 拦截目标类方法的调用
     * @param obj 目标类的实例
     * @param method 目标方法的反射对象
     * @param args 方法的参数
     * @param proxy 代理类的实例
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("旅途开始");
        // 代理类调用父类的方法
        proxy.invokeSuper(obj, args);
        System.out.println("旅途结束");
        return null;
    }
}
