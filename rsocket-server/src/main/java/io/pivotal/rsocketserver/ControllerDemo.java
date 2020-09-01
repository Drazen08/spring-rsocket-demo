package io.pivotal.rsocketserver;

import com.ido85.client.Message;
import io.pivotal.rsocketserver.service.IDemoService;
import io.rsocket.RSocket;
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
    public Mono<String> sayHi() {
        return Mono.just(iDemoService.sayHi() + String.format("port :[%s]", environment.getProperty("local.rsocket.server.port")));
    }

    @MessageMapping("clientDemo")
    public Mono<String> clientDemo() {
        return Mono.just(iDemoService.sayHi() + String.format("client85 demo port :[%s]", environment.getProperty("local.rsocket.server.port")));
    }

    @MessageMapping("messageCheck")
    public Mono<Message> msgChecker(Message message) {
        message.setChecked(iDemoService.sayHi() + String.format("client85 demo port :[%s]", environment.getProperty("local.rsocket.server.port")));
        return Mono.just(message);
    }

}
