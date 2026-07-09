package com.example.springAI.controller;

import com.example.springAI.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MyChatController {

    @Autowired
    private ChatService service;



    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam("q") String query ){
        return ResponseEntity.ok(service.chatServe(query));
    }

    @GetMapping("/chat/flux")
    public ResponseEntity<Flux<String>> chatFlux(@RequestParam("q") String query ){
        return ResponseEntity.ok(service.chatFluxServe(query));
    }

    @GetMapping("/chat/memory")
    public ResponseEntity<String> chatMemorySession(
            @RequestParam("conversationId") String conversationId,
            @RequestParam("q") String query) {

        return ResponseEntity.ok(
                service.chatMemorySession(conversationId, query)
        );
    }

    @GetMapping("/chat/memory/db")
    public ResponseEntity<String> chatMemorySessionDb(
            @RequestParam("conversationId") String conversationId,
            @RequestParam("q") String query) {

        return ResponseEntity.ok(
                service.chatMemorySessionDb(conversationId, query)
        );
    }

}
