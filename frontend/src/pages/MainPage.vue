<template>
  <div class="tag-box" style="background-color: rgb(245,245,245)">
    <div class="tag-sub-box">
      <img class="tag-title"
           src="https://enadu.s3.ap-northeast-2.amazonaws.com/IMAGE/2024/10/01/5e09b855-a36d-4fa5-a66a-afb505ba26b9"
           alt="Enadu"/>
      <div class="tag-sub-title">에러의 모든 것</div>
    </div>
  </div>
  <div class="custom-container" style="margin-top: 0;">

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

    <div v-if="isLoading" style="text-align:center;"></div>
    <div v-else class="subject-container">
      <div class="subject-box">
        <router-link to="/wiki/list" class="subject">위키</router-link>
        <router-link to="/wiki/list" class="show-all-button">더 보기</router-link>
      </div>
      <div class="wiki-list-grid" v-if="!isLoading">
        <WikiCardComponent v-for="wikiCard in mainStore.mainPageInfo.wikiListResList"
                           :key="wikiCard.id"
                           :wikiCard="wikiCard"/>
      </div>
    </div>

    <LoadingComponent v-if="isLoading"/>
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
  </div>
</template>

<script>
import { mapStores } from "pinia";
import { useMainStore } from "@/store/useMainStore";
import ErrorArchiveCardComponent from "@/components/errorarchive/ErrorArchiveCardComponent.vue";
import WikiCardComponent from "@/components/wiki/WikiCardComponent.vue";
import QnaCardComponent from "@/components/Qna/List/QnaListCardComponent.vue";
import LoadingComponent from "@/components/Common/LoadingComponent.vue";

export default {
  name: "MainPage",
  components: { LoadingComponent, QnaCardComponent, WikiCardComponent, ErrorArchiveCardComponent },
  computed: {
    ...mapStores(useMainStore),
  },
  data() {
    return {
      isLoading: true,
    }
  },
  methods: {
    async getMainPageInfo() {
      try {
        this.isLoading = true;
        await this.mainStore.getMainPageInfo();

        if (this.mainStore.mainPageInfo.errorArchiveListResList.length > 0 &&
            this.mainStore.mainPageInfo.wikiListResList.length > 0 &&
            this.mainStore.mainPageInfo.qnaListResList.length > 0) {
          this.isLoading = false;
        } else {
          console.error("데이터가 제대로 로드되지 않았습니다.");
        }
      }
      catch (error) {
        console.error("데이터 로드 중 오류 발생:", error);
        this.isLoading = false;
      }
    }
  },
  mounted() {
    this.getMainPageInfo();
  }
}
</script>


<style scoped>
.tag-box {
  width: 100%;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  display: inline-flex;
  padding-bottom: 20px;
  background-color: rgb(245, 245, 245)
}

.tag-sub-box {
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  gap: 8px;
  display: flex
}

.tag-title {
  align-self: stretch;
  text-align: center;
  color: #1E1E1E;
  font-size: 40px;
  font-family: Inter;
  font-weight: 700;
  word-wrap: break-word;
  width: auto;
  height: 100px;
  display: block;
  margin: 20px auto;
}

.tag-sub-title {
  align-self: stretch;
  text-align: center;
  color: #757575;
  font-size: 25px;
  font-family: Inter;
  font-weight: 400;
  word-wrap: break-word
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
  grid-template-columns: repeat(auto-fill, minmax(230px, 1fr));
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
  gap: 0 36px;
  justify-items: stretch;
  max-width: 100%;
  margin: 0 auto;
}
</style>