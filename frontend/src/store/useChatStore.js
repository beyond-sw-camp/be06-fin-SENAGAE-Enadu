import {defineStore} from "pinia";
import axios from "axios";
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
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
        stompClient: null,

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

            const serverUrl = "http://localhost:8080/ws/chat";
            let  socket = new SockJS(serverUrl);
            this.stompClient = Stomp.over(socket);
            console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverUrl}`)
            this.stompClient.connect(
                {},
                frame => {
                    // this.connected = true;
                    console.log("소켓 연결 성공", frame);

                    this.stompClient.subscribe("/sub/chatroom/"+this.selectedChatRoomId, res => {
                        console.log("구독으로 받은 메시지 입니다.", res.body);

                    })
                }
            )

        },
        send(message) {
            console.log("Send Message:" + message);

            if (this.stompClient && this.stompClient.connected) {
                const msg = {
                    message: message,
                    sendTime: this.formatDateTime(),
                    chatRoomId: this.selectedChatRoomId,
                    senderId: 1
                };
                this.stompClient.send("/pub/message/"+this.selectedChatRoomId, JSON.stringify(msg), {});
            }
        },
        sendMessage(message){
            if(message !== ''){
                this.send(message);
            }
        },
        formatDateTime () {
            const date = new Date();

            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 +1
            const day = String(date.getDate()).padStart(2, "0");
            const hours = String(date.getHours()).padStart(2, "0");
            const minutes = String(date.getMinutes()).padStart(2, "0");
            const seconds = String(date.getSeconds()).padStart(2, "0");

            return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
        }


    }
})
