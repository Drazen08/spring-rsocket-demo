package io.pivotal.rsocketclient.controller;

import cn.grpcdemo.grpc.lib.HelloReply;
import cn.grpcdemo.grpc.lib.HelloRequest;
import cn.grpcdemo.grpc.lib.SimpleGrpc;
import io.application.grpc.client.SimpleRpcClient;
import io.pivotal.rsocketclient.rpcclient.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author ：sunjx
 * @date ：Created in 2020/9/4 14:22
 * @description：
 */
@RestController
public class GrpcController {

    @Autowired
    private GrpcClientService grpcClientService;

    @Autowired
    private SimpleRpcClient simpleRpcClient;

    @GetMapping("/grpc")
    public String get() {
        return grpcClientService.sendMessage(UUID.randomUUID().toString());
    }

    @GetMapping("/clientGrpc")
    public String getget(){
        SimpleGrpc.SimpleBlockingStub stub = simpleRpcClient.getService();
        HelloRequest build = HelloRequest.newBuilder().setName(UUID.randomUUID().toString()).build();
        HelloReply response = stub.sayHello(build);
        return response.getMessage();
    }

}
