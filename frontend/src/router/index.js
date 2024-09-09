import { createRouter, createWebHistory } from "vue-router";
import QnaListPage from "@/pages/QnaListPage.vue";
import ChatNavComponent from "@/components/Chat/ChatNavComponent.vue";
import WikiRegisterPage from "@/pages/wiki/WikiRegisterPage.vue";
import QnaRegisterComponent from "@/components/qna/QnaRegisterComponent.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/qna-list", component: QnaListPage },
    { path: "/qna/register", component: QnaRegisterComponent },
    { path: "/wiki", component: WikiRegisterPage },
    { path: "/chat", component: ChatNavComponent },

  ]
});

export default router;
