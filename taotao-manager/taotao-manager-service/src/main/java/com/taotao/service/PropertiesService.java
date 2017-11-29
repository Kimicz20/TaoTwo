package com.taotao.service;

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
@Service
public class PropertiesService {

    @Value("#{T(java.lang.Boolean).parseBoolean('${IS_FTP_ENABLED}')}")
    public Boolean IS_FTP_ENABLED;

    @Value("${PIC_FTP_HOSTNAME}")
    public String PIC_FTP_HOSTNAME;

    @Value("${PIC_FTP_PORT}")
    public Integer PIC_FTP_PORT;

    @Value("${PIC_FTP_USERNAME}")
    public String PIC_FTP_USERNAME;

    @Value("${PIC_FTP_PASSWORD}")
    public String PIC_FTP_PASSWORD;

    @Value("${PIC_FTP_BASEPATH}")
    public String PIC_FTP_BASEPATH;

    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;

    @Value("${REPOSITORY_PATH}")
    public String REPOSITORY_PATH;

}
