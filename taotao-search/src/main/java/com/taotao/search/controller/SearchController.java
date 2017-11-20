package com.taotao.search.controller;

import com.alibaba.druid.util.StringUtils;
import com.taotao.pojo.SearchResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.search.service.ItemSearchService;
import com.taotao.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by geek on 2017/7/20.
 */
@Controller
public class SearchController {

    @Autowired
    private ItemSearchService itemSearchService;

//    @RequestMapping(value="/{condition}/{page}/{rows}")
    @RequestMapping("/query")
    @ResponseBody
    public TaotaoResult search(@RequestParam("condition") String condition,
                               @RequestParam(value = "rows",
                                       defaultValue = "60",required = false) Integer rows,
                               @RequestParam(value = "page",defaultValue = "1") Integer page)  {

        if (StringUtils.isEmpty(condition)){
            return TaotaoResult.build(400,"查询不能为空");
        }

        SearchResult searchResult = null;
        try {
            condition = new String(condition.getBytes("iso8859-1"),"utf-8");
            searchResult = itemSearchService.itemSearch(condition, page,rows);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok(searchResult);
    }

}
