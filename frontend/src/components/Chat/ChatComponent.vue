<template>
  <div id="content" class="content">
    <section class="chat_section">
      <header class="chat_header" aria-hidden="false">
        <div class="chat_header_top">
          <div class="info_area">
            <div class="text_wrap">
              <div class="name_area"><strong class="name">{{ chatStore.selectedChatRoom.recipientNickname }}</strong>
              </div>
            </div>
          </div>
        </div>
      </header>
      <section class="chat_area _chatWindow" aria-hidden="false">
        <div class="chat_reverse">
          <ul class="group_message_balloon" style="visibility: visible;">
            <li v-if="isLoading"></li>
            <ChatMessageComponent v-else v-for="(chatMessage, idx) in chatStore.chatMessageList" :key="`${idx}-${chatMessage.sendTime}`"
                                  :idx="idx" :chatMessage="chatMessage" />

            <li v-if="chatStore.chatMessageList.length !== 0"  class="date_check">
              <span>
                <em><strong> {{ chatStore.chatMessageList.at(-1).sendTime.split("T")[0] }}</strong></em>
              </span>
            </li>
          </ul>
        </div>
        <div class="chat_empty"  v-if="chatStore.chatMessageList.length === 0"><span class="chat_empty_span">대화 내역이 없습니다.</span></div>
      </section>
      <footer class="chat_write" aria-hidden="false">
        <div>
          <div class="chat_write_area">
            <div class="chat_write_wrap">
              <div class="input_btn_wrap"></div>
              <div class="chat_input_area">
                <textarea @input="autoResize" ref="textarea" title="메시지 입력창" class="chat_input" maxlength="2000"
                          placeholder="메시지를 입력하세요." @keydown="handleKeydown" v-model="content"></textarea>
              </div>
              <div class="submit_btn_wrap">
                <button class="btn_submit " type="submit" @click="clickSendMessageButton" aria-disabled="true"><img src="@/assets/img/send_icon.png"
                                                                                    style="width: 20px; height: 20px;"
                                                                                    alt=""></button>
              </div>
            </div>
          </div>
        </div>
        <div class="chat_faq_area">
          <div class="chat_composite_option_area hide ">
            <ul class="list_btn_option"></ul>
          </div>
          <div class="ly_chat_toast hide" role="alert" style="opacity: 0;"></div>
        </div>
      </footer>
    </section>
  </div>
</template>

<script>
import ChatMessageComponent from "@/components/Chat/ChatMessageComponent.vue";
import {mapStores} from "pinia";
import {useChatStore} from "@/store/useChatStore";

export default {
  name: "ChatComponent",
  components: {ChatMessageComponent},
  computed: {
    ...mapStores(useChatStore) // 어떤 저장소랑 연결시켜 주겠다.
  },
  data() {
    return {
      isLoading: true,
      page: 0,
      content: "",
    }
  },
  methods: {
    async getChatMessageList() {
      await this.chatStore.getChatMessageList(this.page);
      this.isLoading = false;
    },
    autoResize() {
      const textarea = this.$refs.textarea;
      textarea.style.height = 'auto'; // 높이를 초기화하여 줄 수를 재계산
      textarea.style.height = textarea.scrollHeight + 'px'; // 내용에 맞게 높이 조절
      if (this.content === "") {
        textarea.style.height = 20+"px";
      }
    },
    clickSendMessageButton(){
      if (this.chatStore.selectedChatRoom.chatRoomId === 0){
        alert("채팅방이 선택되지 않았습니다.");
        this.content=""
        return;
      } else if (this.content === ""){
        alert("채팅 메세지를 입력해주세요.");
        return;
      }
      this.chatStore.sendMessage(this.content);
      this.content=""
      this.autoResize()
    },
    handleKeydown(event) {
      if (event.key === 'Enter') {
        if (!event.shiftKey) {
          event.preventDefault();
          this.clickSendMessageButton();
        }
      }
    },
  },
  mounted() {
    this.getChatMessageList();
  }
}
</script>

<style scoped>

.content {
  position: relative;
  -webkit-box-flex: 1;
  -ms-flex-positive: 1;
  flex-grow: 1;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  min-width: 445px;
  margin-left: 10px;
  border: 1px solid #e6e6ea;
}

