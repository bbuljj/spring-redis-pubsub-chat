package com.example.chatroom.config;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RedisMessageSubscriber implements MessageListener {
    public static List<String> messageList = new ArrayList<>();
    @Autowired
    private SimpMessageSendingOperations operations;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void onMessage(Message message, byte[] pattern) {
        messageList.add(message.toString());
        operations.convertAndSend("/chat-result", Map.of(
                "message", message.toString()
        ));

        System.out.println("Message received: " + message.toString());
    }
}