<template>
  <TagComponent :tagTitle="'QnA'" :tagSubTitle="'당신의 에러를 해결해보세요'"/>
  <div class="custom-container" style="margin-top: 0; padding-top: 0;">
    <div class="qna-inner">


      <SearchComponent @checkLatest="handleCheckLatest"
                       @checkLike="handleCheckLike"
                       @search="handleSearch"
      />

      <div style="display: flex">
        <QnaResolvedComponent @changeResolved="handleResolved" />
      </div>
      <LoadingComponent v-if="isLoading" style="margin-top: 15rem;"/>
      <div v-else>
        <div class="qna-list-flex" v-if="!isSearched">
          <QnaCardComponent
                            v-for="qnaCard in qnaStore.qnaCards"
                            :key="qnaCard.id"
                            v-bind:qnaCard="qnaCard"
          />
        </div>
        <div class="qna-list-flex" v-else>
          <QnaCardComponent
                            v-for="qnaCard in qnaStore.qnaSearchedCards"
                            :key="qnaCard.id"
                            v-bind:qnaCard="qnaCard"
                            @click="goToDetail(qnaCard.id)"
          />
        </div>
      </div>
    </div>
    <div v-if="!isLoading && totalPages > 0" class="qna-bottom">
      <PaginationComponent @updatePage="handlePageUpdate" :nowPage="selectedPage" :totalPage="totalPages"/>
    </div>
  </div>
</template>

<script>
import {mapStores} from "pinia";
import {useQnaStore} from "@/store/useQnaStore";
import QnaCardComponent from "@/components/Qna/List/QnaListCardComponent.vue";
import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import SearchComponent from "@/components/Common/SearchComponent.vue";
import TagComponent from "@/components/Common/TagComponent.vue";
import QnaResolvedComponent from "@/components/Qna/QnaResolvedComponent.vue";
import LoadingComponent from "@/components/Common/LoadingComponent.vue";

export default {
  name: "QnaListPage",
  data() {
    return {
      isLoading: false,
      isSearched: false,

      selectedSort: "latest",
      selectedPage: 0,
      selectedSolvedStatus: "",

      searchQuery: "",
      categoryId: 0,
      selectedSuperCategoryId: 0,
      selectedSubCategoryId: 0,
      selectedType: "",

      totalPages: 1,
    };
  },
  computed: {
    ...mapStores(useQnaStore),
  },
  mounted() {
    this.selectedSort = "latest";
    this.selectedPage = 1;
    this.selectedSolvedStatus = "ALL"
    this.isSearched = false;
    this.fetchQnaList();
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
      this.fetchQnaList();
    },
    handleResolved(newStatus) {
      this.selectedSolvedStatus = newStatus;
    },
    async handleSearch(data) {
      if (data !== null) {
        this.isSearched = true;
        this.isLoading = true;

        const {searchQuery, selectedSuperCategoryId, selectedSubCategoryId, selectedType} = data;

        this.searchQuery = searchQuery;
        this.selectedSuperCategoryId = selectedSuperCategoryId;
        this.selectedSubCategoryId = selectedSubCategoryId;
        this.selectedType = selectedType;

        this.categoryId = (this.selectedSubCategoryId > 0) ? this.selectedSubCategoryId : this.selectedSuperCategoryId;
        console.log("super" + this.selectedSuperCategoryId);
        console.log("sub" + this.selectedSubCategoryId);
        console.log("main" + this.categoryId);

        await useQnaStore().qnaSearch(this.searchQuery, this.categoryId, this.selectedType, this.selectedSort, this.selectedPage, this.selectedSolvedStatus);

        this.totalPages = useQnaStore().searchedTotalPage || 1;
        this.isLoading = false;
      }
    },
    async fetchQnaList() {
      this.isLoading = true;
      if (!this.isSearched) {
        await useQnaStore().getQnaList(this.selectedSort, this.selectedPage - 1, this.selectedSolvedStatus);
        this.totalPages = useQnaStore().totalPage || 1;
      } else {
        await useQnaStore().qnaSearch(this.searchQuery, this.categoryId, this.selectedType, this.selectedSort, this.selectedPage, this.selectedSolvedStatus);
        this.totalPages = useQnaStore().searchedTotalPage || 1;
      }
      this.isLoading = false;
    }
  },
  watch: {
    async selectedSort() {
      await this.fetchQnaList();
    },
    async selectedSolvedStatus() {
      this.isLoading = true;
      this.selectedPage = 1;
      if (!this.isSearched) {
        await useQnaStore().getQnaList(this.selectedSort, this.selectedPage - 1, this.selectedSolvedStatus);
        this.totalPages = useQnaStore().totalPage || 1;
      }
      else {
        await useQnaStore().qnaSearch(this.searchQuery, this.categoryId, this.selectedType, this.selectedSort, this.selectedPage, this.selectedSolvedStatus);
        this.totalPages = useQnaStore().searchedTotalPage || 1;
      }
      this.isLoading = false;
    },
  },
  components: {
    LoadingComponent,
    QnaResolvedComponent,
    TagComponent,
    QnaCardComponent,
    PaginationComponent,
    SearchComponent,
  },
}
</script>


<style>
.qna-bottom {
  margin-top: 40px;
  height: 70px;
  display: grid;
  background-color: #ffffff;
  justify-content: center;
  align-content: space-around;
}

#main-title {
  text-align: center;
  font-size: 40px;
}

#sub-title {
  text-align: center;
  font-size: 25px;
}

.qna-list-flex {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(230px, 1fr));
  grid-auto-rows: auto;
  gap: 26px 36px;
  justify-items: stretch;
  max-width: 100%;
  margin: 0 auto
}

.qna-inner {
  width: auto;
  height: max-content;
  //margin: 20px 100px;
  //padding: 10px;
  background-color: #fff;
}


</style>
