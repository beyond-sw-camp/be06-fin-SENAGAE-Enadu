<template>
    <div class="inner">
      <QnaDetailHeader/>
      <QnaDetailComponent/>
    </div>

</template>

<script>
import {mapStores} from "pinia";
import {useQnaStore} from "@/store/useQnaStore";
import QnaDetailComponent from "@/components/qna/QnaDetailComponent.vue";
import QnaDetailHeader from "@/components/qna/QnaDetailHeader.vue";

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
    console.log(this.$route)
    const qnaDetailId = this.$route.params.id;
    this.qnaStore.getQnaDetail(qnaDetailId);
  },
  methods: {},
  components: {
    QnaDetailComponent,
    QnaDetailHeader,
  },
};
</script>

<style>
.inner {
  width: 1200px;
  height: max-content;
  margin: auto;
  padding: 10px;
  background-color: #fff;
}
</style>