/* class:chat_section 정확하게  채팅방 section */
.chat_section {
  position: relative;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -ms-flex-direction: column;
  flex-direction: column;
  height: 600px;
}

.chat_section {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  background: #f0f2f5;
}

/* class: chat_header 채팅방 헤더 */
.chat_header {
  position: relative;
  z-index: 200;
  -webkit-box-flex: 0;
  -ms-flex: 0 0 auto;
  flex: 0 0 auto;
  padding: 9px 20px;
  border-bottom: 1px solid rgba(0, 0, 0, .06);
  background-color: rgba(240, 242, 245, .95);
}

.chat_header_top {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-pack: justify;
  -ms-flex-pack: justify;
  justify-content: space-between;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  height: 38px;
}

.chat_header_top .info_area {
  -webkit-box-flex: 1;
  -ms-flex: 1 1 auto;
  flex: 1 1 auto;
  min-width: 0;
  color: #1e1e23;
}

/* .info_area {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
} */
.chat_header_top .text_wrap {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  overflow: hidden;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -ms-flex-direction: column;
  flex-direction: column;
  -webkit-box-flex: 1;
  -ms-flex: 1 1 auto;
  flex: 1 1 auto;
}

.chat_header_top .name_area {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}

.chat_header_top .text_wrap .name {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  font-size: 16px;
  font-weight: 700;
  line-height: 18px;
}

/* class: chat_area _chatWindow, 채팅 에리어 */
.chat_area {
  z-index: 100;
  -webkit-box-flex: 1;
  -ms-flex: 1 1 auto;
  flex: 1 1 auto;
  min-height: 0;
}

.chat_area {
  position: relative;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -ms-flex-direction: column;
  flex-direction: column;
  height: 100%;
}

.chat_reverse {
  display: flex;
  overflow-x: hidden;
  overflow-y: auto;
  -webkit-box-orient: vertical;
  -webkit-box-direction: reverse;
  flex-direction: column-reverse;
  -webkit-box-flex: 1;
  -ms-flex: 1 1 100%;
  flex: 1 1 100%;
}

.group_message_balloon {
  position: sticky;
  bottom: 0;
  -webkit-box-flex: 1;
  -ms-flex-positive: 1;
  flex-grow: 1;
  padding: 20px 14px;
  display: flex;
  flex-direction: column-reverse;
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

.group_message_balloon .new_message_balloon_area + li {
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

/* class: chat_write채팅방 푸터 (메세지 입력 부분) */
.chat_write {
  position: relative;
  z-index: 100;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -ms-flex-direction: column;
  flex-direction: column;
  -webkit-box-flex: 0;
  -ms-flex: 0 0 auto;
  flex: 0 0 auto;
}

.chat_write_area {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  border-top: 1px solid #e6e6ea;
  background-color: #fff;
}

.chat_write_wrap {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
}

.input_btn_wrap {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: stretch;
  -ms-flex-align: stretch;
  align-items: stretch;
  height: 49px;
  padding-left: 16px;
}

.chat_input_area {
  -webkit-box-flex: 1;
  -ms-flex: 1 0;
  flex: 1 0;
  padding: 15px 0 14px;
  margin-left: 1px;
}

.chat_input {
  overflow-y: auto;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  width: 100%;
  height: 20px;
  max-height: 200px;
  padding: 0 17px 0 6px;
  color: #000;
  font-size: 15px;
  line-height: 20px;
}

textarea {
  border: none;
  outline: none;
  resize: none;
}

textarea {
  font-family: -apple-system, BlinkMacSystemFont, Apple SD Gothic Neo, Arial, Malgun Gothic, 맑은 고딕, Dotum, sans-serif;
  font-size: 12px;
}

textarea {
  padding: 0;
  margin: 0;
}

.submit_btn_wrap {
  margin-right: 12px;
}

.submit_btn_wrap .btn_submit {
  width: 40px;
  height: 49px;
  color: #c7c9cf;
}

button {
  border: none;
  background: none;
  cursor: pointer;
}

img {
  max-width: 100%;
  border: 0;
  vertical-align: top;
}
.chat_empty {
  margin-bottom:280px;
  display: flex;
}

.chat_empty_span {
  margin: auto auto;
  font-weight: bold
}
</style>