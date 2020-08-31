package io.pivotal.rsocketclient.adapter;

import io.pivotal.rsocketclient.config.RequestCompoent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.pivotal.rsocketclient.client85.DemoClient;
import java.lang.annotation.Annotation;
import java.util.ServiceLoader;

/**
 * @author ：sunjx
 * @date ：Created in 2020/8/31 16:46
 * @description：
 */
@Slf4j
@Component
@Aspect
public class RSocketAspect {

    @Autowired
    private RequestCompoent requestCompoent;

    @Pointcut("execution( * io.pivotal.rsocketclient.client85.*.*(..))")
    public void cut() {
    }

    @Around("cut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long   startTime = System.currentTimeMillis();
        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        Class declaringType = signature.getDeclaringType();
        String declaringTypeName = signature.getDeclaringTypeName();
        int modifiers = signature.getModifiers();
        Annotation annotation = declaringType.getAnnotation(RSocketServer.class);
        String name = signature.getName();
        String kind = pjp.getKind();
        ServiceLoader loader = ServiceLoader.load(declaringType);

//        declaringType.getMethod(signature.getName(),signature.getDeclaringTypeName())
        log.info("[rSocket] 耗时 : {} ms", (System.currentTimeMillis() - startTime));
        return null;
    }



}
