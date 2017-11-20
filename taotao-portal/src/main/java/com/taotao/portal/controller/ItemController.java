package com.taotao.portal.controller;

import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping("/{id}")
    public String itemShow(@PathVariable Long id, Model model){
        ItemInfo item = itemService.getItemById(id);
        model.addAttribute("item",item);
        return "item";
    }

    @RequestMapping(value = "/desc/{id}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String itemDesc(@PathVariable("id") Long id){
        String desc = itemService.getItemDescById(id);
        return desc;
    }

    @RequestMapping(value = "/param/{id}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
        public String itemParam(@PathVariable("id") Long id){
        String param = itemService.getItemParamById(id);
        return param;
    }
}
