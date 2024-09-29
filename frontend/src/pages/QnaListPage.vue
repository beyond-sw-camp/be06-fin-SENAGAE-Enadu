<template>
  <div class="custom-container">
    <TagComponent :tagTitle="'QnA'" :tagSubTitle="'당신의 에러를 해결해보세요'"/>
    <div class="qna-inner">
      <SearchComponent @checkLatest="handleCheckLatest"
                       @checkLike="handleCheckLike"
                       @search="handleSearch"
      />
      <div v-if="isLoading"></div>
      <div v-else>
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
          />
        </div>
      </div>
    </div>
  </div>
  <div class="qna-bottom">
    <PaginationComponent @updatePage="handlePageUpdate" :nowPage="selectedPage + 1" :totalPage="totalPages"/>
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
      selectedSort: null,
      selectedPage: 0,
      isSearched: false,
      selectedSubCategory: "",
      searchQuery: "",
      selectedType: "",
      totalPages: 1,
    };
  },
  computed: {
    ...mapStores(useQnaStore),
    qnaCards() {
      return this.qnaStore.qnaCards;
    },
    qnaSearchedCards() {
      return this.qnaStore.qnaSearchedCards;
    },
  },
  mounted() {
    this.selectedSort = "latest";
    this.selectedPage = 1;
    this.isSearched = false;
    this.checking();
  },
  watch: {
    selectedSort() {
      this.qnaStore.getQnaList(this.selectedSort, this.selectedPage - 1);
      this.isLoading=false;
    },
    selectedPage() {
      this.qnaStore.getQnaList(this.selectedSort, this.selectedPage - 1);
      this.isLoading=false;
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
    handleSearch(data) {
      if (data !== null) {
        this.isSearched = true

        const {selectedSubCategory, searchQuery, selectedType} = data;
        this.selectedSubCategory = selectedSubCategory;
        this.searchQuery = searchQuery;
        this.selectedType = selectedType;

        useQnaStore().qnaSearch(this.selectedType, this.searchQuery, this.selectedSubCategory, this.selectedSort, this.selectedPage);
        this.isLoading=false;
      }
    },
    checking() {
      if (!this.isSearched) {
        if (this.qnaStore.qnaCards.length > 0) {
          this.totalPages = this.qnaStore.totalPage || 1;
        } else {
          this.totalPages = 1;
        }
      } else {
        if (this.qnaStore.qnaSearchedCards.length > 0) {
          this.totalPages = this.qnaStore.searchedTotalPage || 1;
        } else {
          this.totalPages = 1;
        }
      }
    },
  },
  components: {
    TagComponent,
    QnaCardComponent,
    PaginationComponent,
    SearchComponent,
  },
};
</script>


<style>
.qna-top {
  //height: 320px;
  //display: grid;
  padding-bottom: 50px;
  align-content: center;
  align-items: center;
  //background-color: #e1e8e8;
}

.qna-bottom {
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
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
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
