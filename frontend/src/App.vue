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

</style>