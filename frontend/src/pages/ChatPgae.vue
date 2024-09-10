<template>
  <div class="body">
    <div class="container">
      <div id="__next">
        <div id="warp">
          <main id="chat-container">
            <ChatNavComponent @loading="loading" @reload-chatRoom="reloadChatRoom"/>
            <ChatComponent v-if="isLoading" :key="chatKey"/>
          </main>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import ChatNavComponent from "@/components/Chat/ChatNavComponent.vue";
import ChatComponent from "@/components/Chat/ChatComponent.vue";

export default {
  name: "ChatPage",
  components: {ChatNavComponent, ChatComponent},
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

/* id=wrap */
#wrap {
  position: relative;
  z-index: 100;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  overflow-x: hidden;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -ms-flex-direction: column;
  flex-direction: column;
}
#__next, #wrap {
  height: 600px;
}
.body {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 100vh;
  background: #fff;
}
.container{
  border-right: 10px;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.08), 0 10x 10px rgba(0, 0, 0, 0.08);
  position: relative;
  overflow: hidden;
  width: 800px;
  max-width: 100%;
  min-height: 700px;
}
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