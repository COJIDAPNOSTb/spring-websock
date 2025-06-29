package org.example.springwebsocket.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String content;
    private String sender;
    private MessageType type;
    @Builder.Default
    private Instant timestamp = Instant.now();
}
