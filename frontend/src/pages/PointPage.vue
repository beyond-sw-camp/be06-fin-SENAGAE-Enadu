<template>
  <div class="custom-container" style="text-align:center;">
    <router-view v-show="isLoading"/>
    <div v-if="!isLoading"></div>
    <pagination-component v-else style="margin-top: 20px;" @updatePage="updatePage" :totalPage="pointStore.pointHistoryList[0].totalPage"/>
  </div>

</template>

<script>

import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import {mapStores} from "pinia";
import {usePointStore} from "@/store/usePointStore";

export default {
  name: "PointPage" ,
  components: {PaginationComponent},
  computed: {
    ...mapStores(usePointStore),
  },
  data(){
    return {
      isLoading: false,
      page: 0,
    }
  },
  methods: {
    async getMyRank(){
      await this.pointStore.getMyPointRanking();
    },
    updatePage(page){
      this.page = page-1
      this.getPointHistory();
    },
    async getPointHistory() {
      await this.pointStore.getPointHistory(this.page);
      this.isLoading = true;
    },

  },
  mounted() {
    this.getMyRank();
    this.getPointHistory(this.page);

  },
}
</script>

<style scoped>

</style>

