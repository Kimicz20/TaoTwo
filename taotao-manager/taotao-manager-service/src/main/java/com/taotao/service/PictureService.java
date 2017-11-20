package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by geek on 2017/6/13.
 */
public interface PictureService {
    String uploadPicture(MultipartFile multipartFile);
}
