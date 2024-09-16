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
                                        <input type="text" id="title" placeholder="제목을 입력해주세요."
                                            v-model="wikiRegisterReq.title"
                                            class="block w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-3 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                                            name="title">
                                    </div>

                                    <!-- 카테고리 선택 -->
                                    <div id="rowGapZero">
                                        <label for="tags"
                                            class="text-sm font-medium text-gray-700 dark:text-gray-300">카테고리</label>
                                        <div class="space-y-1 flex w-full">
                                            <input type="text" id="category1"
                                                :value="wikiRegisterReq.mySuperCategoryName || '상위 카테고리를 선택해주세요.'"
                                                class="w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-10 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                                                name="category1" readonly @click="openSuperCategoryModal" />
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
                                            <input type="file" id="thumbnail" name="thumbnail" ref="thumbnailInput"
                                                class="block w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-3 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                                                accept="image/*" @change="setImages" />
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
                                        <v-md-editor v-model="wikiRegisterReq.myText" height="400px"></v-md-editor>
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
import { useWikiStore } from "@/store/useWikiStore";
import { mapStores } from "pinia";
import SuperCategoryModal from '../../components/Category/SuperCategoryModal.vue';

export default {
    name: "WikiRegisterComponent",
    data() {
        return {
            wikiRegisterReq: {
                title: '',
                myText: '',
                mySuperCategory: '',
                mySuperCategoryName: '' 
            },
            thumbnail: null,
            showSuperCategoryModal: false, 
        }
        
    },
    computed: {
        ...mapStores(useWikiStore),
        isNullOrEmpty() {
            return (this.wikiRegisterReq.title === '' || this.wikiRegisterReq.myText === '' || this.wikiRegisterReq.mySuperCategory === '')
        },
    },
    methods: {
        openSuperCategoryModal() {
            this.showSuperCategoryModal = true;
        },
        setSuperCategory(selectedSuperCategory) {
            if (!selectedSuperCategory || !selectedSuperCategory.id) {
                alert("상위 카테고리를 선택하세요.");
                return;
            }
            this.wikiRegisterReq.mySuperCategory = selectedSuperCategory.id;
            this.wikiRegisterReq.mySuperCategoryName = selectedSuperCategory.categoryName; // 수정된 부분
            this.showSuperCategoryModal = false; 
        },
        closeSuperCategoryModal() {
            this.showSuperCategoryModal = false;
        },
        setImages(event) {
            const file = event.target.files[0]; 
            this.thumbnail = file;
        },
        clearThumbnail() {
            this.thumbnail = null;
            this.$refs.thumbnailInput.value = ''; 
        },
        async submitForm() {
            console.log(this.wikiRegisterReq.title);
     
            if (this.wikiRegisterReq.myText.trim() === '') {
                alert('본문을 입력해주세요.');
                return;
            }
            if (!this.wikiRegisterReq.mySuperCategory) {
                alert('상위 카테고리를 선택해주세요.');
                return;
            }
            console.log("mySuperCategory 값:", this.wikiRegisterReq.mySuperCategory);
            console.log("mySuperCategory 값:", this.wikiRegisterReq.myText);


            this.wikiStore.wikiRegisterReq.title=this.wikiRegisterReq.title;
            this.wikiStore.wikiRegisterReq.content=this.wikiRegisterReq.myText;
            this.wikiStore.wikiRegisterReq.categoryId=this.wikiRegisterReq.mySuperCategory;
            const result = await this.wikiStore.registerWiki(this.thumbnail);
            if (result) {
                alert("정상적으로 등록되었습니다.");
                this.$router.push("/wiki");
            } else {
                alert("등록에 실패하였습니다. 다시 시도해주세요.");
                this.$router.go();
            }
        },
        cancel() {
        }
    },
    components: {
        SuperCategoryModal
    }
};
</script>

<style>
#rowGapZero {
    row-gap: 0;
}
</style>
