<template>
  <div id="root">
    <div class="custom-container">
      <div class="css-ey8mg8" style="">
        <ErrorArchiveRegisterComponent @register="handleClick"/>
      </div>
    </div>
  </div>
</template>

<script>
import { mapStores } from "pinia";
import { useErrorArchiveStore } from "@/store/useErrorArchiveStore";
import ErrorArchiveRegisterComponent
  from '@/components/errorarchive/ErrorArchiveRegisterComponent.vue';

export default {
  name: "ErrorArchiveRegisterPage",
  components: {
    ErrorArchiveRegisterComponent
  },
  data() {
    return {
      showSubModal: false,
      showSuperModal: false
    };
  },
  computed: {
    ...mapStores(useErrorArchiveStore) // Pinia store 연결
  },
  methods: {
    async handleClick(errorArchive) {
      console.log('Received data: ', errorArchive);
      try {
        console.log("Data to submit: ", errorArchive);
        await this.errorArchiveStore.registerErrorArchive();
        console.log('Registration successful');
      }
      catch (error) {
        console.error('Registration error:', error);
      }
    },
  }
};
</script>
<style scoped>
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