package com.taotao.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by geek on 2017/11/29.
 */
@Configuration
public class DataSourceConfig {

    private final static Logger LOGGER = Logger.getLogger(DataSourceConfig.class);

    //数据源配置
    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource dataSource(
            @Value("${spring.datasource.url}") String dbUrl,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.driverClassName}") String driverClassName,
            @Value("#{T(java.lang.Integer).parseInt('${spring.datasource.initialSize}')}") Integer initialSize,
            @Value("#{T(java.lang.Integer).parseInt('${spring.datasource.minIdle}')}") Integer minIdle,
            @Value("#{T(java.lang.Integer).parseInt('${spring.datasource.maxActive}')}") Integer maxActive,
            @Value("#{T(java.lang.Integer).parseInt('${spring.datasource.maxWait}')}") Integer maxWait,
            @Value("#{T(java.lang.Integer).parseInt('${spring.datasource.timeBetweenEvictionRunsMillis}')}") Integer timeBetweenEvictionRunsMillis,
            @Value("#{T(java.lang.Integer).parseInt('${spring.datasource.minEvictableIdleTimeMillis}')}") Integer minEvictableIdleTimeMillis,
            @Value("${spring.datasource.validationQuery}") String validationQuery,
            @Value("#{T(java.lang.Boolean).parseBoolean('${spring.datasource.testWhileIdle}')}") Boolean testWhileIdle,
            @Value("#{T(java.lang.Boolean).parseBoolean('${spring.datasource.testOnBorrow}')}") Boolean testOnBorrow,
            @Value("#{T(java.lang.Boolean).parseBoolean('${spring.datasource.testOnReturn}')}") Boolean testOnReturn,
            @Value("#{T(java.lang.Boolean).parseBoolean('${spring.datasource.poolPreparedStatements}')}") Boolean poolPreparedStatements,
            @Value("#{T(java.lang.Integer).parseInt('${spring.datasource.maxPoolPreparedStatementPerConnectionSize}')}") Integer maxPoolPreparedStatementPerConnectionSize,
            @Value("${spring.datasource.filters}") String filters,
            @Value("{spring.datasource.connectionProperties}") String connectionProperties
    ) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Initialize the data source... dbUrl " + dbUrl);
        }

        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            LOGGER.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);
        return datasource;
    }


    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("dialect", "mysql");
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("pageSizeZero", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("params", "pageNum=start;pageSize=limit;");
        p.setProperty("supportMethodsArguments", "true");
        p.setProperty("returnPageInfo", "check");
        pageHelper.setProperties(p);
        return pageHelper;
    }

    //mybatis的配置
    @Bean(name = "sqlSessionFactoryBean")
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource, Interceptor pageHelper) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeAliasesPackage("com.taotao.pojo");
        //配置pageHelper
        sqlSessionFactory.setPlugins(new Interceptor[]{pageHelper});
        return sqlSessionFactory;
    }

    //通用mapper配置
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        Properties properties = new Properties();
        properties.setProperty("mappers", "tk.mybatis.mapper.common.Mapper,tk.mybatis.mapper.common.special.InsertListMapper");
        configurer.setProperties(properties);
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        configurer.setBasePackage("com.taotao.mapper");
        return configurer;
    }

    //事物管理器
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
