package com.taotao;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by geek on 2017/11/27.
 */
public class MapperTest {
    private TbItemCatMapper tbItemCatMapper;

    @Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
        this.tbItemCatMapper = applicationContext.getBean(TbItemCatMapper.class);
    }

    @Test
    public void testSelectOne() {
        TbItemCat record = new TbItemCat();
        //设置查询条件
        record.setId(1L);
        TbItemCat cat = this.tbItemCatMapper.selectOne(record);
        System.out.println(cat.getName());
    }
}
