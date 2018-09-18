package com.hxh.Quesan.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect//注解表示这是个切面
@Component//将对象通过依赖注入的方式给service
public class LogAspect {//对所有访问Indexcontroller的方法做切面截获
    private static final Logger logger= LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.hxh.Quesan.controller.*(..))")//*<返回值> com.hxh.Quesan.controller<类包>.*<方法>(..)<参数>
    public void beforeMethod(){//在所有调用Indexcontroller中方法之前都调用该方法
        logger.info("before method");
    }
    @After("execution(* com.hxh.Quesan.controller.*(..))")
    public void afterMethod(){//在所有调用Indexcontroller中方法之后都调用该方法
        logger.info("after method");
    }

}
