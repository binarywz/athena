package binary.wz.spring.schema.handler;

import binary.wz.spring.schema.bean.Application;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author binarywz
 * @date 2022/4/13 21:49
 * @description:
 */
public class ApplicationBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    protected Class getBeanClass(Element element) {
        return Application.class;
    }

    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        String name = element.getAttribute("name");
        if (StringUtils.hasText(name)) {
            bean.addPropertyValue("name", name);
        }
    }
}
