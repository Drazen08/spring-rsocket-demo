package io.application.grpc.client;

import cn.grpcdemo.grpc.lib.SimpleGrpc;
import io.application.grpc.adapter.AbstractGrpcClient;
import io.application.grpc.adapter.annotation.GrpcServer;
import org.springframework.stereotype.Service;

/**
 * @author ：sunjx
 * @date ：Created in 2020/9/15 11:27
 * @description：
 */
@Service
public class SimpleRpcClient extends AbstractGrpcClient<SimpleGrpc.SimpleBlockingStub> {


    @Override
    protected String rpcName() {
        return "rsocket-server";
    }

    @Override
    protected Class<SimpleGrpc.SimpleBlockingStub> getRpcStubClass() {
        return SimpleGrpc.SimpleBlockingStub.class;
    }
}
