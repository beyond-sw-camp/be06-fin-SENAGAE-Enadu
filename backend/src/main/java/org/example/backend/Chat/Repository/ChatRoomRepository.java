package org.example.backend.Chat.Repository;

import org.example.backend.Chat.Model.Entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByUser1Id(Long userId);
    List<ChatRoom> findAllByUser2Id(Long userId);
}
