<template>
  <div id="__next">
    <main class="mx-auto mt-2 w-full max-w-7xl px-4 lg:mt-[18px] lg:px-0">
      <div class="flex lg:space-x-10">
        <div class="w-full min-w-0 flex-auto lg:static lg:max-h-full lg:overflow-visible">
          <div class="space-y-10">
            <div class="space-y-2">
              <h3 class="text-xl font-medium sm:text-3xl sm:leading-10">기술 위키 수정하기</h3>
            </div>
            <form @submit.prevent="submitUpdate">
              <div class="space-y-12 sm:space-y-14">
                <div class="grid grid-cols-1 gap-y-7">
                  
                  <div class="space-y-1">
                    <label for="title" class="text-sm font-medium text-gray-700">제목</label>
                    <input type="text" v-model="updatedTitle" class="form-control" readonly />
                  </div>

                  <div class="space-y-1">
                    <label for="category" class="text-sm font-medium text-gray-700">카테고리</label>
                    <input type="text" v-model="updatedCategory" class="form-control" readonly />
                  </div>

                  <div class="space-y-1">
                    <label for="content" class="text-sm font-medium text-gray-700">내용</label>
                    <v-md-editor v-model="updatedContent" height="400px"></v-md-editor>
                  </div>

                  <div class="space-y-1">
                    <label for="thumbnail" class="text-sm font-medium text-gray-700">썸네일 수정</label>
                    <div class="flex w-full">
                      <input type="file" id="thumbnail" name="thumbnail" ref="thumbnailInput"
                             class="block w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-3 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                             accept="image/*" @change="setImages" />
                      <button type="button" id="clearThumbnail"
                              class="ml-3 w-20 items-center space-x-2 rounded-md bg-red-500 px-4 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-600"
                              @click="clearThumbnail">취소
                      </button>
                    </div>
                    <!-- 현재 썸네일 미리보기 -->
                    <div v-if="currentThumbnail" class="mt-2">
                      <p>현재 썸네일:</p>
                      <img :src="currentThumbnail" alt="썸네일 미리보기" width="150px" />
                    </div>
                  </div>
                </div>

                <div class="mt-5 flex justify-between gap-x-3 mb-10">
                  <button type="button"
                          class="w-20 rounded-md bg-white px-4 py-2 text-sm font-medium shadow-sm ring-1 ring-gray-500/30 hover:bg-gray-100 focus:outline-none dark:bg-gray-700 dark:ring-gray-500/70 dark:hover:bg-gray-600"
                          @click="cancel">취소
                  </button>
                  <div class="relative flex shrink-0 items-center gap-x-3">
                    <button type="submit"
                            class="inline-flex items-center space-x-2 rounded-md bg-blue-500 px-8 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-blue-600 disabled:bg-blue-500 disabled:opacity-40"
                            :disabled="isNullOrEmpty">저장
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
import { useWikiStore } from "@/store/useWikiStore";
import { mapStores } from "pinia";
import VMdEditor from '@kangc/v-md-editor';

export default {
  name: "WikiUpdateComponent",
  components: {
    VMdEditor,
  },
  data() {
    return {
      updatedTitle: "", 
      updatedCategory: "", 
      updatedContent: "", 
      updatedThumbnail: null, 
      currentThumbnail: "", 
    };
  },
  computed: {
    ...mapStores(useWikiStore),
    isNullOrEmpty() {
      return this.updatedContent.trim() === "";
    },
  },
  created() {
    const id = this.$route.query.id;
    if (id) {
      this.fetchWikiDetail(id); 
    }
  },
  methods: {
    async fetchWikiDetail(id) {
      try {
        await this.wikiStore.fetchWikiDetail(id); 
        this.updatedTitle = this.wikiStore.wikiDetail.title; 
        this.updatedCategory = this.wikiStore.wikiDetail.category; 
        this.updatedContent = this.wikiStore.wikiDetail.content;
        this.currentThumbnail = this.wikiStore.wikiDetail.thumbnail;
      } catch (error) {
        console.error("위키 상세 조회 중 오류:", error);
      }
    },
    setImages(event) {
      const file = event.target.files[0];
      this.updatedThumbnail = file;
    },
    clearThumbnail() {
      this.updatedThumbnail = null;
      this.$refs.thumbnailInput.value = ''; 
    },
    async submitUpdate() {
      try {
        const id = this.$route.query.id;
        console.log("Updating Wiki with ID:", id);
        await this.wikiStore.updateWiki(id, this.updatedContent, this.updatedThumbnail);  // Store 메서드 호출
        alert("수정이 완료되었습니다.");
        this.$router.push({ name: "WikiDetail", query: { id } });
      } catch (error) {
        console.error("수정 중 오류 발생:", error);
        alert("수정에 실패했습니다. 다시 시도해주세요.");
      }
    },
    cancel() {
      const id = this.$route.query.id;
      this.$router.push({ name: "WikiDetail", query: { id } });
    },
  },
};
</script>

<style scoped>
#rowGapZero {
  row-gap: 0;
}
</style>
