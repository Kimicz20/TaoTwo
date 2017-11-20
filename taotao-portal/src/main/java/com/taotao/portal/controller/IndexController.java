package com.taotao.portal.controller;

import com.taotao.portal.service.ContentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转
 */
@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;

    private Log logger = LogFactory.getLog(this.getClass());

    @RequestMapping("/index")
    String index(Model model){
        String JsonStr = contentService.getContentList();
        logger.info("数据显示:"+JsonStr);
        model.addAttribute("ad1",JsonStr);
        return "index";
    }
}
