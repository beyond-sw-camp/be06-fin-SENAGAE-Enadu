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
        ]
    }),
    actions: {
        async getChatRoomList() {
            const res = await axios.get(backend + "/chat/chatRoomList")
            this.chatRoomList = res.data.result;
        },
    }
});