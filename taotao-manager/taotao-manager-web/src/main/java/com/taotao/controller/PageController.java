package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by geek on 2017/6/12.
 */
@Controller
public class PageController {

    /**
     * 后台管理主页面 eadyUI
     * @return
     */
    @RequestMapping("/")
    String show(){
        return "index";
    }

    /**
     * 各个子页面
     * @param modePage  模块名称
     * @return
     */
    @RequestMapping("/{modePage}")
    String showModePage(@PathVariable String modePage){
        return modePage;
    }
}
