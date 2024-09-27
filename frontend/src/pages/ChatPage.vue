<template>
  <div id="custom-container">
    <main id="chat-container">
      <ChatNavComponent @loading="loading" @reload-chatRoom="reloadChatRoom"/>
      <ChatComponent v-if="isLoading" :key="chatKey"/>
    </main>
  </div>
</template>


<script>
import ChatNavComponent from "@/components/Chat/ChatNavComponent.vue";
import ChatComponent from "@/components/Chat/ChatComponent.vue";
import {mapStores} from "pinia";
import {useUserStore} from "@/store/useUserStore";

export default {
  name: "ChatPage",
  components: {ChatNavComponent, ChatComponent},
  computed: {
    ...mapStores(useUserStore)
  },
  mounted(){
    if(!this.userStore.isLoggedIn) {
      alert("로그인이 필요한 서비스입니다.");
      this.$router.push({path: "/login"})
    }
  },
  data() {
    return {
      chatKey: 0,
      isLoading: false,
    }
  },
  methods: {
    reloadChatRoom(){
      this.chatKey += 1
    },
    loading(){
      this.isLoading = true;
    }
  }


}
</script>


<style scoped>
/* id: container */
#chat-container, #snb {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
#chat-container {
  -webkit-box-flex: 1;
  -ms-flex: 1 1 auto;
  flex: 1 1 auto;
  width: 100%;
  max-width: 1068px;
  min-height: 0;
  padding: 0 10px;
  margin: 18px auto 0;
}
</style>