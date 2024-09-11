<template>
  <div class="wrap">
    <div class="qna-inner">
      <SortTypeComponent v-bind:sortType="sortType"/>
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
    return {};
  },
  computed: {
    ...mapStores(useQnaStore),
  },
  mounted() {
    this.qnaStore.getQnaList();
    console.log(this.qnaStore.qnaCards);
  },
  methods: {},
  components: {
    QnaCardComponent,
    SortTypeComponent,
  },
};
</script>


<style>
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
