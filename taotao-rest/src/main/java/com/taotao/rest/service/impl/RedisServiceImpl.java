package com.taotao.rest.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RedisService;
import com.taotao.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by geek on 2017/6/30.
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    @Qualifier("jedisClientCluster")
    private JedisClient jedis;

    @Value("${REDIS_AD_KEY}")
    private String REDIS_AD_KEY;

    @Override
    public TaotaoResult redisCacheSync(Long gategoryId) {
        try {
            jedis.hdel(REDIS_AD_KEY,gategoryId+"");
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }
}
