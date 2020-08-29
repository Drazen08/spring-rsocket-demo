package io.pivotal.rsocketclient.config;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.client.LoadBalancedRSocketMono;
import io.rsocket.client.filter.RSocketSupplier;
import io.rsocket.core.RSocketConnector;
import io.rsocket.core.Resume;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.TcpClientTransport;
import lombok.Value;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author ：sunjx
 * @date ：Created in 2020/8/29 16:16
 * {https://github.com/spring-cloud/spring-cloud-commons/issues/736}
 */
@Component
public class RequestCompoent {

    @Autowired
    private RSocketStrategies rSocketStrategies;

    private List rsockets;

//    private LoadBalancedRSocketMono balancer;

    static final int[] PORTS = new int[]{7001};

    private final RSocketRequester.Builder builder = RSocketRequester.builder();


    @Autowired
    private ReactiveDiscoveryClient reactiveDiscoveryClient;


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

//    public RSocketRequester getRequest(String appName) {
//        RSocket rSocket = balancer.retry().block();
//
//
//        RSocketRequester wrap = RSocketRequester.wrap(rSocket, MimeTypeUtils.APPLICATION_JSON, MimeTypeUtils.APPLICATION_JSON, rSocketStrategies);
//        return wrap;
//    }

    public Mono<RSocketRequester> create(String serviceId) {
        indirectlyCreateConfig(serviceId);

        return loadBalancedRSocketMono(serviceId).map(balancedRSocket -> RSocketRequester.wrap(
                balancedRSocket,
                MimeTypeUtils.APPLICATION_JSON,
                MimeTypeUtils.APPLICATION_JSON,
                rSocketStrategies
        ));
    }


    // only needed to get the Requester meta data
    private void indirectlyCreateConfig(String serviceId) {
        final RSocketRequester rSocketRequester = reactiveDiscoveryClient
                .getInstances(serviceId)
                .next()
                .map(s->tcpClientTransport(s))
                .flatMap(builder::connect)
                .block();

    }



    private LoadBalancedRSocketMono loadBalancedRSocketMono(String serviceId) {
        return reactiveDiscoveryClient
                        .getInstances(serviceId)
                        .map(serviceInstance -> new RSocketSupplier(() -> builder
                                .connect(tcpClientTransport(serviceInstance))
                                .retry()
                                .map(RSocketRequester::rsocket)
                        ))
                        .collectList()
                        .as(LoadBalancedRSocketMono::create);
    }

    private TcpClientTransport tcpClientTransport(ServiceInstance serviceInstance) {
        String host = serviceInstance.getHost();
        Map<String, String> metadata = serviceInstance.getMetadata();
        String port = metadata.get("rsocket-port");
        return TcpClientTransport.create(host,Integer.valueOf(port));
    }

    @Value
    private static class ConfigData {
        MimeType mimeType;
        MimeType metaMimeType;
    }

}
