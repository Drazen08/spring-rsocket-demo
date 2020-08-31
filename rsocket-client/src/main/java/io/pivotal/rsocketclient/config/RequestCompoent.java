package io.pivotal.rsocketclient.config;


import io.rsocket.RSocket;
import io.rsocket.client.LoadBalancedRSocketMono;
import io.rsocket.client.filter.RSocketSupplier;
import io.rsocket.core.RSocketConnector;
import io.rsocket.core.Resume;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.TcpClientTransport;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：sunjx
 * @date ：Created in 2020/8/29 16:16
 * {https://github.com/spring-cloud/spring-cloud-commons/issues/736}
 * {https://grapeup.com/blog/reactive-service-to-service-communication-with-rsocket-load-balancing-resumability/}
 */
@Slf4j
@Component
public class RequestCompoent {

    @Autowired
    private RSocketStrategies rSocketStrategies;

    private List rsockets;

//    private LoadBalancedRSocketMono balancer;


    private final RSocketRequester.Builder builder = RSocketRequester.builder();
    // application/cbor
    // message/x.rsocket.composite-metadata.v0
    private static final MimeType cbor = new MimeType("application","cbor");
    private static final MimeType rsocket = new MimeType("message","x.rsocket.composite-metadata.v0");

    @Autowired
    private ReactiveDiscoveryClient reactiveDiscoveryClient;

    Map<String, LoadBalancedRSocketMono> lbMap = new ConcurrentHashMap<>(1024);

    @PostConstruct
    public void init() {
//        this.rsockets = Arrays.stream(PORTS)
//                .mapToObj(port -> new RSocketSupplier(() ->
//                                RSocketConnector.create()
//                                        .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
//                                        .metadataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
//                                        // Enable Zero Copy
////                                        .payloadDecoder(PayloadDecoder.ZERO_COPY)
//                                        .connect(TcpClientTransport.create(port))
//                        )
//
//                ).collect(Collectors.toList());
//
//        this.balancer = LoadBalancedRSocketMono.create((Publisher) s -> {
//
//            s.onNext(this.rsockets);
//            s.onComplete();
//        });
    }

    public Mono<RSocketRequester> create(String serviceId) {
        LoadBalancedRSocketMono lb = lbMap.compute(serviceId, (k, v) -> {
            if (v == null) {
                return loadBalancedRSocketMono(serviceId);
            }
            return v;
        });

        return lb.map(r -> RSocketRequester.wrap(
                r,
                cbor,
                rsocket,
                rSocketStrategies));

//        RSocketRequester       r                =  pureRequest(serviceId);
//        return Mono.just(r);
//        return Mono.just(RSocketRequester.wrap(
//                block,
//                cbor,
//                rsocket,
//                rSocketStrategies));
    }

    public RSocketRequester pureRequest(String serviceId) {
        Flux<ServiceInstance> instances = reactiveDiscoveryClient
                .getInstances(serviceId);

                return RSocketRequester.builder()
                .rsocketFactory(factory -> factory
                        .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .metadataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .frameDecoder(PayloadDecoder.ZERO_COPY))
                .rsocketStrategies(rSocketStrategies)
                .connect(tcpClientTransport(instances.blockFirst()))
                .retry().block();
    }


    private LoadBalancedRSocketMono loadBalancedRSocketMono(String serviceId) {
        LoadBalancedRSocketMono as = reactiveDiscoveryClient
                .getInstances(serviceId)
                .map(serviceInstance -> new RSocketSupplier(() ->
                        this.builder
                                .rsocketStrategies(rSocketStrategies)
                                .connect(tcpClientTransport(serviceInstance)).retry().
                                map(r->r.rsocket())
                ))
                .collectList()
                .as(l -> LoadBalancedRSocketMono.create((Publisher<Collection<RSocketSupplier>>) s -> {
                    s.onNext(l.block());
                    s.onComplete();
                }));

        return as;
    }

    private TcpClientTransport tcpClientTransport(ServiceInstance serviceInstance) {
        String              host     = serviceInstance.getHost();
        Map<String, String> metadata = serviceInstance.getMetadata();
        String              port     = metadata.get("rsocket-port");
        log.info("service instance = [{}:{}]", host, port);
        return TcpClientTransport.create(host, Integer.valueOf(port));
    }

    @Value
    private static class ConfigData {
        MimeType mimeType;
        MimeType metaMimeType;
    }

    // only needed to get the Requester meta data
    private void indirectlyCreateConfig(String serviceId) {
        final RSocketRequester rSocketRequester = reactiveDiscoveryClient
                .getInstances(serviceId)
                .next()
                .map(s -> tcpClientTransport(s))
                .flatMap(builder::connect)
                .block();

    }

}
