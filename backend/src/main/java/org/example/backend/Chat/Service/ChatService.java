package org.example.backend.Chat.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.backend.Chat.Model.Entity.Chat;
import org.example.backend.Chat.Model.Entity.ChatRoom;
import org.example.backend.Chat.Model.Req.MessageReq;
import org.example.backend.Chat.Model.Res.ChatMessageRes;
import org.example.backend.Chat.Model.Res.ChatRoomRes;
import org.example.backend.Chat.Repository.ChatRepository;
import org.example.backend.Chat.Repository.ChatRoomRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidChatException;
import org.example.backend.User.Model.Entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

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
            if (!chatList.isEmpty()) {
                chatList.sort((chat1, chat2) -> chat2.getSendTime().compareTo(chat1.getSendTime()));
                lastMessage = chatList.get(0).getMessage();
                lastSendTime = chatList.get(0).getSendTime();
            }
            ChatRoomRes chatRoomRes = ChatRoomRes.builder()
                    .chatRoomId(chatRoom.getId())
                    .recipientNickname(user.getNickname())
                    .recipientProfile(user.getProfileImg())
                    .recipientId(user.getId())
                    .lastMessage(lastMessage)
                    .lastMessageDay(lastSendTime)
                    .build();
            myChatRoomResList.add(chatRoomRes);
        }
    }

    public List<ChatMessageRes> getChatMessageList(Long userId, Long chatRoomId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new InvalidChatException(BaseResponseStatus.CHAT_INVALID_CHATROOM_ID));
        if (!chatRoom.getUser1().getId().equals(userId) && !chatRoom.getUser2().getId().equals(userId)) {
            throw new InvalidChatException(BaseResponseStatus.CHAT_INVALID_CHATROOM_ID);
        }

        List<Chat> chatList = chatRepository.findByChatRoomIdOrderBySendTimeDesc(pageable, chatRoomId).stream().toList();
        List<ChatMessageRes> chatMessageResList = new ArrayList<>();
        for (Chat chat : chatList) {
            ChatMessageRes chatMessageRes = ChatMessageRes.builder()
                    .message(chat.getMessage())
                    .sendTime(chat.getSendTime())
                    .senderId(chat.getUser().getId())
                    .build();
            chatMessageResList.add(chatMessageRes);
        }
        return chatMessageResList;
    }

    @Transactional
    public void saveChat(MessageReq messageReq, Long chatRoomId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new InvalidChatException(BaseResponseStatus.CHAT_INVALID_CHATROOM_ID));
        User sender;
        if (chatRoom.getUser1().getId().equals(messageReq.getSenderId())) {
            sender = chatRoom.getUser1();
        } else if (chatRoom.getUser2().getId().equals(messageReq.getSenderId())) {
            sender = chatRoom.getUser2();
        } else {
            throw new InvalidChatException(BaseResponseStatus.CHAT_INVALID_USER_ID);
        }
        Chat chat = Chat.builder()
                .chatRoom(chatRoom)
                .message(messageReq.getMessage())
                .user(sender)
                .sendTime(LocalDateTime.parse(messageReq.getSendTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
        chatRepository.save(chat);
    }
}
