package com.taotao.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by geek on 2017/6/12.
 */
public class PageControllerTest {

    @Test
    public void testPageHelper(){

        //创建spring上下文
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");

        TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);

        TbItemExample tbItemExample = new TbItemExample();
        //分页
        PageHelper.startPage(1,10);
        List<TbItem> list = tbItemMapper.selectByExample(tbItemExample);
        for (TbItem t:list) {
            System.out.println(t.getTitle());
        }
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        System.out.println(pageInfo.getTotal());
    }

}