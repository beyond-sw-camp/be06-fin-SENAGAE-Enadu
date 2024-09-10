<template>
    <div id="__next">
        <main class="mx-auto mt-2 w-full max-w-7xl px-4 lg:mt-[18px] lg:px-0">
            <div class="flex lg:space-x-10">
                <div class="w-full min-w-0 flex-auto lg:static lg:max-h-full lg:overflow-visible">
                    <div class="space-y-10">
                        <div class="space-y-2">
                            <h3 class="text-xl font-medium sm:text-3xl sm:leading-10">기술 위키 작성하기</h3>
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
                                            <input type="text" id="category1" placeholder="상위 카테고리를 선택해주세요."
                                                v-model="selectedSuperCategory?.categoryName"
                                                class="w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-10 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                                                name="category1" readonly />
                                            <div style="margin-left:10px; margin-right:10px">
                                                <button type="button"
                                                    class="w-20 items-center space-x-2 rounded-md bg-blue-500 px-4 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-blue-600"
                                                    @click="openSuperCategoryModal">
                                                    검색
                                                </button>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- 썸네일 등록 -->
                                    <div class="space-y-1">
                                        <label for="thumbnail"
                                            class="text-sm font-medium text-gray-700 dark:text-gray-300">썸네일 등록</label>
                                        <div class="flex w-full">
                                            <input type="file" id="thumbnail" name="thumbnail"
                                                class="block w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-3 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                                                accept="image/*" @change="handleFileUpload">
                                            <button type="button" id="clearThumbnail"
                                                class="ml-3 w-20 items-center space-x-2 rounded-md bg-red-500 px-4 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-600"
                                                @click="clearThumbnail">
                                                취소
                                            </button>
                                        </div>
                                    </div>

                                    <!-- 본문 -->
                                    <div class="space-y-1">
                                        <label for="text"
                                            class="text-sm font-medium text-gray-700 dark:text-gray-300">본문</label>
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
                                            v-bind:disabled="isNullOrEmpty" @click="submitForm">
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
        <!-- 상위 카테고리 모달 -->
        <SuperCategoryModal v-if="showSuperCategoryModal" @mySuperCategory="setSuperCategory"
            @closeSuper="closeSuperCategoryModal" />
    </div>
</template>


<script>
import { mapStores } from "pinia";
import { useWikiStore } from "@/store/useWikiStore";
export default {
    name: "WikiRegisterComponent",
    data() {
        return {
            myTitle: '',
            myText: '',
            mySuperCategory: '',
            selectedSuperCategory: null,
      selectedThumbnail: null,
      showSuperCategoryModal: false, 
        };
    },
    computed: {
        ...mapStores(useWikiStore),
        isNullOrEmpty() {
            return (this.myTitle === '' || this.myText === '' || this.mySuperCategory === '')
        },
    },
    methods: {
        handleFileUpload(event) {
            const file = event.target.files[0];
            if (file) {
                this.selectedThumbnail = file;
            }
        },
        clearThumbnail() {
            this.selectedThumbnail = null;
            this.$refs.thumbnailInput.value = ''; // 파일 입력 필드 초기화
        },
        async submitForm() {
            // 파일이 선택된 경우에는 FormData를 사용하여 파일을 포함하여 전송
            const formData = new FormData();
            formData.append('title', this.myTitle);
            formData.append('content', this.myText);
            formData.append('categoryId', this.mySuperCategory);

            if (this.selectedThumbnail) {
                formData.append('thumbnail', this.selectedThumbnail);
            }

            try {
                await this.useWikiStore.registerWiki(formData); // pinia 스토어 호출
                // 성공 후 동작 처리
            } catch (error) {
                console.error("위키 등록 실패:", error);
                // 에러 처리
            }
        },
        cancel() {
            this.myTitle = '';
            this.myText = '';
            this.mySuperCategory = '';
            this.selectedThumbnail = null;
        },
    },
};
</script>

<style>
#rowGapZero {
    row-gap: 0;
}
</style>