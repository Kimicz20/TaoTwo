package com.taotao.controller;

import com.taotao.pojo.EUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by geek on 2017/6/28.
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult saveContent(TbContent tbContent){
        return contentService.saveContent(tbContent);
    }

    @RequestMapping("/query/list")
    @ResponseBody
    EUIDataGridResult queryContentList(Long categoryId,@RequestParam(defaultValue="1")Integer page,
                                  @RequestParam(defaultValue="20")Integer rows){
        return contentService.queryContentList(categoryId,page,rows);
    }

    @RequestMapping("/edit")
    @ResponseBody
    public TaotaoResult editContent(TbContent tbContent){
        return contentService.editContent(tbContent);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContent(String ids){
        return contentService.deleteContent(ids);
    }
}
