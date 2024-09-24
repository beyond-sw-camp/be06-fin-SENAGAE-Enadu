<template>
  <div class="custom-container">
    <TagComponent :tagTitle="'QnA'" :tagSubTitle="'당신의 에러를 해결해보세요'"/>
    <div class="qna-inner">
      <SearchComponent  @checkLatest="handleCheckLatest"
                        @checkLike="handleCheckLike"/>
      <div class="qna-list-flex">
        <QnaCardComponent
            v-for="qnaCard in qnaStore.qnaCards"
            :key="qnaCard.id"
            v-bind:qnaCard="qnaCard"
        />
      </div>
    </div>
  </div>
  <div class="qna-bottom">
    <PaginationComponent @updatePage="handlePageUpdate" :nowPage="selectedPage + 1"/>
  </div>
</template>

<script>
import {mapStores} from "pinia";
import {useQnaStore} from "@/store/useQnaStore";
import QnaCardComponent from "@/components/qna/QnaListCardComponent.vue";
import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import SearchComponent from "@/components/Common/SearchComponent.vue";
import TagComponent from "@/components/Common/TagComponent.vue";

export default {
  name: "QnaListPage",
  data() {
    return {
      selectedSort: null,
      selectedPage: 0
    };
  },
  computed: {
    ...mapStores(useQnaStore),
  },
  mounted() {
    this.selectedSort = "latest";
    this.selectedPage = 1;
  },
  watch: {
    selectedSort() {
      this.qnaStore.getQnaList(this.selectedSort, this.selectedPage-1);
    },
    selectedPage() {
      this.qnaStore.getQnaList(this.selectedSort, this.selectedPage-1);
    },
  },
  methods: {
    handleCheckLatest() {
      this.selectedSort = "latest"
    },
    handleCheckLike() {
      this.selectedSort = "like"
    },
    handlePageUpdate(newPage) {
      this.selectedPage = newPage
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
