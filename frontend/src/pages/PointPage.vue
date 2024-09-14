<template>
  <div class="custom-container" style="text-align:center;">
    <router-view v-show="isLoading" />
    <div v-if="!isLoading"></div>
    <pagination-component v-else style="margin-top: 20px;" @updatePage="updatePage" :totalPage="totalPage"/>
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
      totalPage: 1
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
      if (this.pointStore.pointHistoryList.length !== 0){
        this.totalPage = this.pointStore.pointHistoryList[0].totalPage;
      }
      this.isLoading = true;
    },
    async getPointRankList(){
      await this.pointStore.getPointRankList(this.page);
      if (this.pointStore.pointRankingList.length !== 0){
        this.totalPage = this.pointStore.pointRankingList[0].totalPage;
      }
      this.isLoading = true;
    }

  },
  mounted() {
    this.getMyRank();
    if(this.$route.path.endsWith("info")){
      this.getPointHistory(this.page);
    } else if (this.$route.path.endsWith("rank")){
      this.getPointRankList(this.page);
    }

  },
}
</script>

<style scoped>

</style>

