package org.example.springwebsocket.app.repository;

import org.example.springwebsocket.app.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query(value = "SELECT * FROM (" +
            "SELECT * FROM chat_messages " +
            "ORDER BY timestamp DESC " +
            "LIMIT 50" +
            ") AS recent_messages " +
            "ORDER BY timestamp ASC",
            nativeQuery = true)
    List<ChatMessage> findLast50OrderedByTimestamp();
}
