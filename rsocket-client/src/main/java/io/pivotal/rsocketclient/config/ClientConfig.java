package io.pivotal.rsocketclient.config;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

import java.net.InetSocketAddress;

/**
 * @author ：sunjx
 * @date ：Created in 2020/8/28 14:51
 * @description：
 */
@Configuration
public class ClientConfig {

//    @Bean
//    public RSocket rSocket() {
//        return RSocketFactory.connect()
//                .mimeType(MetadataExtractor.ROUTE_KEY.toString(), MimeTypeUtils.APPLICATION_JSON_VALUE)
//                .frameDecoder(PayloadDecoder.ZERO_COPY)
//                .transport(TcpClientTransport.create(new InetSocketAddress(7000)))
//                .start()
//                .block();
//    }

    @Bean
    RSocketRequester rSocketRequester(RSocketStrategies rSocketStrategies) {

        InetSocketAddress address = new InetSocketAddress("localhost",7000);
        return RSocketRequester.builder()
                .rsocketFactory(factory -> factory
                        .dataMimeType(MimeTypeUtils.ALL_VALUE)
                        .frameDecoder(PayloadDecoder.ZERO_COPY))
                .rsocketStrategies(rSocketStrategies)
                .connect(TcpClientTransport.create(address))
                .retry().block();
    }

}
