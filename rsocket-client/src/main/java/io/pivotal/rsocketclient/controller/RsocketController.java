package io.pivotal.rsocketclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author ：sunjx
 * @date ：Created in 2020/8/26 11:42
 * @description：
 */
@RestController
public class RsocketController {

    @Autowired
    private RSocketRequester rSocketRequester;


    @GetMapping("/getget")
    public Mono<String> getRsocketResponse() throws InterruptedException {
        return rSocketRequester
                .route("demo")
//                .data(new MarketDataRequest(stock))
                .retrieveMono(String.class);
    }

}
