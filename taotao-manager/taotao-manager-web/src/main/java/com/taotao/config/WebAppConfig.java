package com.taotao.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by geek on 2017/11/29.
 */
public class WebAppConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/rest/*"};
    }

    @Override
    protected Filter[] getServletFilters() {
        //解决post乱码
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);

        //Put请求无法提交表单
        HttpPutFormContentFilter formContentFilter = new HttpPutFormContentFilter();

        //post请求转换为delete或者put 要用_method指定真正的请求参数
        HiddenHttpMethodFilter httpMethodFilter = new HiddenHttpMethodFilter();

        return new Filter[]{encodingFilter,formContentFilter,httpMethodFilter};
    }
}
