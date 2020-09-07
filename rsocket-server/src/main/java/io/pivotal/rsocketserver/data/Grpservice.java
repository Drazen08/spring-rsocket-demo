package io.pivotal.rsocketserver.data;

import cn.grpcdemo.grpc.lib.HelloReply;
import cn.grpcdemo.grpc.lib.HelloRequest;
import cn.grpcdemo.grpc.lib.SimpleGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author ：sunjx
 * @date ：Created in 2020/9/4 13:51
 * @description：
 */
@GrpcService
public class Grpservice extends SimpleGrpc.SimpleImplBase {

    @Override
    public void sayHello(HelloRequest request, io.grpc.stub.StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello =============> " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
