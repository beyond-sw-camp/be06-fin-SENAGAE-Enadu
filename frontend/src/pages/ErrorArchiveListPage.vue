<template>
  <div v-if="isLoading"></div>
  <div v-else class="custom-container">
    <TagComponent :tagTitle="'에러 아카이브'" :tagSubTitle="'당신의 에러 해결 방법을 공유해주세요'"/>
    <div class="errorarchive-inner">
      <SortTypeComponent @checkLatest="handleCheckLatest"
                         @checkLike="handleCheckLike"/>
      <div class="errorarchive-list-flex">
        <ErrorArchiveCardComponent
            v-for="errorarchiveCard in errorarchiveStore.errorarchiveCards"
            :key="errorarchiveCard.id"
            v-bind:errorarchiveCard="errorarchiveCard"
        />
      </div>
    </div>
  </div>
  <div class="errorarchive-bottom">
    <div v-if="isLoading"></div>
    <PaginationComponent v-else @updatePage="handlePageUpdate" :totalPage="totalPage"/>
  </div>
</template>

<script>
import {mapStores} from "pinia";
import { useErrorArchiveStore } from '@/store/useErrorArchiveStore';
import ErrorArchiveCardComponent from '@/components/errorarchive/ErrorArchiveCardComponent.vue';
import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import SortTypeComponent from "@/components/Common/SortTypeComponent.vue";
import TagComponent from "@/components/Common/TagComponent.vue";

export default {
  name: 'ErrorArchiveListPage',
  components: {
    TagComponent,
    ErrorArchiveCardComponent,
    SortTypeComponent,
    PaginationComponent,
  },
  data() {
    return {
      selectedSort: null,
      selectedPage: 0,
      page: 0,
      totalPage: 1,
      isLoading:true
    };
  },
  computed: {
    ...mapStores(useErrorArchiveStore),
  },
  mounted() {
    this.selectedSort = "latest";
    this.selectedPage = 1;
    this.getErrorArchiveList();
  },
  watch: {
    selectedSort() {
      this.errorarchiveStore.getErrorArchiveList(this.selectedSort, this.selectedPage-1);
    },
    selectedPage() {
      this.errorarchiveStore.getErrorArchiveList(this.selectedSort, this.selectedPage-1);
    },
  },
  methods: {
    handleCheckLatest() {
      this.selectedSort = "latest";    
    },
    handleCheckLike() {
      this.selectedSort = "like";
    },
    handlePageUpdate(newPage) {
      this.selectedPage = newPage;
      this.errorarchiveStore.getErrorArchiveList(this.selectedSort, this.selectedPage - 1);
    },
    updatePage(page){
      this.page = page-1
      if(this.$route.path.endsWith("info")){
        this.getErrorArchiveHistory();
      } else if (this.$route.path.endsWith("rank")){
        this.getPointRankList();
      }
    },
    async getErrorArchiveList(){
      await this.errorarchiveStore.getErrorArchiveList(this.selectedSort, this.selectedPage - 1);
      if (this.errorarchiveStore.errorarchiveCards.length !== 0){
        this.totalPage = this.errorarchiveStore.errorarchiveCards[0].totalPage;
        console.log("total"+this.totalPage);
      }
      this.isLoading=false;
    }
  },
};
</script>

<style scoped>
.errorarchive-top{
 
 
  padding-bottom: 50px;
  align-content: center;
  align-items: center;

}
#main-title {
  text-align: center;
  font-size: 40px;
}

#sub-title {
  text-align: center;
  font-size: 25px;
}
.errorarchive-inner {
  width: auto;
  height: max-content;
  background-color: #fff;
}
.errorarchive-list-flex {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    grid-auto-rows: auto;
    gap: 26px 36px;
    justify-items: stretch;
    max-width: 100%;
    margin: 0 auto
}
.errorarchive-bottom {
  height: 70px;
  display: grid;
  background-color: #ffffff;
  justify-content: center;
  align-content: space-around;
}



</style>
