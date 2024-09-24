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
import EmailVerifyPage from "@/pages/EmailVerifyPage.vue";
import WikiUpdatePage from "@/pages/WikiUpdatePage.vue";
import ErrorArchiveListPage from "@/pages/ErrorArchiveListPage.vue";
import {useChatStore} from "@/store/useChatStore";
import MypagePage from "@/pages/MypagePage.vue";
import InfoComponent from "@/components/Mypage/Info/InfoComponent.vue";
import HistoryListComponent from "@/components/Mypage/History/HistoryListComponent.vue";
import ScrapListComponent from "@/components/Mypage/Scrap/ScrapListComponent.vue";
import ErrorArchiveDetailPage from "@/pages/ErrorArchiveDetailPage.vue";
import WikiVersionListPage from '@/pages/WikiVersionListPage.vue';
import WikiVersionDetailPage from '@/pages/WikiVersionDetailPage.vue'; 
import WikiListPage from "@/pages/WikiListPage.vue";
import MainPage from "@/pages/MainPage.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/login", component: LoginPage, meta: { showHeader: false }},
    { path: "/qna/list", component: QnaListPage },
    { path: "/qna/register", component: QnaRegisterComponent },
    { path: '/qna/detail/:id', component: QnaDetailPage },
    { path: "/wiki/register", component: WikiRegisterPage },
    { path: "/chat", component: ChatPage },
    { path: "/errorarchive", component: ErrorArchiveRegisterPage },
    { path: "/errorarchive/register", component: ErrorArchiveRegisterPage },
    { path: "/errorarchive/list", component: ErrorArchiveListPage },
    { path: "/oauth", component: OAuthLoginPage, meta: { showHeader: false } },
    { path: "/point", component: PointPage, children: [
        { path: "info", component: PointInfoComponent },
        { path: "rank", component: PointRankingComponent },
      ]},
    { path: "/wiki/detail", component: WikiDetailPage },
    { path: "/email/verify", component: EmailVerifyPage },
   
    { path: "/wiki/detail", name: "WikiDetail", component: WikiDetailPage },
    { path: "/wiki/update", name: "WikiUpdate", component: WikiUpdatePage },
    { path: "/wiki/version/list", component: WikiVersionListPage },
    { path: "/wiki/version/detail", component: WikiVersionDetailPage },
    { path: "/wiki/list", component: WikiListPage },
    { path: "/mypage", component: MypagePage, children: [
        { path: "info", component: InfoComponent },
        { path: "history", component: HistoryListComponent },
        { path: "scrap", component: ScrapListComponent }
      ] },
    { path:"/errorarchive/detail", component: ErrorArchiveDetailPage},
    { path:"/", component: MainPage},
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
