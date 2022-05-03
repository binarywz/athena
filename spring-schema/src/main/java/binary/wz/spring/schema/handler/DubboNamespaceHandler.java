package binary.wz.spring.schema.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author binarywz
 * @date 2022/4/13 21:46
 * @description:
 */
public class DubboNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("application", new ApplicationBeanDefinitionParser());
    }
}
