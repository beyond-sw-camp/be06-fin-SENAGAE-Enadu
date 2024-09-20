<template>
  <li :class="[ chatRoom.chatRoomId == chatStore.selectedChatRoom.chatRoomId ? 'on': '' ]" @click="selected">
    <div role="link" tabindex="0" class="div_button chat_list_link" aria-current="page">
      <div class="info_area">
        <div class="profile_wrap" aria-hidden="true">
          <img class="profile" alt="" :src="chatRoom.recipientProfile">
        </div>
        <div class="text_wrap">
          <div class="name_area"><strong class="name">{{ chatRoom.recipientNickname }}</strong>
            <span class="date_area">{{ lastMessageDay }}</span>
          </div>
          <div class="text_area">{{ chatRoom.lastMessage }}</div>
        </div>
      </div>
    </div>
  </li>
</template>

<script>
import {mapStores} from "pinia";
import {useChatStore} from "@/store/useChatStore";

export default {
  name: "ChatRoomComponent",
  computed: {
    ...mapStores(useChatStore) // 어떤 저장소랑 연결시켜 주겠다.
  },
  props: ['chatRoom'],
  data() {
    return {
      isSelected: false,
      lastMessageDay: "",
    }
  },

  methods: {
    async selected() {
      this.isSelected = true
      this.chatStore.disconnect();
      this.chatStore.selectedChatRoom = {
        chatRoomId: this.chatRoom.chatRoomId,
        recipientNickname: this.chatRoom.recipientNickname,
        recipientProfile: this.chatRoom.recipientProfile,
        recipientId: this.chatRoom.recipientId
      }
      this.$emit("reload-chatRoom")
    },
    formatDateTime(dateTimeString) {
      // 입력 문자열을 Date 객체로 변환
      const inputDate = new Date(dateTimeString)

      // 현재 날짜와 시간
      const now = new Date()

      // 현재 날짜의 시작 시간 (00:00:00)
      const startOfToday = new Date(now.getFullYear(), now.getMonth(), now.getDate())

      // 비교
      if (inputDate >= startOfToday) {
        // 오늘이면 시간만 표시
        return inputDate.toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'})
      } else {
        // 오늘이 아니면 날짜만 표시
        return inputDate.toLocaleDateString().replace(/\.$/, '')
      }
    },
  },
  mounted() {
    this.lastMessageDay = this.formatDateTime(this.chatRoom.lastMessageDay.toString())
    this.selectedChatRoomId = this.chatStore.selectedChatRoomId
  }
}
</script>

<style scoped>
/* 유저 목록 각각*/
.chat_list_area .chat_list li {
  position: relative;
  padding: 0;
  margin: 0;
}

.chat_list_area .chat_list li.on .chat_list_link {
  border-color: #e6e6ea;
  background-color: #f2f9ff;
}

.chat_list_area .chat_list_link {
  overflow: hidden;
  padding: 11px 16px;
  border-top: 1px solid transparent;
  border-bottom: 1px solid transparent;
}

.div_button {
  cursor: pointer;
}

.info_area {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}

/* 유저 목록의 프로필 이미지 */
.chat_list_area .profile_wrap {
  position: relative;
  overflow: hidden;
  -ms-flex-negative: 0;
  flex-shrink: 0;
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

.chat_list_area .profile_wrap .profile {
  width: 50px;
  height: 50px;
  display: block;
}

img {
  max-width: 100%;
  border: 0;
  vertical-align: top;
}

.chat_list_area .profile_wrap:after {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  content: "";
  border: 1px solid rgba(0, 0, 0, .06);
  border-radius: inherit;
}

/* 유저 목록의 글씨 */
.chat_list_area .chat_list_link .text_wrap {
  -webkit-box-flex: 1;
  -ms-flex-positive: 1;
  flex-grow: 1;
  min-width: 0;
  margin-left: 14px;
}

.chat_list_area .chat_list_link .name_area {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  line-height: 19px;
}

.chat_list_area .chat_list_link .name {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  color: #1e1e23;
  font-size: 15px;
}

.chat_list_area .chat_list_link .date_area {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -ms-flex-negative: 0;
  flex-shrink: 0;
  margin-left: 4px;
  color: #929294;
  font-size: 12px;
}

.chat_list_area .chat_list_link .date_area:before {
  content: "";
  -ms-flex-negative: 0;
  flex-shrink: 0;
  width: 4px;
  height: 4px;
  margin-right: 4px;
  border-radius: 50%;
  background-color: #e6e6ea;
}

.chat_list_area .chat_list_link .text_area {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin-top: 1px;
  color: #767678;
  font-size: 13px;
  line-height: 19px;
}
</style>