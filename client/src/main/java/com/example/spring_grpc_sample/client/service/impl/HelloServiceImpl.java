package com.example.spring_grpc_sample.client.service.impl;

import com.example.spring_grpc_sample.client.service.HelloService;
import io.grpc.examples.helloworld.GreeterGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class HelloServiceImpl implements HelloService {

    @GrpcClient("HelloService")
    private GreeterGrpc.GreeterBlockingStub helloGreeterStub;

    @Override
    public Optional<String> receiveGreeting(String name) {
        return Optional.empty();
    }
}
