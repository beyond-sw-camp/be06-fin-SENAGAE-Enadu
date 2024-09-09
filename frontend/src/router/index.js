import { createRouter, createWebHistory } from "vue-router";
import LoginPage from "@/pages/LoginPage.vue";
import QnaListPage from "@/pages/QnaListPage.vue";
import ChatNavComponent from "@/components/Chat/ChatNavComponent.vue";
import WikiRegisterPage from "@/pages/wiki/WikiRegisterPage.vue";
import QnaRegisterComponent from "@/components/qna/QnaRegisterComponent.vue";
import QnaDetailPage from "@/pages/QnaDetailPage.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/login", component: LoginPage, meta: { showHeader: false }},
    { path: "/qna-list", component: QnaListPage },
    { path: "/qna/register", component: QnaRegisterComponent },
    { path: '/detail/:id', component: QnaDetailPage },
    { path: "/wiki", component: WikiRegisterPage },
    { path: "/chat", component: ChatNavComponent },

  ]
});

export default router;
