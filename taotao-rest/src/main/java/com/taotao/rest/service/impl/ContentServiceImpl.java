package com.taotao.rest.service.impl;

import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by geek on 2017/6/29.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    @Qualifier("jedisClientCluster")
    private JedisClient jedis;

    @Value("${REDIS_AD_KEY}")
    private String REDIS_AD_KEY;

    @Override
    public TaotaoResult  queryContent(long categoryId) {

        //1.在缓存中查询
        try {
            String result = jedis.hget(REDIS_AD_KEY,categoryId+"");
            if(!StringUtils.isEmpty(result)){
                //存在缓存中，字符串解析为list返回
                List<TbContent> contents  = JsonUtils.jsonToList(result,TbContent.class);
                return TaotaoResult.ok(contents);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //2.数据库查询
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> contents  = contentMapper.selectByExample(example);

        //3.向缓存中添加
        //list转string
        String JsonStr = JsonUtils.objectToJson(contents);
        try {
            //存入redis中
            jedis.hset(REDIS_AD_KEY,categoryId+"",JsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TaotaoResult.ok(contents);
    }
}
