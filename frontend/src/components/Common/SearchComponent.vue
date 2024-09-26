<template>
  <div class="container">
    <!-- 상단 네비게이션 바 -->
    <div class="top-bar">
      <div :class="!$route. path.startsWith('/wiki') ? 'separator' : ''">
      <select v-model="selectedCategory" @change="getSubCategory" class="category-dropdown">
        <option value="">카테고리 선택</option>
        <option v-for="(super_category, idx) in categoryStore.superCategories" :key=idx :value="super_category.id">{{ super_category.categoryName }}</option>
      </select>
      </div>
      <!-- 탭 네비게이션 -->
      <div v-show="!$route.path.startsWith('/wiki')" class="tabs">
        <div :class="selectedSubCategory.id != 0 ? 'tab active': ''">{{selectedSubCategory.id !== 0 ? selectedSubCategory.categoryName:''}}</div>
        <div class="tab" @click="handleMoreCategory">
          모든 하위 카테고리 보기
          <i data-v-55f5a47e="" class="fas fa-caret-down"></i>
        </div>
      </div>
    </div>
    <div class="more-category-container show-more-container" :style="show_more_category ? 'display: block': 'display:none'">
      <div class="show-more-box">
        <div class="show-more-header">
          <h3>모든 하위 카테고리</h3>
          <button type="button" class="e-close-show-more" style="margin-left:auto" @click="cancelSelectSubCategory">선택 취소</button>
          <button type="button" class="e-close-show-more" style="margin-left:10px" @click="handleMoreCategory">닫기</button>
        </div>
        <div class="show-more-list">
          <ul>
            <li v-for="(subCategory) in categoryStore.subCategories" :key="subCategory.id" @click="selectCategory(subCategory)" >
              <span class="pagination-bullet e-select-bullet hero-bullet" :class="subCategory.id===selectedSubCategory.id ? 'pagination-bullet-active': ''">{{ subCategory.categoryName }}</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
      <!-- 검색창 -->
    <div style="display:flex; flex-direction: row; justify-content: center; width: 100%">
      <SortTypeComponent v-show="!$route.path.startsWith('/wiki')" style="margin-bottom: 0" @checkLatest="handleCheckLatest" @checkLike="handleCheckLike"/>
      <div style="display:flex" class="search-bar">
        <input type="text" placeholder="검색어를 입력하세요" v-model="searchQuery">
        <button @click="searchData"><i class="fas fa-search"></i></button>
      </div>

      <!-- 두 번째 드롭다운 -->
      <select style="display:flex;" v-model="selectedType" class="type-dropdown">
        <option value="tc">제목+내용</option>
        <option value="t">제목</option>
        <option value="c">내용</option>
      </select>
    </div>

    <!-- 콘텐츠 영역 -->
    <div class="content">
      <!-- 검색 결과나 탭에 따른 콘텐츠 표시 영역 -->
    </div>
  </div>
</template>

<script>
import SortTypeComponent from "@/components/Common/SortTypeComponent.vue";
import {mapStores} from "pinia";
import {useCategoryStore} from "@/store/useCategoryStore";

export default {
  data() {
    return {
      selectedCategory: '', // 선택된 슈퍼 카테고리 id
      selectedSubCategory: { // 선택된 하위 카테고리
        id: 0,
        categoryName: ''
      },
      searchQuery: '', // 검색어
      selectedType: 'tc',  // 검색 범위
      show_more_category: false,
      sort: "latest",
    };
  },
  computed:{
    ...mapStores(useCategoryStore),
  },
  methods: {
    selectCategory(subCategory) {
      this.selectedSubCategory = subCategory; // 탭 선택
    },
    
    handleMoreCategory(){
      this.show_more_category = !this.show_more_category;
    },
    handleCheckLatest() {
      this.$emit("checkLatest");
    },
    handleCheckLike() {
      this.$emit("checkLike");
    },
    async getSuperCategory(){
      if (!this.categoryStore.loading){
        await this.categoryStore.loadSuperCategories();
      }
    },
    async getSubCategory(event){
      this.selectedSubCategory = {
        id: 0,
        categoryName: ''
      };
      await this.categoryStore.loadSubCategories(event.target.value);
    },
    cancelSelectSubCategory(){
      this.selectedSubCategory = {
        id: 0,
        categoryName: ''
      };
      this.handleMoreCategory();
    },
    searchData() {
      switch(this.$route.path){
        case "/errorarchive/list": {
          const request = {
            keyword: this.searchQuery,
            selectedCategory: this.selectedCategory,
            selectedSubCategoryId: this.selectedSubCategory.id,
            selectedSubCategoryName: this.selectedSubCategory.categoryName,
            type: this.selectedType,
          };
          this.$router.push({
            path: this.$route.path,  // 현재 경로 유지
            query: request
          });
          break;
        }
        case "/qna/list":
          this.$emit("search", {
            selectedSubCategory: this.selectedSubCategory,
            searchQuery: this.searchQuery,
            selectedType: this.selectedType
          });
      },
    },
  },
  async mounted() {
    await this.getSuperCategory();
    this.selectedCategory = this.$route.query.selectedCategory || '';
    this.searchQuery = this.$route.query.keyword || "";
    this.selectedType = this.$route.query.type || "tc";
    this.selectedSubCategory.id = this.$route.query.selectedSubCategoryId || 0;
    this.selectedSubCategory.categoryName = this.$route.query.selectedSubCategoryName || "";
  },
  components: {
    SortTypeComponent
  }
};
</script>




