<template>
  <TagComponent :tagTitle="'QnA'" :tagSubTitle="'당신의 에러를 해결해보세요'"/>
  <div v-if="!isLoading" class="custom-container" style="margin-top: 0; padding-top: 0;">
    <div class="qna-inner">
      <SearchComponent @checkLatest="handleCheckLatest"
                       @checkLike="handleCheckLike"
                       @search="handleSearch"
      />
      <div class="qna-list-flex" v-show="!isSearched">
        <QnaCardComponent
            v-for="qnaCard in qnaStore.qnaCards"
            :key="qnaCard.id"
            v-bind:qnaCard="qnaCard"
        />
      </div>
      <div class="qna-list-flex" v-show="isSearched">
        <QnaCardComponent
            v-for="qnaCard in qnaStore.qnaSearchedCards"
            :key="qnaCard.id"
            v-bind:qnaCard="qnaCard"
            @click="goToDetail(qnaCard.id)"
        />
      </div>
    </div>
    <div class="qna-bottom">
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

export default {
  name: "QnaListPage",
  data() {
    return {
      isLoading: true,
      isSearched: false,

      selectedSort: null,
      selectedPage: 0,

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
    this.isSearched = false;
    this.checking();
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

        await useQnaStore().qnaSearch(this.searchQuery, this.categoryId, this.selectedType, this.selectedSort, this.selectedPage);

        this.totalPages = useQnaStore().searchedTotalPage || 1;
        this.isLoading = false;
      }
    },
    async checking() {
      if (!this.isSearched) {
        if (useQnaStore().qnaCards.length > 0) {
          this.totalPages = await useQnaStore().totalPage || 1;
        } else {
          this.totalPages = 1;
        }
      } else {
        if (useQnaStore().qnaSearchedCards.length > 0) {
          this.totalPages = await useQnaStore().searchedTotalPage || 1;
        } else {
          this.totalPages = 1;
        }
      }
    },
  },
  watch: {
    async selectedSort() {
      if (!this.isSearched) {
        await this.qnaStore.getQnaList(this.selectedSort, this.selectedPage - 1);
        this.totalPages = useQnaStore().totalPage || 1;
        this.isLoading = false;
      } else {
        await useQnaStore().qnaSearch(this.searchQuery, this.categoryId, this.selectedType, this.selectedSort, this.selectedPage);
        this.totalPages = useQnaStore().searchedTotalPage || 1;
        this.isLoading = false;
      }
    },
    async selectedPage() {
      if (!this.isSearched) {
        this.totalPages = useQnaStore().totalPage || 1;
        this.isLoading = false;
      } else {
        this.totalPages = useQnaStore().searchedTotalPage || 1;
        this.isLoading = false;
      }
    },
  },
  components: {
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
