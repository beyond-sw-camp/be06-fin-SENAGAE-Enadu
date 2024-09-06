import { createRouter, createWebHistory } from "vue-router";
import ChatNavComponent from "@/components/Chat/ChatNavComponent.vue";
import WikiRegisterPage from "@/pages/wiki/WikiRegisterPage.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/chat", component: ChatNavComponent },
    { path: "/wiki", component: WikiRegisterPage }
  ]
});

export default router;