package com.taotao.config;

import com.taotao.bean.TaoTaoConstant;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

/**
 * Created by geek on 2017/11/29.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.taotao", excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
@Import({DataSourceConfig.class,AOPConfig.class})
public class RootConfig {

    private final static Logger LOGGER = Logger.getLogger(RootConfig.class);

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setSystemPropertiesModeName("SYSTEM_PROPERTIES_MODE_OVERRIDE");
        configurer.setIgnoreResourceNotFound(true);
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource resource1 = resourcePatternResolver.getResource("classpath:jdbc.properties");
        Resource resource2 = resourcePatternResolver.getResource("classpath:config.properties");
        configurer.setLocations(new Resource[]{resource1, resource2});
        return configurer;
    }

    @Bean
    public TaoTaoConstant propertiesService(
            @Value("#{T(java.lang.Boolean).parseBoolean('${IS_FTP_ENABLED}')}") Boolean IS_FTP_ENABLED,
            @Value("${PIC_FTP_HOSTNAME}") String PIC_FTP_HOSTNAME,
            @Value("#{T(java.lang.Integer).parseInt('${PIC_FTP_PORT}')}") int PIC_FTP_PORT,
            @Value("${PIC_FTP_USERNAME}") String PIC_FTP_USERNAME,
            @Value("${PIC_FTP_PASSWORD}") String PIC_FTP_PASSWORD,
            @Value("${PIC_FTP_BASEPATH}") String PIC_FTP_BASEPATH,
            @Value("${IMAGE_BASE_URL}") String IMAGE_BASE_URL,
            @Value("${REPOSITORY_PATH}") String REPOSITORY_PATH
    ) {
        return new TaoTaoConstant(IS_FTP_ENABLED, PIC_FTP_HOSTNAME, PIC_FTP_PORT, PIC_FTP_USERNAME,
                PIC_FTP_PASSWORD, PIC_FTP_BASEPATH, IMAGE_BASE_URL, REPOSITORY_PATH);
    }


}
