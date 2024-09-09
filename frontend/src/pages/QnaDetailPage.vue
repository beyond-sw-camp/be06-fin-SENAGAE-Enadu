<template>
  <QnaDetailComponent
    v-for="qnaDetail in qnaStore.qnaDetails"
    :key="qnaDetail.id"
    v-bind:qnaDetail="qnaDetail"
  />
</template>

<script>
import { useRoute } from 'vue-router';
import { mapStores } from "pinia";
import { useQnaStore } from "@/store/useQnaStore";
import QnaDetailComponent from "@/components/qna/QnaDetailComponent.vue";

const route = useRoute();

export default {
  name: "QnaDetailPage",
  data() {
    return {
      id: 1
    };
  },
  computed: {
    ...mapStores(useQnaStore),
  },
  mounted() {
    const qnaDetailId = route.params.id;
    this.qnaStore.getQnaDetail(qnaDetailId);
  },
  methods: {},
  components: {
    QnaDetailComponent,
  },
};
</script>

<style></style>
