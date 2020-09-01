package io.pivotal.rsocketclient.client85.impl;

import com.google.auto.service.AutoService;
import com.ido85.client.Message;
import io.pivotal.rsocketclient.client85.DemoClient;
import org.springframework.stereotype.Service;

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
