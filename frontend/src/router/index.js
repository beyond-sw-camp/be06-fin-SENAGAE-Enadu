import { createRouter, createWebHistory } from "vue-router";
import WikiRegisterPage from "@/pages/wiki/WikiRegisterPage.vue";


const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/wiki", component: WikiRegisterPage }
    , 
  ]
});

export default router;