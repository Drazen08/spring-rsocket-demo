package io.application.grpc.adapter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ：sunjx
 * @date ：Created in 2020/8/31 17:48
 * @description：
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RSocketMethod {
    
    String value() default "";
    
}
