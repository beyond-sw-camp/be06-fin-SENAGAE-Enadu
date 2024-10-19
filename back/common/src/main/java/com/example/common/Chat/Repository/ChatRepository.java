package com.example.common.Chat.Repository;

import com.example.common.Chat.Model.Entity.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Slice<Chat> findByChatRoomIdOrderBySendTimeDesc(Pageable pageable, Long chatRoomId);

}
