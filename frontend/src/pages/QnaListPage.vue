<template>
  <SortTypeComponent/>
  <QnaCardComponent
    v-for="qnaCard in qnaStore.qnaCards"
    :key="qnaCard.id"
    v-bind:qnaCard="qnaCard"
  />
</template>

<script>
import { mapStores } from "pinia";
import { useQnaStore } from "@/store/useQnaStore";
import QnaCardComponent from "@/components/qna/QnaCardComponent.vue";
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

<style></style>
