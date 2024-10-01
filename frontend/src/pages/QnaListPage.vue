<template>
  <div class="custom-container">
    <TagComponent :tagTitle="'QnA'" :tagSubTitle="'당신의 에러를 해결해보세요'"/>
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
            @click="goToDetail(qnaCard.id)"
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
    <div v-if="!isLoading" class="qna-bottom">
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
    async selectedSort() {
      await this.qnaStore.getQnaList(this.selectedSort, this.selectedPage - 1);
      this.totalPages = useQnaStore().totalPage || 1;
      this.isLoading = false;
    },
    async selectedPage() {
      await this.qnaStore.getQnaList(this.selectedSort, this.selectedPage - 1);
      this.totalPages = useQnaStore().totalPage || 1;
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
    async handleSearch(data) {
      if (data !== null) {
        this.isSearched = true;
        this.isLoading = true;

        const {selectedSubCategory, searchQuery, selectedType} = data;
        this.selectedSubCategory = selectedSubCategory;
        this.searchQuery = searchQuery;
        this.selectedType = selectedType;

        await useQnaStore().qnaSearch(this.selectedType, this.searchQuery, this.selectedSubCategory, this.selectedSort, this.selectedPage);
        this.totalPages = useQnaStore().searchedTotalPage || 1;
        this.isLoading = false;
      }
    },
    goToDetail(id) {
      this.$router.push('/qna/detail/' + id);
    },
    checking() {
      console.log("back"+useQnaStore().totalPage);
      if (!this.isSearched) {
        if (useQnaStore().qnaCards.length > 0) {
          this.totalPages = useQnaStore().totalPage || 1;
        } else {
          this.totalPages = 1;
        }
        console.log(this.totalPages);
      } else {
        if (useQnaStore().qnaSearchedCards.length > 0) {
          this.totalPages = useQnaStore().searchedTotalPage || 1;
        } else {
          this.totalPages = 1;
        }
        console.log(this.totalPages);
      }
    },
  },
  components: {
    TagComponent,
    QnaCardComponent,
    PaginationComponent,
    SearchComponent,
  }
  ,
}
;
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
  grid-template-columns: repeat(auto-fill, minmax(270px, 1fr));
  grid-auto-rows: auto;
  gap: 50px 30px;
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
