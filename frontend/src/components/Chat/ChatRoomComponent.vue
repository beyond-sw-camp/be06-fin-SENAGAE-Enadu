<template>
  <li :class="[ chatRoom.chatRoomId == selectedChatRoomId ? 'on': '' ]" @click="selected">
    <div role="link" tabindex="0" class="div_button chat_list_link" aria-current="page">
      <div class="info_area">
        <div class="profile_wrap" aria-hidden="true">
          <img class="profile" width="50" height="50" alt="" :src="chatRoom.recipientProfile">
        </div>
        <div class="text_wrap">
          <div class="name_area"><strong class="name">{{ chatRoom.recipientName}}</strong>
            <span class="date_area">{{chatRoom.lastMessageDay}}</span>
          </div>
          <div class="text_area">{{chatRoom.lastMessage}}</div>
        </div>
      </div>
    </div>
  </li>
</template>

<script>
export default {
  name: "ChatRoomComponent",
  props: ['chatRoom', 'selectedChatRoomId'],
  data() {
    return {
      isSelected: false,
    }
  },
  methods: {
    selected() {
      this.isSelected = true
      this.$emit("update-select-chatRoom", this.chatRoom.chatRoomId)
    }
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