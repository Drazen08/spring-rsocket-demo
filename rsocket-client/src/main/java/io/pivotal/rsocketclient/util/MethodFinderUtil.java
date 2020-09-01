package io.pivotal.rsocketclient.util;

import java.lang.reflect.Method;

/**
 * @author ：sunjx
 * @date ：Created in 2020/9/1 10:54
 * @description：
 */
public class MethodFinderUtil {
    private MethodFinderUtil() {
    }

    public static Method getLastMethod(Class<?> type, String methodName) {
        Method[] methods = type.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

}
