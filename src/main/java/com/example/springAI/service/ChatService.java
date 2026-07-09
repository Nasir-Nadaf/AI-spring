package com.example.springAI.service;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface ChatService {
    @Nullable String chatServe(String query);

    @Nullable Flux<String> chatFluxServe(String query);

    @Nullable String chatMemorySession(String conversationId,String query);

    @Nullable String chatMemorySessionDb(String conversationId, String query);
}
