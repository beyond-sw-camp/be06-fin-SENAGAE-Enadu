import { defineStore } from "pinia";
import axios from "axios";

// const backend = "";

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
            const res = await axios.get("https://run.mocky.io/v3/526736d4-0733-4c7b-94f5-ecb09f1333a4");
            this.chatRoomList = res.data.result;
            console.log(res);
        },
    }
});