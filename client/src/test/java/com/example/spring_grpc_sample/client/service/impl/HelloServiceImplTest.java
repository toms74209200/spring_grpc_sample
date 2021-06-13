package com.example.spring_grpc_sample.client.service.impl;

import com.example.spring_grpc_sample.client.service.HelloService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class HelloServiceImplTest {

    private static final int SERVER_PORT = 8080;

    @Autowired
    private HelloService helloService;

    private Server serverMock;

    @AfterEach
    public void tearDown() throws Exception {
        serverMock.shutdown();
        serverMock.awaitTermination();
    }

    @Test
    public void testReceiveGreetingSuccess() throws Exception {
        GreeterGrpc.GreeterImplBase greeterMock = new GreeterGrpc.GreeterImplBase() {
            @Override
            public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
                HelloReply reply = HelloReply.newBuilder()
                        .setMessage(request.getName())
                        .build();
                responseObserver.onNext(reply);
                responseObserver.onCompleted();
            }
        };

        startServer(greeterMock);

        Thread.sleep(1000);

        String expectName = "test";
        String actualReply = helloService.receiveGreeting(expectName).orElse(null);

        if (actualReply == null) {
            fail();
        }
        assertThat(actualReply).isEqualTo(expectName);
    }

    @Test
    public void testReceiveGreetingNoReply() throws Exception {
        GreeterGrpc.GreeterImplBase greeterMock = new GreeterGrpc.GreeterImplBase() {
            @Override
            public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {}
        };

        startServer(greeterMock);

        assertThrows(
                Exception.class,
                () -> helloService.receiveGreeting("test")
        );
    }

    private void startServer(GreeterGrpc.GreeterImplBase greeter) throws IOException {
        serverMock = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(greeter)
                .build();
        serverMock.start();
    }

}
