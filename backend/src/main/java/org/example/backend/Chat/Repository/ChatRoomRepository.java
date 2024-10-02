package org.example.backend.Chat.Repository;

import org.example.backend.Chat.Model.Entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByUser1Id(Long userId);
    List<ChatRoom> findAllByUser2Id(Long userId);
    Optional<ChatRoom> findByUser1IdAndUser2Id(Long userId, Long user2Id);
}
