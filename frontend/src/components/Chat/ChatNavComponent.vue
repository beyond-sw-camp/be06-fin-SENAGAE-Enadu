<template>
  <aside id="snb" class="snb">
    <section class="chat_list_search">
      <div class="info_wrap"><em class="result">전체</em></div>
    </section>
    <section id="talk_all_tabpanel" aria-labelledby="talk_all_tab" role="tabpanel"
             class="chat_list_area">
      <div class="chat_list_wrap ">
        <ul class="chat_list">
          <li v-if="isLoading"></li>
          <ChatRoomComponent v-else v-for="(chatRoom, idx) in chatRoomList" :key="idx"
                             :chatRoom="chatRoom" :selectedChatRoomId="selectedChatRoomId"
                             @update-select-chatRoom="handleSelectChatRoom"/>
        </ul>
      </div>
    </section>
    <section class="chat_list_footer">
      <div class="btn_delete_wrap"><img src="@/assets/img/biggarbagebin.svg" style="width:20px; height:20px" alt=""></div>
    </section>
  </aside>
</template>

<script>
import ChatRoomComponent from "@/components/Chat/ChatRoomComponent.vue";
import { useChatStore } from '@/store/useChatStore';
import { mapStores } from 'pinia';

export default {
  name: "ChatNavComponent",
  components: {ChatRoomComponent},
  data() {
    return {
      isLoading: true,
      selectedChatRoomId: 0,
      chatRoomList:[],
    }
  },
  computed: {
    ...mapStores(useChatStore) // 어떤 저장소랑 연결시켜 주겠다.
  },
  methods: {
    async getChatRoomList() {
      await this.chatStore.getChatRoomList();
      this.isLoading=false;
      this.chatRoomList=this.chatStore.chatRoomList;

    },
    handleSelectChatRoom(chatRoomId){
      this.selectedChatRoomId = chatRoomId;
    },
  },
  mounted() {
    this.getChatRoomList();
  }
}
</script>


<style scoped>
li, ol, ul {
  list-style: none;
}
ul {
  padding: 0;
  margin: 0;
}
img {
  max-width: 100%;
  border: 0;
  vertical-align: top;
}
em{
  font-style: normal;
}

/* id: snb  왼쪽 사이드 */
#snb {
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -ms-flex-direction: column;
  flex-direction: column;
  -webkit-box-flex: 0;
  -ms-flex: 0 0 290px;
  flex: 0 0 290px;
  min-width: 0;
  border: 1px solid #e6e6ea;
  background-color: #fff;
}
#snb {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}

/* class: chat_list_search, 전체 써있는 section */
.chat_list_search {
  position: relative;
}

.chat_list_search .info_wrap {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  height: 39px;
  margin: 0 16px;
  border-bottom: 1px solid #e6e6ea;
}
.chat_list_search .info_wrap .result {
  color: #303038;
  font-size: 14px;
  font-weight: 600;
  line-height: 17px;
}

/* id:talk_all_tabpanel, class:chat_list_area 채팅방 목록 */
.chat_list_area {
  position: relative;
  overflow-x: hidden;
  overflow-y: auto;
  -webkit-box-flex: 1;
  -ms-flex: 1 1 450px;
  flex: 1 1 450px;
  padding-bottom: 4px;
  margin-top: -1px;
}

/* class: chat_list_footer 쓰레기통 section */
.chat_list_footer{
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  min-height: 49px;
  border-top: 1px solid #e6e6ea;
}
.chat_list_footer .btn_delete_wrap {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
  padding: 0 15px;
}

</style>