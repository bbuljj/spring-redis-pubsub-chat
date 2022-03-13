package com.example.chatroom.aggregate;

import com.example.chatroom.config.RedisMessageSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void sendMessage(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }
}
