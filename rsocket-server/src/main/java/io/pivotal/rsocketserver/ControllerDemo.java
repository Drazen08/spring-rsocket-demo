package io.pivotal.rsocketserver;

import io.pivotal.rsocketserver.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

/**
 * @author ：sunjx
 * @date ：Created in 2020/8/28 14:47
 * @description：
 */
@Controller
@RequestMapping("my")
public class ControllerDemo {

    @Autowired
    private Environment environment;

    @Autowired
    private IDemoService iDemoService;

    @MessageMapping("demo")
    public Mono<String> sayHi(){
        return Mono.just(iDemoService.sayHi() + String.format("port :[%s]",environment.getProperty("local.rsocket.server.port") ));
    }

}
