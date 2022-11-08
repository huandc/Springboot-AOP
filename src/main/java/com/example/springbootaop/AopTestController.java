package com.example.springbootaop;
 
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
 
/**
 * 类定义为切面类
 */
@Aspect
@Component
public class AopTestController {
    private static final Logger logger = LoggerFactory.getLogger(AopTestController.class);
    /**
     * 定义一个切点
     */
    @Pointcut(value = "execution(public String test (..))")
    public void cutOffPoint() {
    }

    @Before("cutOffPoint()")
    public void beforeTest(){
        logger.info("我在test方法之前执行");
    }

    @After("cutOffPoint()")
    public void doAfter(){
        logger.info("我是在test之后执行的");
    }
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    @Around("cutOffPoint()")
    public Object doAround(ProceedingJoinPoint pjp){
        startTime.set(System.currentTimeMillis());
        Object obj;
        try{
            obj = pjp.proceed();
            logger.info("执行返回值 : " + obj);
            logger.info(pjp.getSignature().getName()+"方法执行耗时: " + (System.currentTimeMillis() - startTime.get()));
        } catch (Throwable throwable) {
            obj=throwable.toString();
        }
        return obj;
    }
}