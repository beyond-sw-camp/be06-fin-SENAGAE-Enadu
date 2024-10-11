package org.example.backend.Chat.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.common.Chat.Model.Entity.Chat;
import com.example.common.Chat.Model.Entity.ChatRoom;
import org.example.backend.Chat.Model.Req.GetMessageReq;
import org.example.backend.Chat.Model.Req.StartChatReq;
import org.example.backend.Chat.Model.Res.GetChatMessageRes;
import org.example.backend.Chat.Model.Res.GetChatRoomRes;
import org.example.backend.Chat.Model.Res.StartChatRes;
import com.example.common.Chat.Repository.ChatRepository;
import com.example.common.Chat.Repository.ChatRoomRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidChatException;
import org.example.backend.Exception.custom.InvalidUserException;
import com.example.common.User.Model.Entity.User;
import com.example.common.User.Repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public StartChatRes startChat(Long userId, StartChatReq startChatReq){
        User recipient = userRepository.findByNicknameAndEnableTrue(startChatReq.getNickname()).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        if (recipient.getId().equals(userId)){
            throw new InvalidChatException(BaseResponseStatus.CHAT_SELF_CHAT);
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        User user1 = userId < recipient.getId() ? user : recipient;  // 작은 userId가 user1에 들어간다.
        User user2 = userId > recipient.getId() ? user : recipient;
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findByUser1IdAndUser2Id(user1.getId(), user2.getId());
        ChatRoom chatRoom;
        if (chatRoomOptional.isEmpty()) {
            chatRoom = ChatRoom.builder().user1(user1).user2(user2).build();
            chatRoomRepository.save(chatRoom);
        } else {
            chatRoom = chatRoomOptional.get();
        }
        System.out.println("에러");
        return StartChatRes.builder()
                .chatRoomId(chatRoom.getId())
                .recipientId(recipient.getId())
                .recipientProfile(recipient.getProfileImg())
                .recipientNickname(recipient.getNickname())
                .build();

    }
}
