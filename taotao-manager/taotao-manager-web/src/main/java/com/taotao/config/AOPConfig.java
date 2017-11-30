package com.taotao.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

/**
 * Created by geek on 2017/11/30.
 */
@Component
@Aspect
@ImportResource("classpath:aop-config.xml")
public class AOPConfig {

    @Pointcut("execution(* com.taotao.service.*.*(..))")
    public void serviceAnnotatedClass() {
    }
}
