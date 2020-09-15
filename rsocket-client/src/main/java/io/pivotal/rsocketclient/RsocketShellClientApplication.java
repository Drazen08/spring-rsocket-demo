package io.pivotal.rsocketclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"io.application","io.pivotal"})
@EnableDiscoveryClient
@EnableEurekaClient
public class RsocketShellClientApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RsocketShellClientApplication.class, args);
    }
}
