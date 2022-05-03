package binary.wz.spring.schema;

import binary.wz.spring.schema.bean.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author binarywz
 * @date 2022/4/13 22:00
 * @description:
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/xml/application.xml");
        Application application = (Application) ctx.getBean("application");
        System.out.println(application.getName());
    }
}
