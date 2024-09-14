package org.example.backend.Chat.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.backend.Chat.Model.Entity.Chat;
import org.example.backend.Chat.Model.Entity.ChatRoom;
import org.example.backend.Chat.Model.Req.GetMessageReq;
import org.example.backend.Chat.Model.Res.GetChatMessageRes;
import org.example.backend.Chat.Model.Res.GetChatRoomRes;
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

    public List<GetChatRoomRes> getMyChatRoomList(Long userId) {
        List<ChatRoom> myChatRoomList1 = chatRoomRepository.findAllByUser1Id(userId);
        List<ChatRoom> myChatRoomList2 = chatRoomRepository.findAllByUser2Id(userId);

        List<GetChatRoomRes> myChatRoomResList = new ArrayList<>();
        makeChatRoomResList(1, myChatRoomList1, myChatRoomResList);
        makeChatRoomResList(2, myChatRoomList2, myChatRoomResList);
        myChatRoomResList.sort((chatRoomRes1, chatRoomRes2) -> chatRoomRes2.getLastMessageDay().compareTo(chatRoomRes1.getLastMessageDay()));
        return myChatRoomResList;
    }

    private static void makeChatRoomResList(Integer userSequence, List<ChatRoom> myChatRoomList1, List<GetChatRoomRes> myChatRoomResList) {
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
            GetChatRoomRes chatRoomRes = GetChatRoomRes.builder()
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

    public List<GetChatMessageRes> getChatMessageList(Long userId, Long chatRoomId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new InvalidChatException(BaseResponseStatus.CHAT_INVALID_CHATROOM_ID));
        if (!chatRoom.getUser1().getId().equals(userId) && !chatRoom.getUser2().getId().equals(userId)) {
            throw new InvalidChatException(BaseResponseStatus.CHAT_INVALID_CHATROOM_ID);
        }

        List<Chat> chatList = chatRepository.findByChatRoomIdOrderBySendTimeDesc(pageable, chatRoomId).stream().toList();
        List<GetChatMessageRes> getChatMessageResList = new ArrayList<>();
        for (Chat chat : chatList) {
            GetChatMessageRes getChatMessageRes = GetChatMessageRes.builder()
                    .message(chat.getMessage())
                    .sendTime(chat.getSendTime())
                    .senderId(chat.getUser().getId())
                    .build();
            getChatMessageResList.add(getChatMessageRes);
        }
        return getChatMessageResList;
    }

    @Transactional
    public void saveChat(GetMessageReq getMessageReq, Long chatRoomId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new InvalidChatException(BaseResponseStatus.CHAT_INVALID_CHATROOM_ID));
        User sender;
        if (chatRoom.getUser1().getId().equals(getMessageReq.getSenderId())) {
            sender = chatRoom.getUser1();
        } else if (chatRoom.getUser2().getId().equals(getMessageReq.getSenderId())) {
            sender = chatRoom.getUser2();
        } else {
            throw new InvalidChatException(BaseResponseStatus.CHAT_INVALID_USER_ID);
        }
        Chat chat = Chat.builder()
                .chatRoom(chatRoom)
                .message(getMessageReq.getMessage())
                .user(sender)
                .sendTime(LocalDateTime.parse(getMessageReq.getSendTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
        chatRepository.save(chat);
    }
}
