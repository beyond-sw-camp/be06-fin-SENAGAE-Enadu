<template>
  <div class="custom-container">
    <p id="main-title">WIKI</p>
    <p id="sub-title">당신의 위키를 만들어보세요</p>

    <div v-if="isLoading" style="text-align:center;">
      <p>로딩 중...</p>
    </div>

    <div class="wiki-list-grid" v-if="!isLoading">
      <WikiCardComponent v-for="wikiCard in wikiCards" :key="wikiCard.id" :wikiCard="wikiCard" />
    </div>

    <div class="pagination-container" v-if="!isLoading">
      <PaginationComponent :totalPage="totalPages" @updatePage="handlePageUpdate" />
    </div>
  </div>
</template>

<script>
import WikiCardComponent from "@/components/wiki/WikiCardComponent.vue";
import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import { mapStores } from "pinia";
import { useWikiStore } from "@/store/useWikiStore";

export default {
  name: "WikiListPage",
  components: {
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
      console.log("weweewewnewPage: "+newPage);
      if (newPage !== this.selectedPage) {
        this.selectedPage = newPage;
        this.wikiStore.fetchWikiList(this.selectedPage);

        // this.fetchWikiList(this.selectedPage);
      }
    },
  },
};
</script>

<style scoped>
.wiki-list-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  grid-auto-rows: auto;
  gap: 64px;
  width: 100%;
  max-width: 1200px;
  padding: 20px;
  justify-items: center;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
