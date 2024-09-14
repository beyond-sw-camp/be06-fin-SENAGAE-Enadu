import {createRouter, createWebHistory} from "vue-router";
import LoginPage from "@/pages/LoginPage.vue";
import QnaListPage from "@/pages/QnaListPage.vue";
import WikiRegisterPage from "@/pages/WikiRegisterPage.vue";
import ChatPage from "@/pages/ChatPage.vue";
import QnaRegisterComponent from "@/components/qna/QnaRegisterComponent.vue";
import OAuthLoginPage from "@/pages/OAuthLoginPage.vue";
import PointPage from "@/pages/PointPage.vue";
import PointInfoComponent from "@/components/Point/PointInfoComponent.vue";
import PointRankingComponent from "@/components/Point/PointRankingComponent.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/login", component: LoginPage, meta: { showHeader: false }},
    { path: "/qna/list", component: QnaListPage },
    { path: "/qna/register", component: QnaRegisterComponent },
    { path: "/wiki", component: WikiRegisterPage },
    { path: "/chat", component: ChatPage },
    { path: "/oauth", component: OAuthLoginPage, meta: { showHeader: false } },
    { path: "/point", component: PointPage, children: [
        { path: "info", component: PointInfoComponent },
        { path: "rank", component: PointRankingComponent },
      ]},
  ]
});

export default router;
