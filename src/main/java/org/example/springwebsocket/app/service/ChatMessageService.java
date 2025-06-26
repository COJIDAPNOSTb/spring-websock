package org.example.springwebsocket.app.service;

import lombok.RequiredArgsConstructor;
import org.example.springwebsocket.app.model.ChatMessage;
import org.example.springwebsocket.app.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    @Autowired
    private final ChatMessageRepository chatMessageRepository;
    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }
    public List<ChatMessage> findAll() {
        return chatMessageRepository.findLast50OrderedByTimestamp();
    }


}
