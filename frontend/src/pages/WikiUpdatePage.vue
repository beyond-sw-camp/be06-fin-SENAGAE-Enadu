<template>
    <div class="custom-container">
      <div id="root">
        <div class="content-wrapper">
          <WikiUpdateComponent />
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import WikiUpdateComponent from '@/components/wiki/WikiUpdateComponent.vue';
  
  export default {
    name: "WikiUpdatePage",
    data() {
        return {
            isLoading: true
        }
    },
    components: {
      WikiUpdateComponent
    }
  }
  </script>
