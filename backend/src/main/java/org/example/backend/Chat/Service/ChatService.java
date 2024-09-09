package org.example.backend.Chat.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Chat.Model.Entity.Chat;
import org.example.backend.Chat.Model.Entity.ChatRoom;
import org.example.backend.Chat.Model.Res.ChatMessageListRes;
import org.example.backend.Chat.Model.Res.ChatMessageRes;
import org.example.backend.Chat.Model.Res.ChatRoomRes;
import org.example.backend.Chat.Repository.ChatRepository;
import org.example.backend.Chat.Repository.ChatRoomRepository;
import org.example.backend.Exception.custom.InvalidChatException;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.User.Model.Entity.User;
import org.example.backend.User.Repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.backend.Common.BaseResponseStatus.CHAT_INVALID_CHATROOM_ID;
import static org.example.backend.Common.BaseResponseStatus.UNREGISTERED_USER;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

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

    public ChatMessageListRes getChatMessageList(Long userId, Long chatRoomId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new InvalidChatException(CHAT_INVALID_CHATROOM_ID));
        User recipient;
        if (chatRoom.getUser1().getId().equals(userId)) {
            recipient = chatRoom.getUser2();
        } else if (chatRoom.getUser2().getId().equals(userId)) {
            recipient = chatRoom.getUser1();
        } else {
            throw new InvalidChatException(CHAT_INVALID_CHATROOM_ID);
        }

        List<Chat> chatList = chatRepository.findByChatRoomIdOrderBySendTimeDesc(pageable, chatRoomId).stream().toList();
        List<ChatMessageRes> chatMessageResList = new ArrayList<>();
        for (Chat chat : chatList) {
            User user = chat.getUser();
            ChatMessageRes chatMessageRes = ChatMessageRes.builder()
                    .userId(user.getId())
                    .nickname(user.getNickname())
                    .profileImg(user.getProfileImg())
                    .message(chat.getMessage())
                    .sendTime(chat.getSendTime())
                    .build();
            chatMessageResList.add(chatMessageRes);
        }
        return ChatMessageListRes.builder()
                .recipientId(recipient.getId())
                .recipientNickname(recipient.getNickname())
                .messageList(chatMessageResList)
                .build();

    }
}
