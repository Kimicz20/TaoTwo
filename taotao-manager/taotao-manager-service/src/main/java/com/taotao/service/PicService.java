package com.taotao.service;

import com.taotao.bean.TaoTaoConstant;
import com.taotao.bean.PicUploadResult;
import com.taotao.utils.FtpUtil;
import com.taotao.utils.IDUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by geek on 2017/6/13.
 */
@Service
public class PicService {

    @Autowired
    private TaoTaoConstant taoTaoConstant;

    //允许上传的格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};

    public String uploadPicture(MultipartFile uploadFile) {

        PicUploadResult uploadResult = new PicUploadResult();
        //1.校验格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        uploadResult.setError(isLegal ? 0 : 1);

        try {
            //2.生成新的文件名称
            String oldName = uploadFile.getOriginalFilename();
            String newName = IDUtils.genImageName() + oldName.substring(oldName.lastIndexOf("."));

            //3.文件路径
            String filePath = (new DateTime()).toString("/yyyy/MM/dd");

            //4.上传到服务器的文件夹中
            String picUrl = taoTaoConstant.IMAGE_BASE_URL + filePath + "/" + newName;
            uploadResult.setUrl(picUrl);

            if (taoTaoConstant.IS_FTP_ENABLED) {
                isLegal = FtpUtil.uploadFile(taoTaoConstant.PIC_FTP_HOSTNAME,
                        taoTaoConstant.PIC_FTP_PORT,
                        taoTaoConstant.PIC_FTP_USERNAME,
                        taoTaoConstant.PIC_FTP_PASSWORD,
                        taoTaoConstant.PIC_FTP_BASEPATH,
                        filePath, newName, uploadFile.getInputStream());

            } else {
                String path = taoTaoConstant.REPOSITORY_PATH + File.separator + "images" + StringUtils.replace(filePath,"/","\\");
                File f = new File(path);
                if (!f.exists()) {
                    f.mkdirs();
                }
            File file = new File(path + File.separator + newName);
            uploadFile.transferTo(file);
            isLegal = false;
            BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    uploadResult.setWidth(image.getWidth() + "");
                    uploadResult.setHeight(image.getHeight() + "");
                    isLegal = true;
                }
                if (!isLegal) {
                    file.delete();
                }
            }
            uploadResult.setError(isLegal ? 0 : 1);
        } catch (IOException e) {
        }
        return JsonUtils.objectToJson(uploadResult);
    }
}
