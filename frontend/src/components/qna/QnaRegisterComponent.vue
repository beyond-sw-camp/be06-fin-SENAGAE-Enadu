<template>
    <div id="__next">
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
                                        <label for="title" class="text-sm font-medium text-gray-700" >제목</label>
                                        <input type="text" id="title" placeholder="제목을 입력해주세요." v-model="myTitle"
                                            class="block w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-3 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                                            name="title">
                                    </div>

                                  <div id="rowGapZero">
                                  <label for="tags" class="text-sm font-medium text-gray-700 dark:text-gray-300">카테고리</label>
                                  <div class="space-y-1 flex w-full">
                                    <input type="text" id="category1" placeholder="상위 카테고리를 선택해주세요." v-model="mySuperCategory"
                                           class="w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-10 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                                           name="category1">
                                    <div style="margin-left:10px; margin-right:10px">
                                      <button type="button"
                                              class="w-20 items-center space-x-2 rounded-md bg-blue-500 px-4 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-blue-600">
                                        검색
                                      </button>
                                    </div>
                                    <input type="text" id="category2" placeholder="하위 카테고리를 선택해주세요." v-model="mySubCategory"
                                           class="w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-10 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                                           name="category2">
                                    <div style="margin-left:10px">
                                      <button type="button"
                                              class="w-20 items-center space-x-2 rounded-md bg-blue-500 px-4 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-blue-600">
                                        검색
                                      </button>
                                    </div>
                                  </div>
                                    </div>

                                    <!-- 본문 -->
                                    <div class="space-y-1">
                                        <label for="text" class="text-sm font-medium text-gray-700 dark:text-gray-300" >본문</label>
                                        <v-md-editor v-model="myText" height="400px"></v-md-editor>
                                    </div>
                                </div>



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
                                            v-bind:disabled="isNullOrEmpty" @click="click" >
                                            등록
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>
    </div>
</template>

<script>
import { mapStores } from "pinia";
import { useQnaStore } from "@/store/useQnaStore";

export default {
  name: "QnaRegisterComponent",
  data() {
    return {
      myTitle: '',
      myText: '',
      mySuperCategory: '',
      mySubCategory: ''
    };
  },
  computed: {
    ...mapStores(useQnaStore),
    isNullOrEmpty() {
      return (this.myTitle === '' || this.myText === '' || this.mySuperCategory === '' || this.mySubCategory === '')
    },
  },
  methods: {
    async click() {
      await useQnaStore().registerQna(this.myTitle, this.myText);
    },
    cancel() {
      this.myTitle = '';
      this.myText = '';
      this.mySuperCategory = '';
      this.mySubCategory = '';
    },
  },
};
</script>

<style>
  #rowGapZero {
    row-gap: 0;
  }
</style>

