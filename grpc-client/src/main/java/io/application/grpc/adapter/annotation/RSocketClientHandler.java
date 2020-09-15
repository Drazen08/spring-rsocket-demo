package io.application.grpc.adapter.annotation;

import io.application.grpc.util.ClassFinderUtil;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author ：sunjx
 * @date ：Created in 2020/8/31 17:54
 * @description：
 */
public class RSocketClientHandler implements ImportSelector {

    /**
     * k class impl
     * v interface
     */
    private final static Map<Class<?>, Class<?>> clientHash = new ConcurrentHashMap<>(1024);

    public static Class<?> findIRsocketClient(Class<?> implClassName){
        return clientHash.getOrDefault(implClassName,null);
    }


    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        //读取DemoScan的属性
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(
                annotationMetadata.getAnnotationAttributes(EnableGrpc.class.getName()));
        if (annoAttrs != null) {
            String[] packages = annoAttrs.getStringArray("packages");
            if (packages != null && packages.length > 0) {
                for (String aPackage : packages) {
                    Set<Class<?>> classesWithAnno = ClassFinderUtil.getClassesWithAnno(aPackage, GrpcServer.class);
                    if (classesWithAnno != null && classesWithAnno.size() > 0) {
                        for (Class<?> aClass : classesWithAnno) {
                            // 拿到的是实现类
                            ServiceLoader m = ServiceLoader.load(aClass);
                            for (Object o : m) {
                                final String name = o.getClass().getName();
                                clientHash.put(o.getClass(),aClass);
                            }
                        }
                    }
                }
            }
        }

        return new String[0];
    }
}
