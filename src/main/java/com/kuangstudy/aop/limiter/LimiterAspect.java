package com.kuangstudy.aop.limiter;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
@Order(1)
public class LimiterAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DefaultRedisScript<Boolean> ipLimitLua;

    @Pointcut("@annotation(com.kuangstudy.aop.limiter.AcessLimter)")
    public void limiterPonicut() {
    }

    @Before("limiterPonicut()")
    public void limiter(JoinPoint joinPoint) {
        log.info("限流切面start");
        log.info("限流进来了");
        // 1：获取方法的签名作为key
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String classname = methodSignature.getMethod().getDeclaringClass().getName();
        String packageName = methodSignature.getMethod().getDeclaringClass().getPackage().getName();
        log.info("classname:{},packageName:{}", classname, packageName);
        // 4: 读取方法的注解信息获取限流参数
        AcessLimter annotation = method.getAnnotation(AcessLimter.class);
        // 5：获取注解方法名
        String methodNameKey = method.getName();
        // 6：获取服务请求的对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        String userIp = RequestUtils.getIpAddr(request);
        log.info("用户IP是：{}", userIp);
        // 7：通过方法反射获取注解的参数
        int limit = annotation.limit();
        int timeout = annotation.timeout();
        String redisKey = classname + "." + methodNameKey + ":" + userIp;
        // 8: 请求lua脚本
        Boolean acquired = true;
        acquired = stringRedisTemplate.execute(ipLimitLua, Lists.newArrayList(redisKey), Integer.toString(limit), Integer.toString(timeout));
        // 如果超过限流限制
        if (!acquired) {
            // 抛出异常，然后让全局异常去处理
            assert response != null;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter writer = response.getWriter();) {
                response.getWriter().print("<h1>客官你慢点，请稍后在试一试!!!</h1>");
            } catch (Exception ex) {
                throw new RuntimeException("客官你慢点，请稍后在试一试!!!");
            }
        }
        log.info("限流切面end");
    }
}
