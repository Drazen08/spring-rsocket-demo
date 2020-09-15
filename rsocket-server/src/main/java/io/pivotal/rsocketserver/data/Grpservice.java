package io.pivotal.rsocketserver.data;

import cn.grpcdemo.grpc.lib.HelloReply;
import cn.grpcdemo.grpc.lib.HelloRequest;
import cn.grpcdemo.grpc.lib.SimpleGrpc;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author ：sunjx
 * @date ：Created in 2020/9/4 13:51
 * @description：
 */
@GrpcService
public class Grpservice extends SimpleGrpc.SimpleImplBase {

    @Value("${server.port}")
    int port;

    /**
     *
     * @param request
     * @param responseObserver
     * @throws {@link Status}
     */
    @Override
    public void sayHello(HelloRequest request, io.grpc.stub.StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello =============> " + request.getName() + port).build();
//        if (1 == 1) {
//            Metadata metadata = new Metadata();
////            metadata.
//            responseObserver.onError(new StatusRuntimeException(Status.DEADLINE_EXCEEDED,metadata));
//        }
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
