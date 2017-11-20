package com.taotao.portal.controller;

import com.taotao.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * Created by geek on 2017/7/22.
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String condition,
                         @RequestParam(defaultValue = "1") Integer page,
                         Model model){

        if (condition !=null){
            try {
                condition =  new String(condition.getBytes("iso8859-1"),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        SearchResult result = searchService.search(condition,page+"");
        model.addAttribute("query",condition);

        if(result !=null){
            model.addAttribute("totalPages",result.getPageCount());
            model.addAttribute("itemList",result.getItemList());
            model.addAttribute("page",result.getCurPage());
        }
        return "search";
    }
}
