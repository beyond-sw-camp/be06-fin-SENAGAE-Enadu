import { createRouter, createWebHistory } from "vue-router";
import QnaListPage from "@/pages/QnaListPage.vue";
import ChatNavComponent from "@/components/Chat/ChatNavComponent.vue";
import WikiRegisterPage from "@/pages/wiki/WikiRegisterPage.vue";
import ChatComponent from "@/components/Chat/ChatComponent.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/qna-list", component: QnaListPage },
    { path: "/wiki", component: WikiRegisterPage },
    { path: "/chat", component: ChatNavComponent },
    { path: "/chat/message", component: ChatComponent },

  ]
});

export default router;
