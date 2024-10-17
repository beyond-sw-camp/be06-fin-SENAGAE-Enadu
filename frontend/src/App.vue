<template>
  <HeaderComponent v-if="$route.meta.showHeader !== false"/>
  <router-view></router-view>
</template>
<script>
import HeaderComponent from "@/components/Header/HeaderComponent.vue";


export default {

  name: "App",
  components: {
    HeaderComponent,
  }

  ,
}

;
</script>
<style>
* {
  font-family: "Noto Sans KR", sans-serif !important;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {

  background-color: #fff;
  --main-color: #1e88e5;
}

i {
  font-family: "Font Awesome 6 Free" !important;
}

.custom-container {
  width: 75%;
  max-width: 1400px;
  margin: 50px auto;
  padding: 30px;
  border-radius: 10px;
}

.custom-container.loading {
  pointer-events: none;
}

#__next.loading {
  pointer-events: none;
}

.newbie {
  color: #a1a1a1;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.apprentice {
  color: #ff9800;
  text-shadow: 2px 2px 2px rgba(255, 152, 0, 0.15);
}

.pro {
  color: #4caf50;
  text-shadow: 2px 2px 2px rgba(76, 175, 80, 0.15);
}

.master {
  color: #1e88e5;
  text-shadow: 2px 2px 2px rgba(30, 136, 229, 0.15);
}

.god {
  color: #e91e63;
  text-shadow: 2px 2px 2px rgba(233, 30, 99, 0.15);
}
</style>