package io.pivotal.rsocketserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 *  springcloud rsocket lb = {https://github.com/spring-cloud/spring-cloud-commons/issues/736}
 *                           {https://github.com/rsocket-routing}
 */
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class RsocketServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsocketServerApplication.class, args);
    }

}