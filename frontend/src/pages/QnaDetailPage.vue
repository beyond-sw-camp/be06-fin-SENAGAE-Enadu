<template>
  <div class="custom-container">
    <div v-if="isLoading"></div>
    <div v-else>
      <QnaDetailHeader v-bind:qnaDetail=qnaStore.qnaDetail />
      <QnaDetailComponent v-bind:qnaDetail=qnaStore.qnaDetail />
      <QnaAnswerDetailComponent v-for="qnaAnswer in qnaStore.qnaAnswers"
                                :key="qnaAnswer.id"
                                :qnaAnswer="qnaAnswer"/>
    </div>
  </div>
</template>

<script>
import {mapStores} from "pinia";
import {useQnaStore} from "@/store/useQnaStore";
import QnaDetailComponent from "@/components/Qna/Detail/QnaDetailComponent.vue";
import QnaDetailHeader from "@/components/Qna/Detail/QnaDetailHeaderComponent.vue";
import QnaAnswerDetailComponent from "@/components/Qna/Detail/QnaAnswerDetailComponent.vue";

export default {
  name: "QnaDetailPage",

  data() {
    return {
      id: 1,
      isLoading: true
    };
  },
  methods: {
    async getQnaDetail() {
      await useQnaStore().getQnaDetail(this.$route.params.id);
      this.isLoading = false
    }
  },
  computed: {
    ...mapStores(useQnaStore),
    checkLike() {
      return useQnaStore().qnaDetail.checkLikeOrHate;
    },
    checkScrap() {
      return useQnaStore().qnaDetail.checkScrap;
    },
  },
  watch: {
    checkLike() {
      useQnaStore().getQnaDetail(this.$route.params.id);
    },
    checkScrap() {
      useQnaStore().getQnaDetail(this.$route.params.id);
    },
  },
  mounted() {
    this.getQnaDetail();
  },
  components: {
    QnaAnswerDetailComponent,
    QnaDetailComponent,
    QnaDetailHeader,
  },
};
</script>

<style>
.custom-container {
  width: 62%;
  max-width: 1000px;
}
</style>
