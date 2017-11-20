package customtag;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by geek on 2017/6/20.
 */
public class customTagTest {

    @Test
    public void test(){
        ApplicationContext bf = new ClassPathXmlApplicationContext("classpath:customtag/test.xml");
        User user = (User) bf.getBean("t");
        System.out.println(user.getUserName()+","+user.getEmail());
    }
}