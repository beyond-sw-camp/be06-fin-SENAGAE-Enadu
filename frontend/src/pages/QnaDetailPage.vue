<template>
  <div class="inner">
    <QnaDetailHeader v-bind:qnaDetail=qnaStore.qnaDetail />
    <QnaDetailComponent v-bind:qnaDetail=qnaStore.qnaDetail />
    <div v-if="isLoading"></div>
    <QnaAnswerDetailComponent v-else
        v-for="qnaAnswer in qnaStore.qnaAnswers"
        :key="qnaAnswer.id"
        :qnaAnswer="qnaAnswer"
    />
  </div>

</template>

<script>
import {mapStores} from "pinia";
import {useQnaStore} from "@/store/useQnaStore";
import {useRoute} from 'vue-router';
import QnaDetailComponent from "@/components/qna/QnaDetailComponent.vue";
import QnaDetailHeader from "@/components/qna/QnaDetailHeaderComponent.vue";
import QnaAnswerDetailComponent from "@/components/qna/QnaAnswerDetailComponent.vue";

export default {
  name: "QnaDetailPage",

  data() {
    return {
      id: 1,
      isLoading: true
    };
  },
  methods: {
    async getQnaDetail(){
      const route = useRoute();
      const qnaDetailId = route.params.id;
      await this.qnaStore.getQnaDetail(qnaDetailId);
      this.isLoading=false
    }
  },
  computed: {
    ...mapStores(useQnaStore),

  },
  mounted() {
    this.getQnaDetail()


  },
  components: {
    QnaAnswerDetailComponent,
    QnaDetailComponent,
    QnaDetailHeader,
  },
};
</script>

<style>
.inner {
  width: auto;
  display: grid;
  align-content: center;
  align-items: center;
  background-color: #ffffff;
  margin: 50px 500px;
}
</style>
