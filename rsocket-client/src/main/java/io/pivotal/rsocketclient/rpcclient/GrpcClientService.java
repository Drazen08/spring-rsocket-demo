package io.pivotal.rsocketclient.rpcclient;

import cn.grpcdemo.grpc.lib.HelloReply;
import cn.grpcdemo.grpc.lib.HelloRequest;
import cn.grpcdemo.grpc.lib.SimpleGrpc;
import io.grpc.Channel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author ：sunjx
 * @date ：Created in 2020/9/4 11:36
 * @description：
 */
@Service
public class GrpcClientService {

    @GrpcClient("rsocket-server")
    private Channel serverChannel;

    public String sendMessage(String name) {
        try {

            SimpleGrpc.SimpleBlockingStub stub = SimpleGrpc.newBlockingStub(serverChannel);
            HelloReply response = stub.sayHello(HelloRequest.newBuilder().setName(name).build());
            return response.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
