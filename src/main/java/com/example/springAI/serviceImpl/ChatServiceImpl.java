package com.example.springAI.serviceImpl;

import com.example.springAI.service.ChatService;
import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatClient chatClient;
    @Autowired
    private ChatMemory chatMemory;
    @Autowired
    private JdbcChatMemoryRepository jdbcChatMemoryRepository;

    @Override
    public String chatServe(String query) {
        return chatClient.prompt().system("you are coding assistent")
                .user(query).call().content();
    }

    @Override
    public @Nullable Flux<String> chatFluxServe(String query) {
        return chatClient
                .prompt()
                .system("you are tutor for coding language java only answer java questions only no other question")
                .user(query)
                .stream().content();

    }

    @Override
    public String chatMemorySession(String conversationId, String query) {

        MessageChatMemoryAdvisor advisor =
                MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chatClient.prompt()
                .advisors(a -> a
                        .advisors(advisor)
                        .param(ChatMemory.CONVERSATION_ID, conversationId))
                .system("You are a friendly assistant. Give answers.")
                .user(query)
                .call()
                .content();
    }

    @Override
    public @Nullable String chatMemorySessionDb(String conversationId, String query) {
        MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory
                .builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .build();
        MessageChatMemoryAdvisor advisor =
                MessageChatMemoryAdvisor.builder(messageWindowChatMemory).build();
        return chatClient.prompt()
                .advisors(a -> a
                        .advisors(advisor)
                        .param(ChatMemory.CONVERSATION_ID, conversationId))
                .system("You are a friendly assistant. Give answers.")
                .user(query)
                .call()
                .content();
    }
}
