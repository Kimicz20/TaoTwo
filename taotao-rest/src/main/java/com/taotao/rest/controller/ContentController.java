package com.taotao.rest.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.ContentService;
import com.taotao.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by geek on 2017/6/29.
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/list/{categoryId}")
    @ResponseBody
    public TaotaoResult queryContents(@PathVariable Long categoryId){
        TaotaoResult result = null;
        try {
            result = contentService.queryContent(categoryId);
        }catch (Exception e) {
                e.printStackTrace();
                return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return result;
    }

}
