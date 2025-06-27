package org.example.springwebsocket.app.controller;

import lombok.RequiredArgsConstructor;
import org.example.springwebsocket.app.model.ChatMessage;
import org.example.springwebsocket.app.service.ChatMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping
    public List<ChatMessage>getChatMessages() {
        return chatMessageService.findAll();
    }

}
