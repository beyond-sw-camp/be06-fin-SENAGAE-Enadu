<template>
  <div id="__next">
    <main class="mx-auto mt-2 w-full max-w-7xl px-4 lg:mt-[18px] lg:px-0">
      <div v-if="isLoading">로딩 중...</div> <!-- 로딩 중일 때 -->
      <div v-else> <!-- 로딩이 완료되면 렌더링 -->
        <div class="flex lg:space-x-10">
          <div class="w-full min-w-0 flex-auto lg:static lg:max-h-full lg:overflow-visible">
            <div class="space-y-10">
              <div class="space-y-2">
                <h3 class="text-xl font-medium sm:text-3xl sm:leading-10">에러 아카이브 수정하기</h3>
              </div>
              <form>
                <div class="space-y-12 sm:space-y-14">
                  <div class="grid grid-cols-1 gap-y-7">
                    <!-- 제목 렌더링 -->
                    <div class="space-y-1">
                      <label for="title" class="text-sm font-medium text-gray-700">제목</label>
                      <input type="text" id="title" placeholder="제목을 입력해주세요."
                             class="block w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-3 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                             name="title" v-model="errorArchive.title">
                    </div>

                    <!-- 상위 카테고리 -->
                    <div class="space-y-1">
                      <label for="superCategory" class="text-sm font-medium text-gray-700 dark:text-gray-300">상위 카테고리</label>
                      <div class="flex w-full">
                        <input type="text" id="superCategory" v-model="selectedSuperCategory.categoryName" 
       :placeholder="selectedSuperCategory.categoryName || '상위 카테고리를 선택해주세요.'" 
       disabled
       class="w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-10 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20" />
                        <div style="margin-left:10px">
                          <button type="button" @click="openSuperCategoryModal"
                                  class="w-20 items-center space-x-2 rounded-md bg-blue-500 px-4 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-blue-600">
                            검색
                          </button>
                        </div>
                      </div>
                    </div>

                    <!-- 하위 카테고리 -->
                    <div class="space-y-1">
                      <label for="subCategory" class="text-sm font-medium text-gray-700 dark:text-gray-300">하위 카테고리</label>
                      <div class="flex w-full">
                        <input type="text" id="subCategory" v-model="selectedSubCategory.categoryName" 
       :placeholder="selectedSubCategory.categoryName || '하위 카테고리를 선택해주세요.'" 
       disabled
       class="w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-10 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20" />
                        <div style="margin-left:10px">
                          <button type="button" @click="openSubCategoryModal"
                                  class="w-20 items-center space-x-2 rounded-md bg-blue-500 px-4 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-blue-600">
                            검색
                          </button>
                        </div>
                      </div>
                    </div>

                    <!-- 본문 -->
                    <div class="space-y-1">
                      <label for="text" class="text-sm font-medium text-gray-700 dark:text-gray-300">본문</label>
                      <v-md-editor v-model="errorArchive.content" :disabled-menus="[]" @upload-image="commonStore.imageUpload" height="400px"></v-md-editor>
                    </div>
                  </div>

                  <!-- 조건부 렌더링: 데이터가 있을 때만 버튼을 활성화 -->
                  <div class="mt-5 flex justify-between gap-x-3 mb-10">
                    <button type="button"
                            class="w-20 rounded-md bg-white px-4 py-2 text-sm font-medium shadow-sm ring-1 ring-gray-500/30 hover:bg-gray-100 focus:outline-none dark:bg-gray-700 dark:ring-gray-500/70 dark:hover:bg-gray-600"
                            @click="cancelForm">
                      취소
                    </button>
                    <div class="relative flex shrink-0 items-center gap-x-3">
                      <button type="button"
                              class="inline-flex items-center space-x-2 rounded-md bg-blue-500 px-8 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-blue-600 disabled:bg-blue-500 disabled:opacity-40"
                              @click="handleSubmit">
                        수정 완료
                      </button>
                    </div>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>

        <!-- 상위 카테고리 모달 -->
        <SuperCategoryModal
          v-if="showSuperModal"
          @mySuperCategory="handleSuperCategorySelection"
          @closeSuper="closeSuperCategoryModal"
        />
        <!-- 하위 카테고리 모달 -->
        <SubCategoryModal
          v-if="showSubModal"
          :superCategory="selectedSuperCategory"
          @mySubCategory="handleSubCategorySelection"
          @closeSub="closeSubCategoryModal"
        />
      </div> <!-- v-else 끝 -->
    </main>
  </div>
