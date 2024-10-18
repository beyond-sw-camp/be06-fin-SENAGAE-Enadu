import { createRouter, createWebHistory } from "vue-router";
import LoginPage from "@/pages/LoginPage.vue";
import QnaListPage from "@/pages/QnaListPage.vue";
import WikiRegisterPage from "@/pages/WikiRegisterPage.vue";
import ChatPage from "@/pages/ChatPage.vue";
import QnaRegisterComponent from "@/components/Qna/Register/QnaRegisterComponent.vue";
import ErrorArchiveRegisterPage from "@/pages/ErrorArchiveRegisterPage.vue";
import OAuthLoginPage from "@/pages/OAuthLoginPage.vue";
import PointPage from "@/pages/PointPage.vue";
import WikiDetailPage from "@/pages/WikiDetailPage.vue";
import QnaDetailPage from "@/pages/QnaDetailPage.vue";
import EmailVerifyPage from "@/pages/EmailVerifyPage.vue";
import WikiUpdatePage from "@/pages/WikiUpdatePage.vue";
import ErrorArchiveListPage from "@/pages/ErrorArchiveListPage.vue";
import { useChatStore } from "@/store/useChatStore";
import MypagePage from "@/pages/MypagePage.vue";
import InfoComponent from "@/components/Mypage/Info/InfoComponent.vue";
import HistoryListComponent from "@/components/Mypage/History/HistoryListComponent.vue";
import ScrapListComponent from "@/components/Mypage/Scrap/ScrapListComponent.vue";
import ErrorArchiveDetailPage from "@/pages/ErrorArchiveDetailPage.vue";
import WikiVersionListPage from '@/pages/WikiVersionListPage.vue';
import WikiVersionDetailPage from '@/pages/WikiVersionDetailPage.vue';
import WikiListPage from "@/pages/WikiListPage.vue";
import MainPage from "@/pages/MainPage.vue";
import QnaEditComponent from "@/components/Qna/Refactor/QnaEditComponent.vue";
import UserLogPage from "@/pages/UserLogPage.vue";
import ErrorArchiveUpdatePage from "@/pages/ErrorArchiveUpdatePage.vue";
import { useUserStore } from "@/store/useUserStore";
import RankingPage from "@/pages/RankingPage.vue";
import TotalSearchPage from "@/pages/TotalSearchPage.vue";
import ExceptionComponent from '@/components/Common/ExceptionComponent.vue';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/login", component: LoginPage, meta: { showHeader: false } },
    { path: "/qna/list", component: QnaListPage },
    { path: "/qna/register", component: QnaRegisterComponent },
    { path: "/qna/edit/:id", component: QnaEditComponent },
    { path: "/qna/detail/:id", component: QnaDetailPage },
    {
      path: "/wiki/register",
      component: WikiRegisterPage,
      beforeEnter: async (to, from, next) => {
        const userStore = useUserStore();
        if (!userStore.isLoggedIn) {
          return next('/login'); //url입력으로 등록 페이지 요청시 리다이렉트
        }
        next();
      }
    },
    { path: "/chat", component: ChatPage },
    { path: "/errorarchive", component: ErrorArchiveRegisterPage },
    { path: "/errorarchive/register", component: ErrorArchiveRegisterPage },
    { path: "/errorarchive/list", component: ErrorArchiveListPage },
    { path: "/errorarchive/update", name: "ErrorArchiveUpdate", component: ErrorArchiveUpdatePage },
    { path: "/oauth", component: OAuthLoginPage, meta: { showHeader: false } },
    { path: "/point", component: PointPage },
    { path: "/ranking", component: RankingPage },
    { path: "/email/verify", component: EmailVerifyPage },

    { path: "/wiki/detail", name: "WikiDetail", component: WikiDetailPage },
    { path: "/wiki/update", name: "WikiUpdate", component: WikiUpdatePage },
    { path: "/wiki/version/list", component: WikiVersionListPage },
    { path: "/wiki/version/detail", component: WikiVersionDetailPage },
    { path: "/wiki/list", component: WikiListPage },
    {
      path: "/mypage", component: MypagePage, children: [
        { path: "info", component: InfoComponent },
        { path: "history", component: HistoryListComponent },
        { path: "scrap", component: ScrapListComponent }
      ]
    },
    { path: "/user/log/:nickname", component: UserLogPage },
    { path: "/errorarchive/detail", component: ErrorArchiveDetailPage },
    { path: "/", component: MainPage },
    { path: "/search", component: TotalSearchPage },
    {
      path: '/:pathMatch(.*)*',
      name: 'CatchAll',
      component: ExceptionComponent,
    },
  ]
});

router.beforeEach((to, from, next) => {
  if (to.path === "/wiki/register" && to.fullPath.includes('?')) {
    const cleanPath = to.path; // 쿼리 파라미터 제거
    next({ path: cleanPath, replace: true });
  } else {
    next();
  }
});

router.beforeEach((to, from, next) => {
  if (from.path === "/chat") { // /chat 페이지를 벋어날 때 소켓 연결 해재
    const chatStore = useChatStore();
    chatStore.disconnect();
  }
  next();
});

export default router;
