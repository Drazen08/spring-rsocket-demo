package io.pivotal.rsocketclient.client85.impl;

import com.google.auto.service.AutoService;
import io.pivotal.rsocketclient.client85.DemoClient;
import io.pivotal.rsocketclient.data.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author ：sunjx
 * @date ：Created in 2020/8/31 16:49
 * @description：
 */
@Service
@AutoService(DemoClient.class)
public class DemoClientFailback implements DemoClient {
    @Override
    public String clientDemo() {
        return "fail";
    }

    @Override
    public Message msgChecker(Message message) {
        return null;
    }
}
