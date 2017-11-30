package com.taotao.controller;

import com.taotao.service.PicService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by geek on 2017/6/13.
 */
@Controller
@RequestMapping("/pic")
public class PicController {

    private static final Logger LOGGER = Logger.getLogger(PicController.class);

    @Autowired
    private PicService pictureService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String uploadPicture(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletResponse response) throws Exception {
        return pictureService.uploadPicture(uploadFile);
    }
}
