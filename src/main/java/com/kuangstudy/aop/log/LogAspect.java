package com.kuangstudy.aop.log;

import com.kuangstudy.pojo.Logs;
import com.kuangstudy.service.log.LogsService;
import com.kuangstudy.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Component
@Aspect
@Slf4j
// @Order注解的数字越小越先进入
@Order(0)
public class LogAspect {

    @Autowired
    private LogsService logsService;

    // 1: 定义切入点，标注有注解@KsdLog的所有函数，通过@Pointcut判断可以进入到具体增强的通知
    @Pointcut("@annotation(com.kuangstudy.aop.log.KsdLog)")
    public void logpointcut() {}


    @Around("logpointcut()")
    public Object beforeAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            // 1:方法执行的开始时间
            log.info("日志切面start");
            long startTime = System.currentTimeMillis();
            // 2:执行真实方法
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            System.out.println(signature.getMethod().getName());
            System.out.println(signature.getReturnType());
            System.out.println(Arrays.toString(signature.getParameterNames()));
            System.out.println(Arrays.toString(signature.getParameterTypes()));
            Object methodReturnValue = proceedingJoinPoint.proceed();
            // 3:方法执行的结束时间
            long endTime = System.currentTimeMillis();
            // 4：方法的总耗时
            long total = endTime - startTime;
            log.info("当前执行方法的返回值是：{}", methodReturnValue);

            //----
            Logs logs = new Logs();
            logs.setId(UUID.randomUUID().toString());
            logs.setClassName("");
            logs.setMethodName(signature.getMethod().getName());
            logs.setParams("");
            logs.setTime(total + "");
            logsService.save(logs);
            //----

            log.info("当前方法:{}，执行的时间是：{}ms", signature.getMethod().getName(), total);
            log.info("日志切面end");
        } catch (Throwable ex) {
            log.info("执行方法出错.....,{}", ex);
            throw ex;
        }
        return null;
    }
}
