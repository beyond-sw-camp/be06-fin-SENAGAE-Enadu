import {defineStore} from "pinia";
import axios from "axios";

const backend = "/api";

export const useChatStore = defineStore("chat", {
    state: () => ({
        chatRoomList: [
            {
                recipientName: "",
                chatRoomId: 0,
                recipientProfile: "",
                lastMessage: "",
                lastMessageDay: ""
            },
        ],
        chatMessageList: {
            prevMessageDate: "1900-01-01",
            recipientNickname: "길동홍",
            recipientId: 1,
            messageList: [
                {
                    nickname: "길동홍",
                    userId: 1,
                    message: "채팅 내용",
                    sendTime: "2024-09-09T10:12:12",
                    profileImg: "http://img.url"
                },
                {
                    nickname: "길동홍",
                    userId: 2,
                    message: "채팅 내용",
                    sendTime: "2024-09-10T12:13:13",
                    profileImg: "http://img.url"
                },
                {
                    nickname: "길동홍",
                    userId: 2,
                    message: "채팅 내용",
                    sendTime: "2024-09-11T15:4:14",
                    profileImg: "http://img.url"
                }
            ]
        },

    }),
    actions: {
        async getChatRoomList() {
            const res = await axios.get(backend + "/chat/chatRoomList")
            this.chatRoomList = res.data.result;
        },
        async getChatMessageList(chatRoomId, page) {
            const res = await axios.get(backend + "/chat/messageList", {
                params: {
                    chatRoomId: chatRoomId,
                    page: page,
                    size: 20
                },
                withCredentials: true
            });
            this.chatMessageList = res.data.result;
            console.log(this.chatMessageList);

        }
    }
})
