package com.taotao.search.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.service.ItemImportService;
import com.taotao.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by geek on 2017/7/20.
 */
@Controller
@RequestMapping("/manager")
public class ItemController {

    @Autowired
    private ItemImportService itemService;

    @RequestMapping("/importall")
    @ResponseBody
    public TaotaoResult ImportAll(){

        TaotaoResult result = null;
        try {
        result = itemService.importAllToSolrIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return result;
    }

    @RequestMapping("/import/{itemId}")
    @ResponseBody
    public TaotaoResult ImportItem(@PathVariable Long itemId){

        TaotaoResult result = null;
        try {
            result = itemService.importItemToSolrIndex(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return result;
    }
}
