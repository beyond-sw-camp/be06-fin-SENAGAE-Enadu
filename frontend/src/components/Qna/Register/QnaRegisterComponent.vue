<template>
  <div class="custom-container">
    <div id="__next" :class="{ 'loading': isLoading }">
      <main class="mx-auto mt-2 w-full max-w-7xl px-4 lg:mt-[18px] lg:px-0">
        <div class="flex lg:space-x-10">
          <div class="w-full min-w-0 flex-auto lg:static lg:max-h-full lg:overflow-visible">
            <div class="space-y-10">
              <div class="space-y-2">
                <h3 class="text-xl font-medium sm:text-3xl sm:leading-10">QnA 등록하기</h3>
              </div>
              <form>
                <div class="space-y-12 sm:space-y-14">
                  <div class="grid grid-cols-1 gap-y-7">
                    <div class="space-y-1">
                      <label for="title" class="text-sm font-medium text-gray-700">제목</label>
                      <input type="text" id="title" placeholder="제목을 입력해주세요." v-model="myTitle"
                             class="block w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-3 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                             name="title">
                    </div>

                    <div id="rowGapZero">
                      <label for="tags"
                             class="text-sm font-medium text-gray-700 dark:text-gray-300">카테고리</label>
                      <div class="space-y-1 flex w-full">
                        <input type="text" id="category1"
                               :placeholder="selectedSuperCategory ? selectedSuperCategory.categoryName : '상위 카테고리를 선택해주세요.'"
                               class="w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-10 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                               name="category1" disabled>
                        <div style="margin-left:10px; margin-right:10px">
                          <button type="button" @click="openSuperCategoryModal"
                                  class="w-20 items-center space-x-2 rounded-md bg-blue-500 px-4 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-blue-600 ui-state-disabled">
                            검색
                          </button>
                        </div>
                        <input type="text" id="category2"
                               :placeholder="selectedSubCategory ? selectedSubCategory.categoryName : '하위 카테고리를 선택해주세요.'"
                               class="w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-10 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20 ui-state-disabled"
                               name="category2" disabled>
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
                      <label for="text"
                             class="text-sm font-medium text-gray-700 dark:text-gray-300">본문</label>
                      <v-md-editor v-model="myText" :disabled-menus="[]"
                                   @upload-image="commonStore.imageUpload"
                                   height="400px"></v-md-editor>
                    </div>
                  </div>

                  <p style="text-align: right; font-size: 15px">등록된 질문은 답변이 2주일간 작성되지 않을 경우 조회 및 검색이
                    되지 않으며, 해당 질문은
                    마이페이지에서 확인하실 수 있습니다.</p>
                  <!-- 본문 버튼 -->
                  <div class="mt-5 flex justify-between gap-x-3 mb-10">
                    <button type="button"
                            class="w-20 rounded-md bg-white px-4 py-2 text-sm font-medium shadow-sm ring-1 ring-gray-500/30 hover:bg-gray-100 focus:outline-none dark:bg-gray-700 dark:ring-gray-500/70 dark:hover:bg-gray-600"
                            @click="cancel">
                      취소
                    </button>

                    <div class="relative flex shrink-0 items-center gap-x-3">
                      <button type="button"
                              class="inline-flex items-center space-x-2 rounded-md bg-blue-500 px-8 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-blue-600 disabled:bg-blue-500 disabled:opacity-40"
                              v-bind:disabled="isNullOrEmpty" @click="click">
                        등록
                      </button>
                    </div>
                  </div>
                </div>
                <LoadingComponent v-show="isLoading" style="margin-top: 15rem"/>
              </form>
            </div>
          </div>
        </div>
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
  </div>
</template>

<script>
import { mapStores } from "pinia";
import { useQnaStore } from "@/store/useQnaStore";
import SuperCategoryModal from "@/components/Category/SuperCategoryModal.vue";
import SubCategoryModal from "@/components/Category/SubCategoryModal.vue";
import { useCommonStore } from "@/store/useCommonStore";
import LoadingComponent from '@/components/Common/LoadingComponent.vue';

export default {
  name: "QnaRegisterComponent",
  data() {
    return {
      isLoading: false,
      myTitle: '',
      myText: '',
      myCategoryId: 0,

      selectedSuperCategory: "",
      selectedSubCategory: "",
      showSuperModal: false,
      showSubModal: false,

    };
  },
  computed: {
    ...mapStores(useQnaStore),
    ...mapStores(useCommonStore),

    isNullOrEmpty() {
      return (this.myTitle === '' || this.myText === '' || this.selectedSuperCategory === '');
    },
  },
  methods: {
    async click() {
      this.isLoading = true;
      if (!this.myTitle || !this.myText || !this.myCategoryId) {
        alert('모든 필드를 올바르게 입력해 주세요.');
      } else {
        try {
          await useQnaStore().registerQna(this.myTitle, this.myText, this.myCategoryId);
          await useQnaStore().getQnaDetail(this.$route.params.id);
          alert('등록이 완료되었습니다.');
          this.$router.push('/qna/detail/' + useQnaStore().registeredQnaId);
          this.cancel();
        }
        catch (error) {
          alert('등록 중 오류 발생');
        } finally {
          this.isLoading = false;
        }
      }
    },
    cancel() {
      this.$router.go(-1);
    },
    openSuperCategoryModal() {
      this.showSuperModal = true;
    },
    closeSuperCategoryModal() {
      this.showSuperModal = false;
    },
    handleSuperCategorySelection(category) {
      this.selectedSuperCategory = category;
      this.myCategoryId = this.selectedSuperCategory.id;
      this.closeSuperCategoryModal();
      this.openSubCategoryModal();
    },
    openSubCategoryModal() {
      if (this.selectedSuperCategory) {
        this.showSubModal = true;
        this.showSuperModal = false;
      } else {
        alert('상위 카테고리를 먼저 선택하세요.');
      }
    },
    closeSubCategoryModal() {
      this.showSubModal = false;
    },

    handleSubCategorySelection(category) {
      this.selectedSubCategory = category;
      this.myCategoryId = this.selectedSubCategory.id;
      this.closeSubCategoryModal();
    },
  },
  components: {
    LoadingComponent,
    SuperCategoryModal,
    SubCategoryModal,
  },
};
</script>

<style>
#rowGapZero {
  row-gap: 0;
}

@media (prefers-color-scheme: dark) {
  .dark\:bg-gray-700 {
    --tw-bg-opacity: 1;
    background-color: #db282873;
  }
}

@media (min-width: 640px) {
  .sm\:space-y-14 > :not([hidden]) ~ :not([hidden]) {
    --tw-space-y-reverse: 0;
    margin-top: calc(1.5rem * calc(1 - var(--tw-space-y-reverse)));
    margin-bottom: calc(3.5rem * var(--tw-space-y-reverse));
  }
}
</style>

