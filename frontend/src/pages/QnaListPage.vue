<template>
  <SearchTypeComponent />
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
import SearchTypeComponent from "@/components/common/SearchTypeComponent.vue"

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
    SearchTypeComponent,
  },
};
</script>

<style></style>
