<template>
  <li v-if="showDate()" class="date_check">
    <span>
      <em><strong> {{ chatStore.chatMessageList[idx-1].sendTime.split("T")[0] }}</strong></em>
    </span>
  </li>
  <li class="new_message_balloon_area  _message _msgId2">
    <div v-if="chatStore.selectedChatRoom.recipientId == chatMessage.senderId" class="thumbnail_profile _thmbnail">
      <button role="link" class="thumbnail_link ">
        <img :src="chatStore.selectedChatRoom.recipientProfile"
             alt="유저 프로필" width="31">
      </button>
    </div>
    <div v-if="chatStore.selectedChatRoom.recipientId == chatMessage.senderId" class="chat_message_nickname _nickname">
      <strong>{{ chatStore.selectedChatRoom.recipientNickname }}</strong>
    </div>
    <div v-if="chatStore.selectedChatRoom.recipientId == chatMessage.senderId" class="message_balloon card_message type_text" role="heading"
         aria-level="5">
      <p class="_copy_area">{{ chatMessage.message }}</p>
      <div class="txt_confirm _status">
        <span class="_time"><em>{{ day }}</em> <span>{{ hour }}:{{ minute }}</span></span>
      </div>
    </div>
    <div v-else class="message_balloon card_message type_text rgt" role="heading" aria-level="5">
      <p class="_copy_area">{{ chatMessage.message }}</p>
      <div class="txt_confirm _status">
        <span class="_time"><em>{{ day }}</em> <span>{{ hour }}:{{ minute }}</span></span>
      </div>
    </div>
  </li>

</template>

<script>
import {mapStores} from "pinia";
import {useChatStore} from "@/store/useChatStore";

export default {
  name: "ChatMessageComponent",
  props: ['chatMessage', "idx"],
  computed: {
    ...mapStores(useChatStore) // 어떤 저장소랑 연결시켜 주겠다.
  },
  data() {
    return {
      chatMessageDate: this.chatMessage.sendTime.split("T")[0],
      time: "",
      day: "오전",
      hour: "",
      minute: "",
    }
  },
  methods: {
    showDate() {
      if (this.idx === 0 ){
        return false;
      }
      return this.chatMessageDate !== this.chatStore.chatMessageList[this.idx-1].sendTime.split("T")[0]
    },
    setTime() {
      let time = this.chatMessage.sendTime.split("T")[1].split(":");
      if (Number(time[0]) >= 12) {
        this.day = "오후"
        this.hour = Number(time[0]) == 12 ? 12 : Number(Math.abs(time[0] - 12))
      } else {
        this.hour = Number(time[0])
      }
      this.minute = String(time[1].padStart(2,"0"))
    },
  },
  mounted() {
    console.log(this.chatMessage);
    this.setTime();
  }


}
</script>

<style scoped>
.group_message_balloon {
  position: sticky;
  bottom: 0;
  -webkit-box-flex: 1;
  -ms-flex-positive: 1;
  flex-grow: 1;
  padding: 20px 14px;
}

ul {
  padding: 0;
  margin: 0;
}

li, ol, ul {
  list-style: none;
}

/* 채팅 지역에 날짜 */
.group_message_balloon .date_check {
  margin-bottom: 12px;
  text-align: center;
}

li {
  padding: 0;
  margin: 0;
}

li, ol, ul {
  list-style: none;
}

.group_message_balloon .date_check > span {
  display: inline-block;
  padding: 0 12px 1px;
  border-radius: 15px;
  background-color: rgba(161, 173, 184, .7);
}

.group_message_balloon .date_check em {
  display: inline-block;
  height: 25px;
  color: #fff;
  font-size: 12px;
  line-height: 25px;
}

em {
  font-style: normal;
}

.group_message_balloon .date_check em > strong {
  font-size: 13px;
  font-weight: 400;
  vertical-align: bottom;
}

/* 채팅 메세지 */
.group_message_balloon .new_message_balloon_area {
  position: relative;
}

li {
  padding: 0;
  margin: 0;
}

