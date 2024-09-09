<template>
  <HeaderComponent v-if="$route.meta.showHeader !== false" />
  <router-view></router-view>
</template>
<script>
  import HeaderComponent from "./components/header/HeaderComponent.vue";

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
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }
  body {
    font-family: 'Arial', sans-serif;
    background-color: #fff;
    --main-color: #1e88e5;
  }
</style>