<style scoped>
.separator {
  border-right: 1px solid rgb(202 202 202)
}
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.top-bar {
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: right;
  align-items: center;
  padding: 10px;
}

.category-dropdown, .type-dropdown {
  padding: 5px;
  margin-right: 20px;
  font-size: 16px;
  border: 1px solid #cfcfcf;
}

.tabs {
  display: flex;
  gap: 10px;
  margin-left: 10px;
}

.tab {
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 20px;
  background-color: #fff;
  border: 1px solid #cfcfcf;
  transition: background-color 0.3s ease;
}

.tab.active {
  background-color: #fff;
  border: 1px solid var(--main-color);
}



.search-box input {
  padding: 5px;
  font-size: 16px;
}

.search-bar {
  display: flex;
  align-items: center;
  background-color: #fafcfc;
  padding: 8px;
  border-radius: 25px;
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-left: auto;
  margin-right: 30px;
  border: 1px solid var(--main-color);
}

.search-bar input {
  border: none;
  outline: none;
  background: none;
  padding: 7px;
  width: 300px;
  font-size: 16px;
  height: 32.5px !important;
}

.search-bar button {
  background-color: #fafcfc;
  border: none;
  border-radius: 7px;
  width: 30px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  color: white;
}

.search-bar i {
  color: var(--main-color);
}

@media (max-width: 768px) {
  header {
    flex-direction: column;
    align-items: flex-start;
  }

  .navigation ul, .auth-navigation ul {
    flex-direction: column;
    gap: 10px;
  }

  .search-bar {
    width: 100%;
    margin-top: 10px;
  }
}

/*show more container*/
.show-more-container {
  position: relative;
  display: none;
  width: 100%;
}
@media screen and (max-width: 1280px) {
  section#pg___main section.hero .container {
    width: auto;
  }
}
.more-category-container {
  flex-grow: 1;
  margin: 0 auto;
  position: relative;
  width: 100%;
  padding: 0 32px;
}
.show-more-box {
  position: absolute;
  top: -2px;
  right: 0;
  width: 700px;
  max-width: 100%;
  border-radius: 14px;
  box-shadow: 0 0 1px 0 rgba(0, 0, 0, .3), 0 6px 20px 0 rgba(0, 0, 0, .04);
  background-color: #fff;
  z-index: 9;
}
.show-more-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 48px;
  border-bottom: 1px solid #e9ecef;
}
.show-more-header h3 {
  color: #212529;
  font-size: 1rem;
  font-weight: 700;
}
.show-more-header button {
  color: #ced4da;
  font-size: 1rem;
  font-weight: 700;
  text-decoration: underline;
  background: none;
  border: 0;
  cursor: pointer;
}
.show-more-list {
  padding: 12px 20px 20px 12px;
}
.show-more-list ul {
  display: flex;
  flex-wrap: wrap;
  list-style: none;
}
.show-more-list ul .pagination-bullet {
  display: block;
  margin-top: 8px;
  margin-left: 8px;
}

.pagination-bullet{
  flex: 0 0 auto;
  padding: 0 1rem;
  height: 36px;
  line-height: 36px;
  text-align: center;
  font-size: .875rem;
  font-weight: 700;
  color: #495057;
  border-radius: 20px;
  box-shadow: inset 0 0 0 1px #ced4da;
  background-color: #fff;
  cursor: pointer;
}

.pagination-bullet-active{
  color: #00c471;
  box-shadow: inset 0 0 0 2px #00c471;
}
</style>
