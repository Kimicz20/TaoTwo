package com.taotao.rest.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by geek on 2017/7/24.
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/info/{itemId}")
    @ResponseBody
    public TaotaoResult queryItemBaseById(@PathVariable Long itemId){
        return itemService.queryItemInfo(itemId);
    }

    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public TaotaoResult queryItemDescById(@PathVariable Long itemId){

        return itemService.queryItemDesc(itemId);
    }

    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public TaotaoResult queryItemParamById(@PathVariable Long itemId){

        return itemService.queryItemParam(itemId);
    }
}
