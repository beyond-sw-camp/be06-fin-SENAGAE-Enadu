<template>
  <div class="search-container">

    <select v-model="selectedCategory" class="category-select rounded-select">
      <option value="">카테고리 선택</option> 
      <option v-for="category in categoryStore.filteredCategories" :key="category.id" :value="category.id">
        {{ category.categoryName }}
      </option>
    </select>

    <select v-model="selectedType" class="category-select rounded-select">
      <option value="tc">제목+내용</option>
      <option value="t">제목</option>
      <option value="c">내용</option>
    </select>

    <input type="text" class="search-input" placeholder="검색어를 입력하세요" v-model="searchQuery" @keydown.enter="onSearch" />

    <button @click="onSearch" class="search-btn">
      <i class="fas fa-search"></i>
    </button>
  </div>
</template>

<script>
import { useWikiStore } from "@/store/useWikiStore"; 
import { useCategoryStore } from "@/store/useCategoryStore"; 
import { mapStores } from "pinia";

export default {
  data() {
    return {
      searchQuery: '',
      selectedType: 'tc',  
      selectedCategory: '', 
    };
  },
  computed: {
    ...mapStores(useWikiStore),
    ...mapStores(useCategoryStore),
  },
  methods: {
    async onSearch() {
      if (!this.searchQuery.trim() && !this.selectedCategory) {
        alert("검색어 또는 카테고리를 입력해주세요.");
        return;
      }

      const request = {
        keyword: this.searchQuery,
        categoryId: this.selectedCategory || null,
        type: this.selectedType || 'tc',
        page: 0,
        size: 16
      };

      await this.wikiStore.wikiSearch(request);
      this.$emit("search", request);
    }
  },
  async mounted() {
    await this.categoryStore.loadSuperCategories();
  }
};
</script>


<style scoped>
fa-magnifying-glass:before,
.fa-search:before {
  content: "\f002";
  color: white;
}

.search-btn {
  margin-bottom: 6px;
}

.rounded-select {
  border-radius: 0.8rem;
}

.search-container {
  display: flex;
  align-items: center;
  background-color: #60abda;
  border-radius: 0.6rem;
  padding: 0.3rem;
  height: 3rem;
}

.category-select {
  background-color: #f2f2f2;
  border: none;
  padding: 0.5rem;
  margin-right: 0.5rem;
  color: #333;
  font-size: 1rem;
  height: 100%;
}

.search-input {
  border: none;
  border-radius: 0.6rem;
  padding: 0.5rem;
  flex-grow: 1;
  outline: none;
  font-size: 1rem;
  height: 100%;
}

.search-btn {
  background-color: transparent;
  border: none;
  padding: 0.5rem;
  cursor: pointer;
  font-size: 1.25rem;
  height: 100%;
  color: black;
}
</style>
  