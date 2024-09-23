<template>
  <div class ="custom-container">
    <TagComponent :tagTitle="'ENADU'" :tagSubTitle="'에러의 모든 것'"/>

    <LoadingComponent v-if="isLoading" />
    <div v-else class="subject-container">
      <div class="subject-box">
        <router-link to="/qna/list" class="subject">QnA</router-link>
        <router-link to="/qna/list" class="show-all-button">더 보기</router-link>
      </div>
      <div class="qna-inner">
        <div class="qna-list-flex">
          <QnaCardComponent
              v-for="qnaCard in mainStore.mainPageInfo.qnaListResList"
              :key="qnaCard.id"
              v-bind:qnaCard="qnaCard"
          />
        </div>
      </div>
    </div>

    <div v-if="isLoading" style="text-align:center;"></div>
    <div v-else class="subject-container">
      <div class="subject-box">
        <router-link to="/wiki/list" class="subject">위키</router-link>
        <router-link to="/wiki/list" class="show-all-button">더 보기</router-link>
      </div>
      <div class="wiki-list-grid" v-if="!isLoading">
        <WikiCardComponent v-for="wikiCard in mainStore.mainPageInfo.wikiListResList" :key="wikiCard.id" :wikiCard="wikiCard" />
      </div>
    </div>

    <div v-if="isLoading" style="text-align:center;"></div>
    <div v-else class="subject-container">
      <div class="subject-box">
        <router-link to="/errorarchive/list" class="subject">에러 아카이브</router-link>
        <router-link to="/errorarchive/list" class="show-all-button">더 보기</router-link>
      </div>
      <div class="errorarchive-inner">
        <div class="errorarchive-list-flex">
          <ErrorArchiveCardComponent
            v-for="errorarchiveCard in mainStore.mainPageInfo.errorArchiveListResList"
            :key="errorarchiveCard.id"
            v-bind:errorarchiveCard="errorarchiveCard"
          />
        </div>
      </div>
    </div>
  </div>
</template>


<script>


import {mapStores} from "pinia";
import {useMainStore} from "@/store/useMainStore";
import ErrorArchiveCardComponent from "@/components/errorarchive/ErrorArchiveCardComponent.vue";
import WikiCardComponent from "@/components/wiki/WikiCardComponent.vue";
import QnaCardComponent from "@/components/qna/QnaListCardComponent.vue";
import TagComponent from "@/components/Common/TagComponent.vue";
import LoadingComponent from "@/components/Common/LoadingComponent.vue";

export default {
  name: "MainPage",
  components: {LoadingComponent, TagComponent, QnaCardComponent, WikiCardComponent, ErrorArchiveCardComponent},
  computed: {
    ...mapStores(useMainStore),
  },
  data() {
    return {
      isLoading: true,
    }
  },
  methods: {
    async getMainPageInfo(){
      await this.mainStore.getMainPageInfo();
      this.isLoading= false;
    }
  },
  mounted() {
    this.getMainPageInfo();
  }
}
</script>


<style scoped>
.custom-container {
  width: 75%;
  max-width: 1400px;
  margin: 30px auto;
  padding: 30px;
  border-radius: 10px;
}
.subject-container {
  margin-bottom: 70px;
}
.subject-box {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: 15px;
  font-weight: bold;
}
.subject {
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
  background-color: #fff;
}

.errorarchive-inner {
  width: auto;
  height: max-content;
  background-color: #fff;
}
.errorarchive-list-flex {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  grid-auto-rows: auto;
  gap: 26px 36px;
  justify-items: stretch;
  max-width: 100%;
  margin: 0 auto
}

.wiki-list-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  grid-auto-rows: auto;
  gap: 64px;
  width: 100%;
  justify-items: center;
}
</style>