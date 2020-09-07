//package io.pivotal.rsocketserver;
//
//import io.grpc.Metadata;
//import io.grpc.ServerCall;
//import io.grpc.ServerCallHandler;
//import io.grpc.ServerInterceptor;
//
//
///**
// * @author ：sunjx
// * @date ：Created in 2020/9/7 18:00
// * @description：
// */
//public class ExceptionInterceptor implements ServerInterceptor {
//
//
//
//    @Override
//    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
//        ServerCall.Listener<ReqT> reqTListener = next.startCall(call, headers);
//        return new ExceptionListener(reqTListener, call);
//    }
//
//    class ExceptionListener extends ServerCall.Listener {
//        public void onHalfClose() {
//            try {
//                this.delegate.onHalfClose();
//            } catch (Exception t) {
//                // 统一处理异常
//                ExtendedStatusRuntimeException exception = fromThrowable(t);
//                // 调用 call.close() 发送 Status 和 metadata
//                // 这个方式和 onError()本质是一样的
//                call.close(exception.getStatus(), exception.getTrailers());
//            }
//        }
//    }
//}
