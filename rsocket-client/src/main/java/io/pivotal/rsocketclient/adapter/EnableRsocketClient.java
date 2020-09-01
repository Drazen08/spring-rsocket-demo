package io.pivotal.rsocketclient.adapter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ：sunjx
 * @date ：Created in 2020/9/1 10:06
 * @description：
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RSocketClientHandler.class)
public @interface EnableRsocketClient {

    String [] packages() default {};

}
