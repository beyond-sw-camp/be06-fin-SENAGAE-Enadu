import { createRouter, createWebHistory } from "vue-router";
import ErrorBoardRegisterPage from "@/pages/errorboard/ErrorBoardRegisterPage.vue";



const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/errorboard", component: ErrorBoardRegisterPage }
  ]
});

export default router;