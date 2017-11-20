package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.fabric.xmlrpc.base.Array;
import com.sun.tools.javac.util.ArrayUtils;
import com.sun.tools.javac.util.StringUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.EUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;
import com.taotao.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by geek on 2017/6/28.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public TaotaoResult saveContent(TbContent content) {

        //1.补全信息
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //2.存储数据库
        contentMapper.insert(content);

        //缓存同步
        syncRedisCache(content.getCategoryId());

        return TaotaoResult.ok();
    }

    @Override
    public EUIDataGridResult queryContentList(Long categoryId, Integer page, Integer rows) {

        //1.查询数据
        TbContentExample example =  new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentMapper.selectByExample(example);

        //2.分页处理
        PageHelper.startPage(page,rows);
        PageInfo<TbContent> info = new PageInfo<TbContent>(list);

        //2.结果显示
        EUIDataGridResult result =  new EUIDataGridResult();
        result.setTotal(info.getTotal());
        result.setRows(list);

        return result;
    }

    @Override
    public TaotaoResult editContent(TbContent tbContent) {

        //更新时间修改
        tbContent.setUpdated(new Date());

        contentMapper.updateByPrimaryKey(tbContent);
        //缓存同步
        syncRedisCache(tbContent.getCategoryId());
        return TaotaoResult.ok();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public TaotaoResult deleteContent(String ids) {
        TbContentExample example =  new TbContentExample();
        Criteria criteria = example.createCriteria();

        //类型转换
        List<Long> inList = new ArrayList<>();
        String[] tmp =  ids.split(",");

        //1.查找所属类别
        long  rL = Long.valueOf(tmp[0]);
        criteria.andIdEqualTo(rL);
        List<TbContent> list = contentMapper.selectByExample(example);
        long gategoryId = list.get(0).getCategoryId();

        //3.数据库删除
        for(String t:tmp){
            inList.add(Long.valueOf(t));
        }
        criteria = example.createCriteria();
        criteria.andIdIn(inList);
        contentMapper.deleteByExample(example);

        //4.缓存同步
        syncRedisCache(gategoryId);

        return TaotaoResult.ok();
    }

    /**
     * 缓存同步
     * @param gategoryId 类别 key
     */
    @Override
    public void syncRedisCache(Long gategoryId) {
        try {
            HttpClientUtil.doGet("http://localhost:8081/rest/content/sync/"+gategoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
