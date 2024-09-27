<template>
  <div class="search-container">
    <!-- 상위 카테고리 선택 -->
    <select v-model="selectedCategory" class="category-select rounded-select">
      <option disabled value="">카테고리 선택</option>
      <option v-for="category in categoryStore.filteredCategories" :key="category.id" :value="category.id">
        {{ category.categoryName }}
      </option>
    </select>

    <!-- 타입 옵션 선택 -->
    <select v-model="selectedType" class="category-select rounded-select">
      <option value="tc">제목+내용</option>
      <option value="t">제목</option>
      <option value="c">내용</option>
    </select>

    <!-- 검색 입력 필드 -->
    <input type="text" class="search-input" placeholder="검색어를 입력하세요" v-model="searchQuery" @keydown.enter="onSearch" />

    <!-- 검색 버튼 -->
    <button @click="onSearch" class="search-btn">
      <i class="fas fa-search"></i>
    </button>
  </div>
</template>

<script>
import { useWikiStore } from "@/store/useWikiStore"; // Wiki Store 가져오기
import { useCategoryStore } from "@/store/useCategoryStore"; // Category Store 가져오기
import { mapStores } from "pinia";

export default {
  data() {
    return {
      searchQuery: '',
      selectedType: 'tc',  // 기본값을 "제목+내용"으로 설정
      selectedCategory: '', // 상위 카테고리 기본 선택
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
    await this.categoryStore.loadSuperCategories(); // 카테고리 로드
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
  /* 둥근 모서리 추가 */
}

.search-container {
  display: flex;
  align-items: center;
  background-color: #60abda;
  /* 배경색 */
  border-radius: 0.6rem;
  padding: 0.3rem;
  height: 3rem;
  /* 전체 높이 통일 */
}

.category-select {
  background-color: #f2f2f2;
  border: none;
  padding: 0.5rem;
  margin-right: 0.5rem;
  /* 드롭다운 간의 간격 */
  color: #333;
  font-size: 1rem;
  height: 100%;
  /* 높이 맞추기 */
}

.search-input {
  border: none;
  border-radius: 0.6rem;
  padding: 0.5rem;
  flex-grow: 1;
  outline: none;
  font-size: 1rem;
  height: 100%;
  /* 높이 맞추기 */
}

.search-btn {
  background-color: transparent;
  border: none;
  padding: 0.5rem;
  cursor: pointer;
  font-size: 1.25rem;
  height: 100%;
  /* 높이 맞추기 */
  color: black;
}
</style>
  