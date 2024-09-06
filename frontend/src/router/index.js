import { createRouter, createWebHistory } from "vue-router";
import ChatNavComponent from "@/components/Chat/ChatNavComponent.vue";


const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/chat", component: ChatNavComponent }
  ]
});

export default router;