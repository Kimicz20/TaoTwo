package com.taotao.controller;

import com.taotao.pojo.EUITreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by geek on 2017/6/28.
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EUITreeNode> listContentCategory(
            @RequestParam(value = "id",defaultValue = "0")
                    Long parentId){
        return contentCategoryService.listContentCategory(parentId);
    }

    @RequestMapping(value = "/create",
            method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createContentCategory(Long parentId,String name){
        return contentCategoryService.insertContentCategory(parentId,name);
    }

    @RequestMapping(value = "/delete",
            method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long id){
        return contentCategoryService.deleteContentCategory(id);
    }

    @RequestMapping(value = "/update",
            method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult updateContentCategory(Long id,String name){
        return contentCategoryService.updateContentCategory(id,name);
    }
}
