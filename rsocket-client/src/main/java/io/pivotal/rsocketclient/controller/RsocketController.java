package io.pivotal.rsocketclient.controller;

import io.pivotal.rsocketclient.client85.DemoClient;
import io.pivotal.rsocketclient.config.RequestCompoent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author ：sunjx
 * @date ：Created in 2020/8/26 11:42
 * @description：
 */
@RestController
public class RsocketController {


    @Autowired
    private RequestCompoent requestCompoent;

    @Autowired
    private DemoClient demoClient;

//    @GetMapping("/getget")
//    public Mono<String> getRsocketResponse() throws InterruptedException {
//        return rSocketRequester
//                .route("demo")
////                .data(new MarketDataRequest(stock))
//                .retrieveMono(String.class);
//    }

    @GetMapping("/getEurekaService")
    public Mono<String> getEurekaService() {
        RSocketRequester rSocketRequesterMono = requestCompoent.create("rsocket-server");
        return rSocketRequesterMono.route("demo").retrieveMono(String.class)
                .retryBackoff(1, Duration.ofMillis(100));
    }

    @GetMapping("/rsocketClient")
    public Mono<String> rsocketClient() {
        return Mono.just(demoClient.clientDemo());
    }

}
