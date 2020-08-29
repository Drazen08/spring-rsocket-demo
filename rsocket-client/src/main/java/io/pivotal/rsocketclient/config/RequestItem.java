//package io.pivotal.rsocketclient.config;
//
//import io.rsocket.RSocketFactory;
//import io.rsocket.client.LoadBalancedRSocketMono;
//import io.rsocket.client.filter.RSocketSupplier;
//import io.rsocket.frame.decoder.PayloadDecoder;
//import io.rsocket.metadata.WellKnownMimeType;
//import io.rsocket.transport.netty.client.TcpClientTransport;
//import lombok.Data;
//import org.springframework.http.MediaType;
//import org.springframework.messaging.rsocket.RSocketRequester;
//import org.springframework.util.MimeTypeUtils;
//import reactor.core.publisher.Flux;
//
//import java.util.Collections;
//import java.util.List;
//
///**
// * @author ：sunjx
// * @date ：Created in 2020/8/29 16:52
// * @description：
// */
//@Data
//public class RequestItem {
//
//    private final String                  appName;
//    private       RSocketRequester        requester;
//    private final LoadBalancedRSocketMono balancer;
//    private       List                    rsockets;
//
//    public RequestItem(String appName) {
//        this.appName = appName;
//
//        this.balancer = LoadBalancedRSocketMono.create(Flux.just(Collections.singleton(new RSocketSupplier(() ->
//                        RSocketFactory
//                                .connect()
//                                .dataMimeType(MediaType.APPLICATION_CBOR_VALUE)
//                                .metadataMimeType(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString())
//                                .frameDecoder(PayloadDecoder.ZERO_COPY)
//                                .transport(TcpClientTransport.create("localhost", 7000))
//                                .start()
//                                .doOnNext(rSocket -> {
//                                    this.requester = RSocketRequester.wrap(rSocket, MediaType.APPLICATION_CBOR, MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString()), rSocketStrategies);
//                                })
//                )))
//        ).subscribe();
//    }
//}
