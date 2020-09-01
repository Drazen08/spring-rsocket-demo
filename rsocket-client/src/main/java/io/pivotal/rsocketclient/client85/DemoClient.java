package io.pivotal.rsocketclient.client85;

import io.pivotal.rsocketclient.adapter.RSocketMethod;
import io.pivotal.rsocketclient.adapter.RSocketServer;
import io.pivotal.rsocketclient.data.Message;


/**
 * @author ：sunjx
 * @date ：Created in 2020/8/31 16:40
 * @description：
 */
@RSocketServer(serviceId = "rsocket-server")
public interface DemoClient {

    @RSocketMethod("clientDemo")
    public String clientDemo();


    @RSocketMethod("messageCheck")
    public Message msgChecker(Message message);

}
