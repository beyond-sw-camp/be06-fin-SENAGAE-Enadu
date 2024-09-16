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
import {useChatStore} from "@/store/useChatStore";
import MypagePage from "@/pages/MypagePage.vue";
import InfoComponent from "@/components/Mypage/Info/InfoComponent.vue";
import UserLogComponent from "@/components/Mypage/UserLogComponent.vue";
import ScrapListComponent from "@/components/Mypage/ScrapListComponent.vue";

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
    { path: "/oauth", component: OAuthLoginPage, meta: { showHeader: false } },
    { path: "/point", component: PointPage, children: [
        { path: "info", component: PointInfoComponent },
        { path: "rank", component: PointRankingComponent },
      ]},
    { path: "/wiki/detail", component: WikiDetailPage },
    { path: "/mypage", component: MypagePage, children: [
        { path: "info", component: InfoComponent },
        { path: "history", component: UserLogComponent },
        { path: "scrap", component: ScrapListComponent }
      ] }
  ]
});

router.beforeEach((to, from, next) => {
    if (from.path === "/chat") { // /chat 페이지를 벋어날 때 소켓 연결 해재
        const chatStore = useChatStore();
        chatStore.disconnect();
    }
    next();
});

export default router;
