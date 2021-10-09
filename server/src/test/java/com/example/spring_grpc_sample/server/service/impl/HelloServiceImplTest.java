package com.example.spring_grpc_sample.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloServiceImplTest {

    @Autowired private HelloServiceImpl helloService;

    @Test
    public void testSayHelloVerifyRequest() throws Exception {
        String expectName = "Foo";
        HelloRequest request = HelloRequest.newBuilder().setName(expectName).build();
        @SuppressWarnings("unchecked")
        StreamObserver<HelloReply> responseObserverMock = mock(StreamObserver.class);

        helloService.sayHello(request, responseObserverMock);

        ArgumentCaptor<HelloReply> replyArgumentCaptor = ArgumentCaptor.forClass(HelloReply.class);
        verify(responseObserverMock).onNext(replyArgumentCaptor.capture());
        HelloReply actualReply = replyArgumentCaptor.getValue();
        assertThat(actualReply.getMessage()).contains("Hello").contains(expectName);

        verify(responseObserverMock).onCompleted();
    }
}
