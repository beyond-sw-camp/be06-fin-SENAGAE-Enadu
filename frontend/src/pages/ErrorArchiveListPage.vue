<template>
  <div class="custom-container">
    <TagComponent :tagTitle="'에러 아카이브'" :tagSubTitle="'당신의 에러 해결 방법을 공유해주세요'"/>
    <div class="errorarchive-inner">
      <div v-if="isLoading"></div>
      <SearchComponent v-else @checkLatest="handleCheckLatest"
                       @checkLike="handleCheckLike"
      />
      <div class="errorarchive-list-flex">
        <LoadingComponent v-if="isLoading" style="margin-top: 150px"/>
        <ErrorArchiveCardComponent v-else
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
import {useErrorArchiveStore} from '@/store/useErrorArchiveStore';
import ErrorArchiveCardComponent from '@/components/errorarchive/ErrorArchiveCardComponent.vue';
import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import TagComponent from "@/components/Common/TagComponent.vue";
import SearchComponent from "@/components/Common/SearchComponent.vue";
import LoadingComponent from "@/components/Common/LoadingComponent.vue";

export default {
  name: 'ErrorArchiveListPage',
  components: {
    LoadingComponent,
    SearchComponent,
    TagComponent,
    ErrorArchiveCardComponent,
    PaginationComponent,
  },
  data() {
    return {
      selectedSort: "latest",
      selectedPage: 1,
      page: 0,
      totalPage: 1,
      isLoading: true,
      searchRequest: null,
    };
  },
  computed: {
    ...mapStores(useErrorArchiveStore),
  },
  mounted() {
    if (Object.keys(this.$route.query).length === 0){
      this.getErrorArchiveList();
    } else {
      this.searchErrorArchiveList();
    }
  },
  watch: {
    '$route.query': {
      immediate: true,
      handler(newQuery) {
        if (Object.keys(newQuery).length !== 0) {
          this.searchErrorArchiveList();
        } else {
          this.selectedPage = 1;
          this.selectedSort = "latest";
          this.searchRequest= null;
          this.errorarchiveStore.getErrorArchiveList(this.selectedSort, this.selectedPage - 1);
        }
      }
    },
    selectedSort() {
      this.isLoading = true;
      if(this.searchRequest === null) {
        this.errorarchiveStore.getErrorArchiveList(this.selectedSort, this.selectedPage - 1);
      } else {
        this.searchRequest.sort = this.selectedSort;
        this.errorarchiveStore.searchErrorArchive(this.searchRequest);
      }
      this.isLoading = false;
    },
    selectedPage() {
      this.isLoading = true;
      if (this.searchRequest === null) {
        this.errorarchiveStore.getErrorArchiveList(this.selectedSort, this.selectedPage - 1);
      } else {
        this.searchRequest.page = this.selectedPage - 1;
        this.errorarchiveStore.searchErrorArchive(this.searchRequest);
      }
      this.isLoading = false;
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
    },
    async getErrorArchiveList() {
      this.isLoading = true;
      await this.errorarchiveStore.getErrorArchiveList(this.selectedSort, this.selectedPage - 1);
      if (this.errorarchiveStore.errorarchiveCards.length !== 0) {
        this.totalPage = this.errorarchiveStore.errorarchiveCards[0].totalPage;
      }
      this.isLoading = false;
      this.searchRequest = null;
    },
    async searchErrorArchiveList(){
      this.isLoading = true;
      const request = {
        keyword:  this.$route.query.keyword,
        categoryId: this.$route.query.selectedSubCategoryId !== 0 ? this.$route.query.selectedSubCategoryId : this.$route.query.selectedCategory,
        type: this.$route.query.type,
        sort: this.$route.query.sort,
        page: this.$route.query.page,
        size: this.$route.query.size,
      }
      await this.errorarchiveStore.searchErrorArchive(request);
      if (this.errorarchiveStore.errorarchiveCards.length !== 0) {
        this.totalPage = this.errorarchiveStore.errorarchiveCards[0].totalPage;
      }
      this.searchRequest = request;
      this.isLoading = false;
    }
  },
};
</script>

<style scoped>
.errorarchive-top {


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
