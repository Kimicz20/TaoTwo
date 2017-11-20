package customtag;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Created by geek on 2017/6/20.
 */
public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    protected Class getBeanClas(Element element){
        return User.class;
    }

    protected void doParse(Element element, BeanDefinitionBuilder bean){

        //从element元素中获取对应属性
        String userName = element.getAttribute("userName");
        String email = element.getAttribute("email");

        if (StringUtils.hasText(userName)){
            bean.addPropertyValue("userName",userName);
        }
        if (StringUtils.hasText(email)){
            bean.addPropertyValue("email",email);
        }
    }

}
