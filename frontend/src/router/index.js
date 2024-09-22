import {createRouter, createWebHistory} from "vue-router";
import LoginPage from "@/pages/LoginPage.vue";
import QnaListPage from "@/pages/QnaListPage.vue";
import WikiRegisterPage from "@/pages/WikiRegisterPage.vue";
import ChatPage from "@/pages/ChatPage.vue";
import QnaRegisterComponent from "@/components/qna/QnaRegisterComponent.vue";
import ErrorArchiveRegisterPage from "@/pages/ErrorArchiveRegisterPage.vue";
import OAuthLoginPage from "@/pages/OAuthLoginPage.vue";
import PointPage from "@/pages/PointPage.vue";
import PointInfoComponent from "@/components/Point/PointInfoComponent.vue";
import PointRankingComponent from "@/components/Point/PointRankingComponent.vue";
import WikiDetailPage from "@/pages/WikiDetailPage.vue";
import QnaDetailPage from "@/pages/QnaDetailPage.vue";
import ErrorArchiveListPage from "@/pages/ErrorArchiveListPage.vue";
import EmailVerifyPage from "@/pages/EmailVerifyPage.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/login", component: LoginPage, meta: { showHeader: false }},
    { path: "/qna/list", component: QnaListPage },
    { path: "/qna/register", component: QnaRegisterComponent },
    { path: '/qna/detail/:id', component: QnaDetailPage },
    { path: "/wiki", component: WikiRegisterPage },
    { path: "/chat", component: ChatPage },
    { path: "/errorarchive", component: ErrorArchiveRegisterPage },
    { path: "/errorarchive/list", component: ErrorArchiveListPage },
    { path: "/oauth", component: OAuthLoginPage, meta: { showHeader: false } },
    { path: "/point", component: PointPage, children: [
        { path: "info", component: PointInfoComponent },
        { path: "rank", component: PointRankingComponent },
      ]},
    { path: "/wiki/detail", component: WikiDetailPage },
    { path: "/email/verify", component: EmailVerifyPage },
    
  ]
  
});

export default router;
