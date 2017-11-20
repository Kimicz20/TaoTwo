package com.taotao.rest.service;

import com.taotao.pojo.TaotaoResult;

/**
 * Created by geek on 2017/6/30.
 */
public interface RedisService {

    TaotaoResult redisCacheSync(Long gategoryId);
}
