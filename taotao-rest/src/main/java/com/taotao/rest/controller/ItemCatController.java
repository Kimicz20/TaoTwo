package com.taotao.rest.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.taotao.rest.pojo.ItemCatsResult;
import com.taotao.rest.service.ItemCatService;
import com.taotao.rest.service.impl.ItemCatServiceImpl;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;

/**
 * Created by geek on 2017/6/28.
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(value = "/itemcat/all",
            produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getCatsJsonP(String callback){

        //获取结果集
        ItemCatsResult result = itemCatService.getItemCatList();

        //转换成json格式
        String data = JsonUtils.objectToJson(result);

        //拼接返回结果
        String JSStr = callback + "(" + data + ");";

        return JSStr;
    }

}
