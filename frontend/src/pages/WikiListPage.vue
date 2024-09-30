<template>
  <div class="custom-container">
    <TagComponent :tagTitle="'WIKI'" :tagSubTitle="'당신의 위키를 만들어보세요'"/>
    <div class="wiki-inner">
      <WikiSearchComponent @search="handleSearch"/>

      <div class="create-wiki-btn-container">
        <button @click="navigateToWikiRegister" class="create-wiki-btn">작성하기</button>
      </div>

      <div v-if="wikiStore.isLoading" style="text-align:center;">
        <p>로딩 중...</p>
      </div>

      <div class="wiki-list-grid" v-if="!wikiStore.isLoading">
        <WikiCardComponent v-for="wikiCard in wikiStore.wikiCards" :key="wikiCard.id" :wikiCard="wikiCard"/>
      </div>

      <div v-if="!wikiStore.isLoading && wikiStore.wikiCards.length === 0" style="text-align: center;">
        <p>검색 결과가 없습니다.</p>
      </div>
    </div>

    <div class="pagination-container" v-if="!isLoading && totalPage > 0">
      <PaginationComponent
          :totalPage="totalPage"
          :nowPage="selectedPage"
          @updatePage="handlePageUpdate"
      />
    </div>
  </div>
</template>

<script>
import {useWikiStore} from "@/store/useWikiStore";
import WikiCardComponent from "@/components/wiki/WikiCardComponent.vue";
import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import WikiSearchComponent from "@/components/Common/WikiSearchComponent .vue";
import TagComponent from "@/components/Common/TagComponent.vue";
import {mapStores} from "pinia";

export default {
  name: "WikiListPage",
  components: {
    WikiCardComponent,
    PaginationComponent,
    WikiSearchComponent,
    TagComponent,
  },
  data() {
    return {
      selectedPage: 1,
      isSearchMode: false,
      searchParams: {},
      isLoading: true,
      totalPage: 1
    };
  },
  computed: {
    ...mapStores(useWikiStore),
  },
  methods: {
    handleSearch(searchParams) {
      this.isSearchMode = true;
      this.selectedPage = 1;
      this.isLoading = true;
      this.searchParams = searchParams;
      this.wikiStore.wikiSearch(this.searchParams).then(() => {
        this.totalPage = this.wikiStore.searchTotalPages;
        this.isLoading = false;
      });
    },

    async fetchWikiList(page) {
      this.isSearchMode = false;
      this.isLoading = true;

      await this.wikiStore.fetchWikiList(page);
      this.totalPage = this.wikiStore.totalPages;
      this.isLoading = false;
    },

    async handlePageUpdate(newPage) {
      if (newPage !== this.selectedPage) {
        this.selectedPage = newPage;
        this.isLoading = true;

        if (this.isSearchMode) {
          this.searchParams.page = newPage - 1;
          this.wikiStore.wikiSearch(this.searchParams).then(() => {
            this.isLoading = false;
          });
        } else {
          this.fetchWikiList(this.selectedPage);
          this.isLoading = false;

        }
      }
    },

    navigateToWikiRegister() {
      this.$router.push("/wiki/register");
    }
  },

  mounted() {
    this.fetchWikiList(this.selectedPage);
  }
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
  color: #3899de;
  text-decoration: none;
  font-weight: 700;
  font-size: 1rem;
  cursor: pointer;
}

.create-wiki-btn:hover {
  text-decoration: underline;
}
</style>
