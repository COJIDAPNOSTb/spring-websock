package org.example.springwebsocket.app.repository;

import org.example.springwebsocket.app.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

public List<ChatMessage> findTop50ByOrderByTimestampDesc();
}