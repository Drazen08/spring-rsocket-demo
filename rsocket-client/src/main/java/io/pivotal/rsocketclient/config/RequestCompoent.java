//package io.pivotal.rsocketclient.config;
//
//
//import io.rsocket.client.LoadBalancedRSocketMono;
//import io.rsocket.client.filter.RSocketSupplier;
//import io.rsocket.transport.netty.client.TcpClientTransport;
//import lombok.Value;
//import lombok.extern.slf4j.Slf4j;
//import org.reactivestreams.Publisher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
//
//import org.springframework.messaging.rsocket.RSocketRequester;
//import org.springframework.messaging.rsocket.RSocketStrategies;
//import org.springframework.stereotype.Component;
//import org.springframework.util.MimeType;
//
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author ：sunjx
// * @date ：Created in 2020/8/29 16:16
// * {https://github.com/spring-cloud/spring-cloud-commons/issues/736}
// * {https://grapeup.com/blog/reactive-service-to-service-communication-with-rsocket-load-balancing-resumability/}
// */
//@Slf4j
//@Component
//public class RequestCompoent {
//
//    @Autowired
//    private ReactiveDiscoveryClient reactiveDiscoveryClient;
//
//    @Autowired
//    private RSocketStrategies rSocketStrategies;
//
//    private final RSocketRequester.Builder builder = RSocketRequester.builder();
//
//    // application/cbor
//    // message/x.rsocket.composite-metadata.v0
//    private static final MimeType cbor    = new MimeType("application", "cbor");
//    private static final MimeType rsocket = new MimeType("message", "x.rsocket.composite-metadata.v0");
//
//
//    private Map<String, LoadBalancedRSocketMono> lbMap = new ConcurrentHashMap<>(1024);
//
//
//    public RSocketRequester create(String serviceId) {
//        return lbMap.compute(serviceId, (k, v) -> {
//            if (v == null) {
//                return loadBalancedRSocketMono(serviceId);
//            }
//            return v;
//        }).map(r -> {
//                    if (r.isDisposed()) {
//                        throw new RuntimeException("error");
//                    }
//                    return RSocketRequester.wrap(
//                            r,
//                            cbor,
//                            rsocket,
//                            rSocketStrategies);
//                }
//        ).block();
//    }
//
//
//    private LoadBalancedRSocketMono loadBalancedRSocketMono(String serviceId) {
//        List<RSocketSupplier> listMono = reactiveDiscoveryClient
//                .getInstances(serviceId)
//                .map(serviceInstance -> new RSocketSupplier(() ->
//                        this.builder
//                                .rsocketStrategies(rSocketStrategies)
//                                .connect(tcpClientTransport(serviceInstance))
//                                .map(r -> r.rsocket())
//                ))
//                .collectList().block();
//        LoadBalancedRSocketMono balancer = LoadBalancedRSocketMono.create((Publisher) s -> {
//            s.onNext(listMono);
//            s.onComplete();
//        });
//        return balancer;
//    }
//
//    private TcpClientTransport tcpClientTransport(ServiceInstance serviceInstance) {
//        String              host     = serviceInstance.getHost();
//        Map<String, String> metadata = serviceInstance.getMetadata();
//        String              port     = metadata.get("rsocket-port");
//        log.info("service instance = [{}:{}]", host, port);
//        return TcpClientTransport.create(host, Integer.valueOf(port));
//    }
//
//    @Value
//    private static class ConfigData {
//        MimeType mimeType;
//        MimeType metaMimeType;
//    }
//
//    // only needed to get the Requester meta data
//    private void indirectlyCreateConfig(String serviceId) {
//        final RSocketRequester rSocketRequester = reactiveDiscoveryClient
//                .getInstances(serviceId)
//                .next()
//                .map(s -> tcpClientTransport(s))
//                .flatMap(builder::connect)
//                .block();
//
//    }
//
//
//}
