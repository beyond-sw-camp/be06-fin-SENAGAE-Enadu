import { createRouter, createWebHistory } from "vue-router";
import QnaListPage from "@/pages/QnaListPage.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/qna-list",
      component: QnaListPage,
    },
  ],
});

export default router;
