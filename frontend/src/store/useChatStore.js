import {defineStore} from "pinia";
import axios from "axios";

const backend = "/api";

export const useChatStore = defineStore("chat", {
    state: () => ({
        selectedChatRoomId: 0,
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
            recipientNickname: "",
            recipientId: 0,
            messageList: [
                {
                    nickname: "",
                    userId: 0,
                    message: "",
                    sendTime: "",
                    profileImg: ""
                }
            ]
        },

    }),
    actions: {
        async getChatRoomList() {
            const res = await axios.get(backend + "/chat/chatRoomList",{withCredentials: true})
            this.chatRoomList = res.data.result;
            this.selectedChatRoomId = this.chatRoomList[0].chatRoomId;
        },
        async getChatMessageList(page) {
            const res = await axios.get(backend + "/chat/messageList", {
                params: {
                    chatRoomId: this.selectedChatRoomId,
                    page: page,
                    size: 20
                },
                withCredentials: true
            });
            this.chatMessageList = res.data.result;

        }
    }
})
