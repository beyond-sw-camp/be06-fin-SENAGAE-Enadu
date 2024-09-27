<template>
  <div class="custom-container">
    <TagComponent :tagTitle="'WIKI'" :tagSubTitle="'당신의 위키를 만들어보세요'"/>

        <!-- 작성하기 버튼 추가 -->
        <div class="create-wiki-btn-container">
      <button @click="navigateToWikiRegister" class="create-wiki-btn">작성하기</button>
    </div>

    <div v-if="isLoading" style="text-align:center;">
      <p>로딩 중...</p>
    </div>

    <div class="wiki-list-grid" v-if="!isLoading">
      <WikiCardComponent v-for="wikiCard in wikiCards" :key="wikiCard.id" :wikiCard="wikiCard" />
    </div>

    <div class="pagination-container" v-if="!isLoading">
      <PaginationComponent :totalPage="totalPages" :nowPage="selectedPage" @updatePage="handlePageUpdate" />
    </div>
  </div>
</template>

<script>
import WikiCardComponent from "@/components/wiki/WikiCardComponent.vue";
import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import { mapStores } from "pinia";
import { useWikiStore } from "@/store/useWikiStore";
import TagComponent from "@/components/Common/TagComponent.vue";

export default {
  name: "WikiListPage",
  components: {
    TagComponent,
    WikiCardComponent,
    PaginationComponent,
  },
  data() {
    return {
      selectedPage: 1,
      totalPages: 1,
      isLoading: true,
    };
  },
  computed: {
    ...mapStores(useWikiStore),
    wikiCards() {
      return this.wikiStore.wikiCards;
    },
  },
  mounted() {
    this.fetchWikiList(this.selectedPage);
  },
  methods: {
    async fetchWikiList(page) {
      this.isLoading = true;
      console.log(`Fetching page ${page}`);

      await this.wikiStore.fetchWikiList(page);

      if (this.wikiStore.wikiCards.length > 0) {
        this.totalPages = this.wikiStore.wikiCards[0].totalPages || 1;
      } else {
        this.totalPages = 1;
      }

      this.isLoading = false;
      console.log("Current Page:", page);
      console.log("Total Pages:", this.totalPages);
    },
    handlePageUpdate(newPage) {
      if (newPage !== this.selectedPage) {
        this.selectedPage = newPage;
        this.wikiStore.fetchWikiList(this.selectedPage);

      }
    },
    navigateToWikiRegister() {
      this.$router.push("/wiki/register");
    }
  },
};
</script>

<style scoped>
.wiki-list-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
    grid-auto-rows: auto;
    gap: 0 36px;
    justify-items: stretch;
    max-width: 100%;
    margin: 0 auto;
}
.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.create-wiki-btn-container {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

.create-wiki-btn {
  margin-bottom: 0.875rem;
    padding-left: 1rem;
    padding-right: 1rem;
    height: 2rem;
    display: inline-flex;
    -webkit-box-align: center;
    align-items: center;
    margin-right: 0.875rem;
    color: #2689d2;
    text-decoration: none;
    font-weight: 700;
    font-size: 1rem;
    cursor: pointer;
}

.create-wiki-btn:hover {
  text-decoration: underline;
}
</style>
