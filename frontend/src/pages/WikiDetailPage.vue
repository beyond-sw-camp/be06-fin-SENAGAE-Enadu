<template>
    <div class="custom-container">
      <div id="root">
        <div class="content-wrapper">
          <WikiDetailComponent />
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import WikiDetailComponent from '@/components/wiki/WikiDetailComponent.vue';
  
  export default {
    name: "WikiDetailPage",
    components: {
      WikiDetailComponent
    }
  }
  </script>
  
  <style scoped>
  .custom-container {
    padding: 20px;
  }
  .content-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
  }
  </style>
  