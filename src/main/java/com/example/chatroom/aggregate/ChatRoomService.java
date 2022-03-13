package com.example.chatroom.aggregate;

import com.example.chatroom.config.RedisMessageSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisMessageSubscriber subscriber;
    private final RedisMessageListenerContainer redisMessageListener;
    private static List<UUID> rooms = new ArrayList<>();

    public UUID createRoom() {
        var roomId = UUID.randomUUID();
        var channel = new ChannelTopic("test");
        redisMessageListener.addMessageListener(subscriber, channel);
        rooms.add(roomId);

        return roomId;
    }

    public List<UUID> findAllRooms() {
        return rooms;
    }
}
