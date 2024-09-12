import {defineStore} from "pinia";
import axios from "axios";
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import {useUserStore} from "@/store/useUserStore";
const backend = "/api";

export const useChatStore = defineStore("chat", {
    state: () => ({
        selectedChatRoom: {
            chatRoomId: 0,
            recipientNickname: "",
            recipientProfile: "",
            recipientId: 0
        },
        chatRoomList: [
            {
                recipientNickname: "",
                chatRoomId: 0,
                recipientProfile: "",
                recipientId: 0,
                lastMessage: "",
                lastMessageDay: ""
            }
        ],
        chatMessageList: [
            {
                message: "",
                sendTime: "",
                senderId: 0
            }
        ],
        stompClient: null,
    }),
    actions: {
        async getChatRoomList() {
            const res = await axios.get(backend + "/chat/chatRoomList",{withCredentials: true})
            this.chatRoomList = res.data.result;
            this.selectedChatRoom =  {
                chatRoomId: this.chatRoomList[0].chatRoomId,
                recipientNickname: this.chatRoomList[0].recipientNickname,
                recipientProfile: this.chatRoomList[0].recipientProfile,
                recipientId: this.chatRoomList[0].recipientId
            }
        },
        async getChatMessageList(page) {
            const res = await axios.get(backend + "/chat/messageList", {
                params: {
                    chatRoomId: this.selectedChatRoom.chatRoomId,
                    page: page,
                    size: 20
                },
                withCredentials: true
            });
            console.log(res.data);
            this.chatMessageList = res.data.result;
            console.log(this.chatMessageList);

            const serverUrl = "http://localhost:8080/ws/chat";
            let  socket = new SockJS(serverUrl, null, {withCredentials: true});
            this.stompClient = Stomp.over(socket);
            console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverUrl}`)
            this.stompClient.connect(
                {},
                frame => {
                    // this.connected = true;
                    console.log("소켓 연결 성공", frame);

                    this.stompClient.subscribe("/sub/chatroom/"+this.selectedChatRoom.chatRoomId, res => {
                        console.log("구독으로 받은 메시지 입니다.", res.body);
                        this.chatMessageList.unshift((JSON).parse(res.body));
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
                    chatRoomId: this.selectedChatRoom.chatRoomId,
                    senderId: useUserStore().$state.userId
                };
                this.stompClient.send("/pub/message/"+this.selectedChatRoom.chatRoomId, JSON.stringify(msg), {});
            }
        },
        sendMessage(message){
            if(message !== ''){
                this.send(message);
            }
            const msg = {
                message: message,
                sendTime: this.formatDateTime(),
                senderId: useUserStore().$state.userId
            };
            this.chatMessageList.unshift(msg);
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
