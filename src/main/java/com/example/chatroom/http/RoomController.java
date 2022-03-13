package com.example.chatroom.http;

import com.example.chatroom.aggregate.ChatRoomService;
import com.example.chatroom.aggregate.ChatSendCommand;
import com.example.chatroom.aggregate.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final ChatRoomService service;
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<Object> create() {
        var roomId = service.createRoom();
        return ResponseEntity.status(HttpStatus.CREATED).body(roomId);
    }

    @GetMapping
    public ResponseEntity<Object> list() {
        var rooms = service.findAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/{id}/chats")
    public ResponseEntity<Object> send(@PathVariable UUID id, @RequestBody ChatSendCommand command) {
        chatService.sendMessage(id.toString(), command.getMessage());
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/chats")
    public Map<String, Object> publish(ChatMessage request) {
        chatService.sendMessage(request.getRoomId().toString(), request.getMessage());

        return Map.of("msg", request.getMessage(), "roomId", request.getRoomId());
    }
}
