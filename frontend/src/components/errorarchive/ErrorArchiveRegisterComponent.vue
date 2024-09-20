<template>
  <div id="__next">
    <main class="mx-auto mt-2 w-full max-w-7xl px-4 lg:mt-[18px] lg:px-0">
      <div class="flex lg:space-x-10">
        <div class="w-full min-w-0 flex-auto lg:static lg:max-h-full lg:overflow-visible">
          <div class="space-y-10">
            <div class="space-y-2">
              <h3 class="text-xl font-medium sm:text-3xl sm:leading-10">에러 아카이브 작성하기</h3>
            </div>
            <form>
              <div class="space-y-12 sm:space-y-14">
                <div class="grid grid-cols-1 gap-y-7">
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
                      <input type="text" id="superCategory" :placeholder="selectedSuperCategory ? selectedSuperCategory.categoryName : '상위 카테고리를 선택해주세요.'"
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
                      <input type="text" id="subCategory" :placeholder="selectedSubCategory ? selectedSubCategory.categoryName : '하위 카테고리를 선택해주세요.'"
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
                    <v-md-editor v-model="errorArchive.content" height="400px"></v-md-editor>
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
                      등록
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
    </main>
  </div>
</template>

<script>
import {mapStores} from "pinia";
import SuperCategoryModal from '@/components/Category/SuperCategoryModal.vue';
import SubCategoryModal from '@/components/Category/SubCategoryModal.vue';
import { useErrorArchiveStore } from '@/store/useErrorArchiveStore';

export default {
  name: 'ErrorArchiveRegisterComponent',
  components: {
    SuperCategoryModal,
    SubCategoryModal,
  },
  computed: {
    ...mapStores(useErrorArchiveStore) // 어떤 저장소랑 연결시켜 주겠다.
  },
  data () {
    return {
     selectedSuperCategory: "",
     selectedSubCategory: "",
     showSuperModal: false,
     showSubModal: false,
     errorArchive : {
      title: "",
      content:"",
      categoryId: null
     }
    }
  },
  methods: {
    async handleSubmit(){
      if (!this.errorArchive.title || !this.errorArchive.content || !this.selectedSuperCategory) {
        alert('모든 필드를 올바르게 입력해 주세요.');
        return;
      }
      // subcategory null인지 확인해서 어떤거를 erroArchive 안에 넣어줄지 판별해서 id 넣어주기
      try { 
        const errorArchiveStore = useErrorArchiveStore(); // Use your store
        await errorArchiveStore. registerErrorArchive(this.errorArchive);
        alert('등록이 완료되었습니다.');
        // 목록페이지, 상세페이지 보통은 보여주는데 일단은 임시로 '/''
      } catch (error) {
        console.error('등록 중 오류 발생:', error);
        alert(`등록 중 오류 발생: ${error.message}`);
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