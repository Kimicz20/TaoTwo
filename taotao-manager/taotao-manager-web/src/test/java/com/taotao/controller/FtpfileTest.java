package com.taotao.controller;

import com.taotao.utils.FtpUtil;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by geek on 2017/6/13.
 */
public class FtpfileTest {

    @Test
    public void testFtp() throws Exception {

        InputStream in = ClassLoader.getSystemResourceAsStream("config.properties");
        Properties prop = new Properties();
        prop.load(in);
        String PIC_FTP_HOSTNAME = prop.getProperty("PIC_FTP_HOSTNAME");
        int PIC_FTP_PORT = Integer.valueOf(prop.getProperty("PIC_FTP_PORT"));
        String PIC_FTP_USERNAME = prop.getProperty("PIC_FTP_USERNAME");
        String PIC_FTP_PASSWORD = prop.getProperty("PIC_FTP_PASSWORD");
        String PIC_FTP_BASEPATH = prop.getProperty("PIC_FTP_BASEPATH");
        String PIC_FTP_REAL_PATH =prop.getProperty("PIC_FTP_REAL_PATH");

        //图片在服务器上文件路径
        DateTime dateTime = new DateTime();
        String filePath = dateTime.toString("/yyyy/MM/dd");
        File f = new File("");
        boolean result = FtpUtil.uploadFile(PIC_FTP_HOSTNAME,PIC_FTP_PORT,
                PIC_FTP_USERNAME,
                PIC_FTP_PASSWORD,
                PIC_FTP_BASEPATH,
                filePath,"1.png",new FileInputStream(new File("C:\\Users\\geek\\Pictures\\gaigeming.jpg")));
        if (!result){
            System.out.println("error");
        }else{
            System.out.println("success");
        }
    }

}