</template>
<script>
import {mapStores} from "pinia";
import SuperCategoryModal from '@/components/Category/SuperCategoryModal.vue';
import SubCategoryModal from '@/components/Category/SubCategoryModal.vue';
import { useErrorArchiveStore } from '@/store/useErrorArchiveStore';
import {useCommonStore} from "@/store/useCommonStore";
export default {
  name: 'ErrorArchiveUpdateComponent',
  components: {
    SuperCategoryModal,
    SubCategoryModal,
  },
  computed: {
    ...mapStores(useErrorArchiveStore), // 어떤 저장소랑 연결시켜 주겠다.
    ...mapStores(useCommonStore)
  },
  data() {
  return {
    selectedSuperCategory: {
      categoryName: ""
    },
    selectedSubCategory: {
      categoryName: ""
    },
    showSuperModal: false,
    showSubModal: false,
    isLoading: true,
    errorArchive: {
      id: 0,
      title: "",
      content: "",
      categoryId: null
    }
  };
},
async mounted() {
  try {
    this.isLoading = true; // Start loading
    this.errorArchive.id = this.errorarchiveStore.errorArchiveDetail.id;
    this.errorArchive.title = this.errorarchiveStore.errorArchiveDetail.title;
    this.errorArchive.content = this.errorarchiveStore.errorArchiveDetail.content;
    this.selectedSuperCategory.categoryName = this.errorarchiveStore.errorArchiveDetail.superCategory;
    this.selectedSubCategory.categoryName = this.errorarchiveStore.errorArchiveDetail.subCategory;

  } catch (error) {
    console.error('Error fetching error archive data:', error);
    alert('데이터 로딩 중 오류가 발생했습니다.');
  } finally {
    this.isLoading = false; // Stop loading
  }
},
  watch: {
    '$route.params.errorArchive': {
      handler(newVal) {
        if (newVal) {
          this.errorArchive = { ...newVal };
        }
      },
      immediate: true
    }
  },
  methods: {
    async handleSubmit() {
      try { 
        const errorarchiveStore = useErrorArchiveStore();
        const errorarchive = {
          id: this. errorArchive.id,
          title: this. errorArchive.title,
          content: this. errorArchive.content,
          categoryId: this.selectedSubCategory.categoryId
        };
        await errorarchiveStore.updateErrorArchive(errorarchive);
        alert('수정이 완료되었습니다.');
        this.$router.push('/errorarchive/list');
      } catch (error) {
        console.error('수정 중 오류 발생:', error);
        alert(`수정 중 오류 발생: ${error.message}`);
      }
    },
   
    // 메소드 2개일 필요없고, if-else문으로 처리 가능
    openSuperCategoryModal() {
      this.showSuperModal = true;
    },
    closeSuperCategoryModal() {
      this.showSuperModal = false;
    },
    handleSuperCategorySelection(category){
      this.selectedSuperCategory = category;
      this.errorArchive.categoryId = category.id;
      this.closeSuperCategoryModal();
      this.openSubCategoryModal();
    },
    openSubCategoryModal() {
      if (this.selectedSuperCategory) {
        this.showSubModal = true;
        this.showSuperModal=false;
      } else {
        alert('상위 카테고리를 먼저 선택하세요.');
      }
    },
    closeSubCategoryModal(){
      this.showSubModal = false;
    },
    handleSubCategorySelection(category) {
      this.selectedSubCategory = category;
      this.errorArchive.categoryId = category.id;
      this.closeSubCategoryModal();
    },
 }
}
</script>
<style scoped>
.category-selection {
margin-top: 1rem;
}
.category-select {
width: 100%;
appearance: none;
border-radius: 4px;
border: 1px solid #ddd;
padding: 8px 12px;
font-size: 16px;
box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
}
.btn-select {
width: 100%;
background-color: #3b82f6;
color: white;
border: none;
border-radius: 4px;
padding: 10px;
font-size: 16px;
font-weight: bold;
cursor: pointer;
transition: background-color 0.3s;
}
.btn-select:hover {
background-color: #2563eb;
}
</style>