package org.example.backend.Chat.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Chat.Model.ChatRoomRes;
import org.example.backend.Chat.Model.Entity.Chat;
import org.example.backend.Chat.Model.Entity.ChatRoom;
import org.example.backend.Chat.Repository.ChatRoomRepository;
import org.example.backend.User.Model.Entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoomRes> getMyChatRoomList(Long userId) {
        List<ChatRoom> myChatRoomList1 = chatRoomRepository.findAllByUser1Id(userId);
        List<ChatRoom> myChatRoomList2 = chatRoomRepository.findAllByUser2Id(userId);

        List<ChatRoomRes> myChatRoomResList = new ArrayList<>();
        makeChatRoomResList(1, myChatRoomList1, myChatRoomResList);
        makeChatRoomResList(2, myChatRoomList2, myChatRoomResList);
        myChatRoomResList.sort((chatRoomRes1, chatRoomRes2) -> chatRoomRes2.getLastMessageDay().compareTo(chatRoomRes1.getLastMessageDay()));
        return myChatRoomResList;
    }

    private static void makeChatRoomResList(Integer userSequence, List<ChatRoom> myChatRoomList1, List<ChatRoomRes> myChatRoomResList) {
        for (ChatRoom chatRoom : myChatRoomList1) {
            User user;
            if (userSequence == 1) {
                user = chatRoom.getUser2();
            } else {
                user = chatRoom.getUser1();
            }
            List<Chat> chatList = chatRoom.getChatList();
            String lastMessage = "";
            LocalDateTime lastSendTime = LocalDateTime.now();
            if (chatList != null) {
                chatList.sort((chat1, chat2) -> chat2.getSendTime().compareTo(chat1.getSendTime()));
                lastMessage = chatList.get(0).getMessage();
                lastSendTime = chatList.get(0).getSendTime();
            }
            ChatRoomRes chatRoomRes = ChatRoomRes.builder()
                    .chatRoomId(chatRoom.getId())
                    .recipientName(user.getNickname())
                    .recipientProfile(user.getProfileImg())
                    .lastMessage(lastMessage)
                    .lastMessageDay(lastSendTime)
                    .build();
            myChatRoomResList.add(chatRoomRes);
        }
    }
}
