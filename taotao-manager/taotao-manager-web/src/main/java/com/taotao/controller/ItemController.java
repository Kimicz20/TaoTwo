package com.taotao.controller;

import com.taotao.pojo.*;
import com.taotao.service.ItemCatService;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by geek on 2017/6/11.
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/{id}")
    @ResponseBody
    TbItem ItemQuery(@PathVariable Long id){
        return itemService.ItemQueryById(id);
    }

    @RequestMapping("/list")
    @ResponseBody
    EUIDataGridResult getItemList(@RequestParam(defaultValue="1")Integer page,
                                   @RequestParam(defaultValue="30")Integer rows){
        return itemService.getPageList(page,rows);
    }

    @RequestMapping("/cat/list")
    @ResponseBody
    List<EUITreeNode> getCatList(@RequestParam(value = "id",
                            defaultValue="0")Long id){
        return itemCatService.getCatList(id);
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    TaotaoResult addTbItem(TbItem tbItem, String desc, String itemParams) throws Exception {
        return itemService.insertTbItem(tbItem,desc,itemParams);
    }

    @RequestMapping("/param/query/itemcatid/{itemcatid}")
    @ResponseBody
    TaotaoResult queryItemParam(@PathVariable Long itemcatid){
        return itemService.queryParam(itemcatid);
    }

    @RequestMapping(value = "/param/save/{cid}",method = RequestMethod.POST)
    @ResponseBody
    TaotaoResult addParam(@PathVariable Long cid,@RequestParam("paramData") String paramData){
        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(cid);
        tbItemParam.setParamData(paramData);
        return itemService.insertParam(tbItemParam);
    }
}
