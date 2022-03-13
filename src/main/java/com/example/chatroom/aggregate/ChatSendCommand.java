package com.example.chatroom.aggregate;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatSendCommand {
    private String message;
}
