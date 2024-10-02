<template>
  <TagComponent :tagTitle="'포인트 정보'" :tagSubTitle="'포인트 정보를 확인하세요'"/>
  <div class="custom-container" style="margin-top: 0;">
    <router-view v-show="isLoading" />
    <div class="pagination-container" v-if="isLoading">
      <PaginationComponent @updatePage="updatePage" :nowPage="page + 1" :totalPage="totalPage"/>
    </div>
  </div>


</template>

<script>

import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import {mapStores} from "pinia";
import {usePointStore} from "@/store/usePointStore";
import TagComponent from "@/components/Common/TagComponent.vue";

export default {
  name: "PointPage" ,
  components: {TagComponent, PaginationComponent},
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
      if(this.$route.path.endsWith("info")){
        this.getPointHistory();
      } else if (this.$route.path.endsWith("rank")){
        this.getPointRankList();
      }
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
      this.getPointHistory();
    } else if (this.$route.path.endsWith("rank")){
      this.getPointRankList();
    }

  },
}
</script>

<style scoped>
.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

</style>

