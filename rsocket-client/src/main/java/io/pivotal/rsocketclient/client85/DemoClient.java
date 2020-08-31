package io.pivotal.rsocketclient.client85;

import io.pivotal.rsocketclient.adapter.RSocketMethod;
import io.pivotal.rsocketclient.adapter.RSocketServer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import reactor.core.publisher.Mono;

/**
 * @author ：sunjx
 * @date ：Created in 2020/8/31 16:40
 * @description：
 */
@RSocketServer(serviceId = "rsocket-server")
public interface DemoClient {

    @RSocketMethod("clientDemo")
    public Mono<String> clientDemo();

}
