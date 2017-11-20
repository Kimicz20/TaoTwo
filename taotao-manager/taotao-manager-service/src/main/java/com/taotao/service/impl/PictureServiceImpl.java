package com.taotao.service.impl;

import com.taotao.service.PictureService;
import com.taotao.utils.FtpUtil;
import com.taotao.utils.IDUtils;
import com.taotao.utils.JsonUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by geek on 2017/6/13.
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Value("${PIC_FTP_HOSTNAME}")
    private String PIC_FTP_HOSTNAME;

    @Value("${PIC_FTP_PORT}")
    private Integer PIC_FTP_PORT;

    @Value("${PIC_FTP_USERNAME}")
    private String PIC_FTP_USERNAME;

    @Value("${PIC_FTP_PASSWORD}")
    private String PIC_FTP_PASSWORD;

    @Value("${PIC_FTP_BASEPATH}")
    private String PIC_FTP_BASEPATH;

    @Value("${PIC_FTP_REAL_PATH}")
    private String PIC_FTP_REAL_PATH;

    @Override
    public String uploadPicture(MultipartFile uploadFile) {
        Map reslutMap = new HashMap();
        try {
            //获取文件名称
            String oldName = uploadFile.getOriginalFilename();
            String type = oldName.substring(oldName.lastIndexOf("."));
            //通过工具类生成新的文件名称
            String newName = IDUtils.genImageName() + type;

            //图片在服务器上文件路径
            DateTime dateTime = new DateTime();
            String filePath = dateTime.toString("/yyyy/MM/dd");
            //上传到服务器的文件夹中
            boolean result = FtpUtil.uploadFile(PIC_FTP_HOSTNAME,PIC_FTP_PORT,
                    PIC_FTP_USERNAME,
                    PIC_FTP_PASSWORD,
                    PIC_FTP_BASEPATH,
                    filePath,newName,uploadFile.getInputStream());
            if (!result){
                reslutMap.put("error",1);
                reslutMap.put("message","图片上传是失败！");
            }else{
                reslutMap.put("error",0);
                reslutMap.put("url",PIC_FTP_REAL_PATH+filePath+"/"+newName);
            }
        } catch (IOException e) {
            reslutMap.put("error",1);
            reslutMap.put("message","上传出现异常！");
        }
        return JsonUtils.objectToJson(reslutMap);
    }
}
