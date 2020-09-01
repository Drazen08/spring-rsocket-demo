package io.pivotal.rsocketclient.adapter;

import io.netty.util.internal.StringUtil;
import io.pivotal.rsocketclient.config.RequestCompoent;
import io.pivotal.rsocketclient.util.MethodFinderUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ServiceLoader;

/**
 * @author ：sunjx
 * @date ：Created in 2020/8/31 16:46
 * @description：
 */
@Component
@Aspect
@Slf4j
public class RSocketAspect {

    @Autowired
    private RequestCompoent requestCompoent;


    //    @Pointcut("@annotation(io.pivotal.rsocketclient.adapter.RSocketMethod)")
    @Pointcut("execution( * io.pivotal.rsocketclient.client85.*.*(..))")
    public void cut() {
    }

    @Around("cut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Signature     signature         = pjp.getSignature();
        Class         declaringType     = signature.getDeclaringType();
        String        name              = signature.getName();
        Object[] args = pjp.getArgs();

        // 获取interface
        Class<?>      iRsocketClient = RSocketClientHandler.findIRsocketClient(declaringType);
        RSocketServer anno           = iRsocketClient.getAnnotation(RSocketServer.class);
        String        serviceId      = anno.serviceId();
        Method lastMethod = MethodFinderUtil.getLastMethod(iRsocketClient, name);
        if (lastMethod != null) {
            RSocketRequester requester = requestCompoent.create(serviceId);
            RSocketMethod   mm        = lastMethod.getAnnotation(RSocketMethod.class);
            String         value     = mm.value();
            if (!StringUtil.isNullOrEmpty(value)) {

                Mono<?> mono = requester.route(value)
                        .data(args.length > 0?args[0]:null)
                        .retrieveMono(lastMethod.getReturnType())
                        .retryBackoff(1, Duration.ofMillis(100));

                return mono.block();
            }
        }

        log.info("[rSocket] 耗时 : {} ms", (System.currentTimeMillis() - startTime));
        return pjp.proceed();
    }


}
