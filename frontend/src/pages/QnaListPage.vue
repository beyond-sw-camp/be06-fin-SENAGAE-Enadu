<template>
  <div class="wrap">
    <div class="qna-top">
      <p id="main-title">QnA</p>
      <p id="sub-title">당신의 에러를 해결해보세요</p>
    </div>
    <div class="qna-inner">
      <SortTypeComponent @checkLatest="handleCheckLatest"
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
</template>

<script>
import {mapStores} from "pinia";
import {useQnaStore} from "@/store/useQnaStore";
import QnaCardComponent from "@/components/qna/QnaListCardComponent.vue";
import SortTypeComponent from "@/components/Common/SortTypeComponent.vue";

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
  },
  watch: {
    selectedSort() {
      this.qnaStore.getQnaList(this.selectedSort, this.selectedPage);
    },
  },
  methods: {
    handleCheckLatest() {
      this.selectedSort="latest"
    },
    handleCheckLike() {
      this.selectedSort="like"
    },
  },
  components: {
    QnaCardComponent,
    SortTypeComponent,
  },
};
</script>


<style>
.qna-top {
  height: 320px;
  display: grid;
  align-content: center;
  align-items: center;
  background-color: #e1e8e8;
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
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  grid-template-rows: repeat(2, auto);
  gap: 26px 36px;
  justify-items: center;
}

.qna-inner {
  width: auto;
  height: max-content;
  margin: 20px 100px;
  padding: 10px;
  background-color: #fff;
}


</style>
