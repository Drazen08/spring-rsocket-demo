package io.pivotal.rsocketclient.adapter;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import io.pivotal.rsocketclient.client85.DemoClient;
/**
 * @author ：sunjx
 * @date ：Created in 2020/8/31 17:54
 * @description：
 */
@Component
public class RSocketClientHandler {

    private final Map<String,Class<?>> clientHash = new ConcurrentHashMap<>(1024);

    public static void main(String[] args) {
        ServiceLoader loader = ServiceLoader.load(DemoClient.class);
        ServiceLoader m = ServiceLoader.load(RSocketServer.class);

        for (Object s : loader) {
            System.out.println(s.getClass());
        }

        System.out.println(m.toString());

    }


}
