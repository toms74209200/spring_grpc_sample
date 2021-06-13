package com.example.spring_grpc_sample.client.service.impl;

import com.example.spring_grpc_sample.client.service.HelloService;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class HelloServiceImpl implements HelloService {

    @GrpcClient("HelloService")
    private GreeterGrpc.GreeterBlockingStub helloGreeterStub;

    @Override
    public Optional<String> receiveGreeting(String name) {
        log.info("name: {}", name);
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();
        HelloReply reply = helloGreeterStub.withDeadlineAfter(1, TimeUnit.SECONDS).sayHello(request);
        String message = reply.getMessage();
        log.info("message: {}", message);
        return Optional.ofNullable(message);
    }
}
