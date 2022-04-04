package binary.wz.spi;

import java.util.ServiceLoader;

/**
 * @author binarywz
 * @date 2022/4/4 20:51
 * @description:
 * SPI的设计目标:
 * 面向对象的设计里，模块之间基于接口编程，模块之间不对实现类进行硬编码。一旦代码里涉及具体的
 * 实现类，就违反了可插拔的原则，若需要替换一种实现，就需要修改代码。为了实现在模块装配的时候，
 * 可以灵活替换实现类，就需要一种服务发现机制。Java Spi可以提供为某个接口寻找服务实现的机制。
 * 有点类似IOC的思想，就是将装配的控制权转移到代码之外。
 * SPI的具体约定:
 * 当服务的提供者（provider），提供了一个接口的多种实现时。一般会在jar包的META-INF/services目录
 * 下，创建该接口的同名文件，该文件里面的内容就是该服务接口的具体实现类的名称。而当外部加载这个模块
 * 时，可以通过META-INF/services里的配置文件得到具体的实现类名称，并加载实例化，完成模块的装配。
 */
public class App {
    public static void main(String[] args) {
        /**
         * JDK SPI存在的问题:
         * 1.JDK标准的SPI会一次性实例化扩展点的所有实现，若有扩展实现初始化很耗时，但是没有用上也加载会浪费资源;
         * 2.若加载失败，则连扩展的名称都找不到了;
         */
        ServiceLoader<Executor> executors = ServiceLoader.load(Executor.class);
        for (Executor executor : executors) {
            executor.execute();
        }
    }
}
