<template>
  <QnaCardComponent
    v-for="qnaDetail in qnaStore.qnaDetails"
    :key="qnaDetail.id"
    v-bind:qnaDetail="qnaDetail"
  />
</template>

<script>
import { mapStores } from "pinia";
import { useQnaStore } from "@/store/useQnaStore";
import QuestionDetailComponent from "@/components/qna/QnaCardComponent.vue";

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
  },
};
</script>

<style></style>
