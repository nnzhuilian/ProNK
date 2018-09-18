package com.hxh.Quesan.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect//注解表示这是个切面
@Component//将对象通过依赖注入的方式给service
public class LogAspect {//对所有访问Indexcontroller的方法做切面截获
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.hxh.Quesan.controller.*Controller.*(..))")//*<返回值> com.hxh.Quesan.controller<类包>.*<方法>(..)<参数>
    public void beforeMethod(JoinPoint joinPoint) {//在所有调用…Controller中方法之前都调用该方法
        //JoinPoint 代表切点
        StringBuilder sb = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {//打印参数
            sb.append("arg:" + arg.toString() + "|");
        }
        logger.info("before method:" + sb.toString());
    }

    @After("execution(* com.hxh.Quesan.controller.*.*(..))")//在所有调用controller中方法之后都调用该方法
    public void afterMethod() {
        logger.info("after method" + new Date());
    }
}
