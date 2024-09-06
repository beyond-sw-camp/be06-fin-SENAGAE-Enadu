import { createRouter, createWebHistory } from "vue-router";
import QnaListPage from "@/pages/QnaListPage.vue";
import WikiRegisterPage from "@/pages/wiki/WikiRegisterPage.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/qna-list",
      component: QnaListPage,
    },
    { path: "/wiki", component: WikiRegisterPage },
  ],
});

export default router;
