package io.pivotal.rsocketclient.controller;

import io.pivotal.rsocketclient.rpcclient.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author ：sunjx
 * @date ：Created in 2020/9/4 14:22
 * @description：
 */
@RestController
public class GrpcController {

    @Autowired
    private GrpcClientService grpcClientService;

    @GetMapping("/grpc")
    public String get() {
        return grpcClientService.sendMessage(UUID.randomUUID().toString());
    }

}