li, ol, ul {
  list-style: none;
}

.group_message_balloon .message_balloon.rgt.card_inform, .group_message_balloon .message_balloon.rgt.card_message {
  border-color: #c5e8fe;
  background: #d9f0ff;
}

.group_message_balloon .message_balloon.rgt.type_text {
  max-width: 80%;
}

.group_message_balloon .message_balloon.rgt {
  float: right;
  max-width: 290px;
  margin-right: 5px;
}

.group_message_balloon .message_balloon {
  position: relative;
  float: left;
  max-width: 290px;
  margin-bottom: 0;
  margin-left: 40px;
  border: 1px solid rgba(0, 0, 0, .12);
  border-radius: 12px;
  background-color: #fff;
  line-height: 1.5;
  word-wrap: break-word;
}

.group_message_balloon .message_balloon > p {
  padding: 11px 13px;
  border-radius: 11px;
  color: #1e1e23;
  font-size: 14px;
}

p {
  padding: 0;
  margin: 0;
}

.group_message_balloon .message_balloon.rgt .txt_confirm {
  right: 100%;
  left: inherit;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  margin: 0 10px 0 0;
  text-align: right;
}

.group_message_balloon .message_balloon .txt_confirm {
  position: absolute;
  bottom: 1px;
  left: 100%;
  min-width: 60px;
  margin-left: 10px;
  color: #929294;
  font-size: 11px;
}

.group_message_balloon .message_balloon.rgt .txt_confirm > span {
  -webkit-box-flex: 1;
  -ms-flex: 1 0 auto;
  flex: 1 0 auto;
}

em {
  font-style: normal;
}

.group_message_balloon .message_balloon.rgt.card_message:after {
  background: url(@/assets/img/message_tail_request_re.svg) no-repeat;
}

.group_message_balloon .message_balloon.rgt.card_message:after {
  top: 1px;
  right: -7px;
  left: inherit;
  width: 10px;
}

.group_message_balloon .message_balloon.card_message:after {
  content: "";
  position: absolute;
  top: 0;
  left: -5px;
  width: 10px;
  height: 10.99px;
  background: url(@/assets/img/message_tail_re.svg) no-repeat;
}

.group_message_balloon .new_message_balloon_area:after {
  content: "";
  display: block;
  clear: both;
}

.group_message_balloon .new_message_balloon_area {
  margin-top: 30px;
}

/* 상대가 보낸 채팅 메세지의 프로필이미지,유저이름 */
li {
  padding: 0;
  margin: 0;
}

li, ol, ul {
  list-style: none;
}

.group_message_balloon .new_message_balloon_area .thumbnail_profile {
  position: absolute;
  top: 0;
  left: 0;
  -webkit-transition: opacity .2s ease-in-out;
  transition: opacity .2s ease-in-out;
}

.group_message_balloon .new_message_balloon_area .thumbnail_profile .thumbnail_link {
  position: relative;
  display: block;
  overflow: hidden;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background-color: #fff;
  text-align: center;
}

button {
  border: none;
  background: none;
  cursor: pointer;
  padding: 0;
  margin: 0;
}

.group_message_balloon .new_message_balloon_area .thumbnail_profile .thumbnail_link img {
  position: absolute;
  top: -50%;
  right: -50%;
  bottom: -50%;
  left: -50%;
  width: auto;
  max-width: 100%;
  height: auto;
  max-height: 100%;
  margin: auto;
}

img {
  max-width: 100%;
  border: 0;
  vertical-align: top;
}

.group_message_balloon .new_message_balloon_area .thumbnail_profile:after {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  content: "";
  border: 1px solid rgba(0, 0, 0, .06);
  border-radius: 50%;
  pointer-events: none;
}

.group_message_balloon .new_message_balloon_area .chat_message_nickname {
  margin: 1px 5px 10px 40px;
}

.group_message_balloon .new_message_balloon_area .chat_message_nickname strong {
  color: #666f7a;
  font-size: 12px;
  font-weight: 600;
}
</style>