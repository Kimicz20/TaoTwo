package com.taotao.bean;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * Created by geek on 2017/11/29.
 */
public class TaoTaoConstant {

    public Boolean IS_FTP_ENABLED;

    public String PIC_FTP_HOSTNAME;

    public int PIC_FTP_PORT;

    public String PIC_FTP_USERNAME;

    public String PIC_FTP_PASSWORD;

    public String PIC_FTP_BASEPATH;

    public String IMAGE_BASE_URL;

    public String REPOSITORY_PATH;

    public TaoTaoConstant(Boolean IS_FTP_ENABLED, String PIC_FTP_HOSTNAME, int PIC_FTP_PORT,
                          String PIC_FTP_USERNAME, String PIC_FTP_PASSWORD, String PIC_FTP_BASEPATH,
                          String IMAGE_BASE_URL, String REPOSITORY_PATH) {
        this.IS_FTP_ENABLED = IS_FTP_ENABLED;
        this.PIC_FTP_HOSTNAME = PIC_FTP_HOSTNAME;
        this.PIC_FTP_PORT = PIC_FTP_PORT;
        this.PIC_FTP_USERNAME = PIC_FTP_USERNAME;
        this.PIC_FTP_PASSWORD = PIC_FTP_PASSWORD;
        this.PIC_FTP_BASEPATH = PIC_FTP_BASEPATH;
        this.IMAGE_BASE_URL = IMAGE_BASE_URL;
        this.REPOSITORY_PATH = REPOSITORY_PATH;
    }
}
