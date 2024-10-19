<template>
    <TagComponent :tagTitle="'채팅'" :tagSubTitle="'빠르게 궁금증을 해결해보세요'"/>
    <div class="custom-container" style="margin-top: 0; padding-top: 0;">
        <div id="chat-container">
            <LoadingComponent v-if="!chatStore.isLoading" style="z-index: 1000;"/>
            <ChatNavComponent @loading="loading" @reload-chatRoom="reloadChatRoom"/>
            <ChatComponent v-if="isLoading" :key="chatKey"/>
        </div>
    </div>
</template>


<script>
import ChatNavComponent from "@/components/Chat/ChatNavComponent.vue";
import ChatComponent from "@/components/Chat/ChatComponent.vue";
import {mapStores} from "pinia";
import {useUserStore} from "@/store/useUserStore";
import LoadingComponent from "@/components/Common/LoadingComponent.vue";
import {useChatStore} from "@/store/useChatStore";
import TagComponent from "@/components/Common/TagComponent.vue";

export default {
    name: "ChatPage",
    components: {TagComponent, LoadingComponent, ChatNavComponent, ChatComponent},
    computed: {
        ...mapStores(useUserStore),
        ...mapStores(useChatStore),
    },
    mounted() {
        this.checkLoggingStatus();
        this.$watch(() => this.userStore.isLoggedIn, () => {
            this.checkLoggingStatus();
        });

        this.chatStore.isLoading = false;
    },
    data() {
        return {
            chatKey: 0,
            isLoading: false,
        }
    },
    methods: {
        reloadChatRoom() {
            this.chatKey += 1
        },
        loading() {
            this.isLoading = true;
            this.chatStore.isLoading = true;
        },
        checkLoggingStatus(){
            if (!this.userStore.isLoggedIn) {
                alert("로그인이 필요한 서비스입니다.");
                this.$router.push({path: "/login"})
            }
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