<template>
  <HeaderComponent v-if="$route.meta.showHeader !== false" />
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
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }
  body {
    font-family: 'Arial', sans-serif;
    background-color: #fff;
    --main-color: #1e88e5;
  }
  .custom-container {
    width: 75%;
    max-width: 1400px;
    margin: 50px auto;
    /* background-color: #fff; */
    padding: 30px;
    border-radius: 10px;
    /* box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); */
  }
</style>