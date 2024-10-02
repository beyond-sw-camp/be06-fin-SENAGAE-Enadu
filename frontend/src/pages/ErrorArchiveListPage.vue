<template>
  <router-link to="/errorarchive/list"><TagComponent :tagTitle="'에러 아카이브'" :tagSubTitle="'당신의 에러 해결 방법을 공유해주세요'"/></router-link>
  <div class="custom-container" style="margin-top: 0; padding-top: 0;">
    <div class="errorarchive-inner">
      <div v-if="isLoading && isLoading2"></div>
      <SearchComponent v-else @checkLatest="handleCheckLatest"
                       @checkLike="handleCheckLike"
      />
      <div class="errorarchive-list-flex">
        <LoadingComponent v-if="isLoading && isLoading2" style="margin-top: 150px"/>
        <ErrorArchiveCardComponent v-else
            v-for="errorarchiveCard in errorarchiveStore.errorarchiveCards"
            :key="errorarchiveCard.id"
            v-bind:errorarchiveCard="errorarchiveCard"
        />
      </div>
    </div>

  <div class="errorarchive-bottom">
    <div v-if="isLoading && isLoading2"></div>
    <PaginationComponent v-else @updatePage="handlePageUpdate" :nowPage="selectedPageAndSort.page" :totalPage="totalPage"/>
  </div>
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
      selectedPageAndSort: {
        sort: "latest",
        page: 1,
      },
      page: 0,
      totalPage: 1,
      isLoading: true,
      isLoading2: true,
      searchRequest: null,
      isUpdating: false ,// 감지 여부 제어용 플래그
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
      immediate: false,
      handler(newQuery) {
        if (Object.keys(newQuery).length !== 0) {
          this.searchErrorArchiveList();
        } else {
          this.searchRequest = null;
          this.isLoading2 = true;
          this.isLoading = true;
          if (this.selectedPageAndSort.page === 1 && this.selectedPageAndSort.sort ==="latest") {
            this.getErrorArchiveList();
          } else {
            this.selectedPageAndSort.page = 1;
            this.selectedPageAndSort.sort = "latest";
          }
        }
      }
    },
    selectedPageAndSort: {
      async handler() {
        if (this.isUpdating){
          return;
        }
        this.isLoading = true;
        if (this.searchRequest === null) {
          await this.getErrorArchiveList();
        } else {
          this.searchRequest.page = this.selectedPageAndSort.page - 1;
          this.searchRequest.sort = this.selectedPageAndSort.sort;
          await this.errorarchiveStore.searchErrorArchive(this.searchRequest);
        }
        this.isLoading = false;
      },
      deep:true
    },
  },
  methods: {
    handleCheckLatest() {
      this.isLoading2 = false;
      this.selectedPageAndSort.sort = "latest";
    },
    handleCheckLike() {
      this.isLoading2 = false;
      this.selectedPageAndSort.sort = "like";
    },
    handlePageUpdate(newPage) {
      this.selectedPageAndSort.page = newPage;
      if (newPage % 5 === 0 || newPage % 5 === 1) {
        this.isLoading2 = true;
      }
      this.isLoading2 = false;
    },
    async getErrorArchiveList() {
      this.isLoading = true;
      await this.errorarchiveStore.getErrorArchiveList(this.selectedPageAndSort.sort, this.selectedPageAndSort.page - 1);
      if (this.errorarchiveStore.errorarchiveCards.length !== 0) {
        this.totalPage = this.errorarchiveStore.errorarchiveCards[0].totalPage;
      }
      this.isLoading = false;
      this.searchRequest = null;
    },
    async searchErrorArchiveList(){ // 검색 처음 했을 때
      this.isLoading = true;
      this.isLoading2 = true;
      this.isUpdating = true; // watch에서 감지 안돼도록
      this.selectedPageAndSort.sort = "latest";
      this.selectedPageAndSort.page = 1;

      const request = {
        keyword:  this.$route.query.keyword.trim(),
        categoryId: this.$route.query.selectedSubCategoryId != 0 ? this.$route.query.selectedSubCategoryId : this.$route.query.selectedCategory,
        type: this.$route.query.type,
        sort: this.selectedPageAndSort.sort,
        page: this.selectedPageAndSort.page-1,
        size: 16,
      }
      await this.errorarchiveStore.searchErrorArchive(request);
      if (this.errorarchiveStore.errorarchiveCards.length !== 0) {
        this.totalPage = this.errorarchiveStore.errorarchiveCards[0].totalPage;
      } else {
        this.totalPage = 1;
      }
      this.searchRequest = request;
      this.isLoading = false;
      this.isLoading2 = false;
      await this.$nextTick(); // DOM 업데이트가 끝날 때까지 기다림
      this.isUpdating = false; // 이렇게 안하면 watch에서 감지됨
    },
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
    margin-top: 20px;
    display: flex;
    justify-content: center;
}


</style>
