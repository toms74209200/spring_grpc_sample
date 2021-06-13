package com.example.spring_grpc_sample.client.service;

import java.util.Optional;

public interface HelloService {

    Optional<String> receiveGreeting(String name);
}
