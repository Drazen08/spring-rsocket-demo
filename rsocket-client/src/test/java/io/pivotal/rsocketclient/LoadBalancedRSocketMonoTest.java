///*
// * Copyright 2015-2018 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package io.pivotal.rsocketclient;
//
//import io.rsocket.RSocket;
//import io.rsocket.core.RSocketConnector;
//import io.rsocket.frame.decoder.PayloadDecoder;
//import io.rsocket.transport.netty.client.TcpClientTransport;
//import org.springframework.messaging.rsocket.MetadataExtractor;
//import org.springframework.messaging.rsocket.RSocketRequester;
//import org.springframework.util.MimeTypeUtils;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.net.InetSocketAddress;
//
//public class LoadBalancedRSocketMonoTest {
//    static final int[] PORTS = new int[]{7001};
//
//   static RSocket init(){
//        return RSocketFactory.connect()
//                .mimeType(MetadataExtractor.ROUTE_KEY.toString(), MimeTypeUtils.APPLICATION_JSON_VALUE)
//                .frameDecoder(PayloadDecoder.ZERO_COPY)
//                .transport(TcpClientTransport.create(new InetSocketAddress(7001)))
//                .start()
//                .block();
//
//    }
//    public static void main(String[] args) {
//        RSocket rSocket = RSocketConnector.create()
//                // Enable Zero Copy
//                .dataMimeType(MimeTypeUtils.ALL_VALUE)
//                .payloadDecoder(PayloadDecoder.ZERO_COPY)
//                .connect(TcpClientTransport.create("localhost",7001)).block();
//        RSocketRequester wrap = RSocketRequester.wrap(rSocket, MimeTypeUtils.ALL, MimeTypeUtils.ALL, null);
//        Mono<String>     demo = wrap.route("demo").retrieveMono(String.class);
//        System.out.println(demo.block());
////        Payload demo = rSocket.requestStream(DefaultPayload.create("demo")).retry().blockFirst();
////        System.out.println(demo.getDataUtf8());
//
//
//
////
////        List rsocketSuppliers = Arrays.stream(PORTS)
////                .mapToObj(port -> new RSocketSupplier(() ->
////                        RSocketConnector.create()
////                                // Enable Zero Copy
////                                .payloadDecoder(PayloadDecoder.ZERO_COPY)
////                                .connect(TcpClientTransport.create(port))
//////                        RSocketFactory.connect()
//////                                .transport(TcpClientTransport.create("localhost", port))
//////                                .start()
////
////                        )
////
////                )
////                .collect(Collectors.toList());
////
////        LoadBalancedRSocketMono balancer = LoadBalancedRSocketMono.create((Publisher) s -> {
////            s.onNext(rsocketSuppliers);
////            s.onComplete();
////        });
////
////        Flux.range(0, 2)
////                .flatMap(i -> balancer)
////                .doOnNext(rSocket -> {
////                    Flux<Payload> demo = rSocket.requestStream(DefaultPayload.create("demo")).retry();
////                    demo.doOnNext(d-> System.out.println(d.getDataUtf8())).blockLast();
////                }).blockLast();
//
//    }
//
//}
