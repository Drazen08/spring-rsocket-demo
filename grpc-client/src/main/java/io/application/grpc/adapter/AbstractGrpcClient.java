package io.application.grpc.adapter;

import cn.grpcdemo.grpc.lib.SimpleGrpc;
import io.application.grpc.util.InstanceFactory;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.AbstractBlockingStub;
import io.grpc.stub.AbstractStub;

/**
 * @author ：sunjx
 * @date ：Created in 2020/9/15 11:20
 * @description：
 */
public abstract class AbstractGrpcClient<T extends AbstractStub<T>> implements IGrpc<T> {

    private ManagedChannel channel;
    private T service;

    public AbstractGrpcClient() {
        this.channel = ManagedChannelBuilder.forTarget(rpcName()).build();
    }


    @Override
    public T getService() {
        String name = this.getClass().getName();

        if(service == null){
            SimpleGrpc.SimpleStub instance = InstanceFactory.getInstance(SimpleGrpc.SimpleStub.class);
            service = (T) instance;
            System.out.println(instance.toString());
        }
        return this.service;
    }

    protected abstract String rpcName();

    protected abstract Class<T> getRpcStubClass();


}
