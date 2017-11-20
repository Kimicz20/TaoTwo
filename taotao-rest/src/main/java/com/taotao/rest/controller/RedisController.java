package com.taotao.rest.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.service.ContentService;
import com.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by geek on 2017/6/30.
 */
@Controller
@RequestMapping("/content")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/sync/{gategoryId}")
    @ResponseBody
    TaotaoResult syncRedisCache(@PathVariable Long gategoryId){
        return redisService.redisCacheSync(gategoryId);
    }

}
