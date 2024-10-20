<template>
  <div id="__next" :class="{ 'loading': isLoading }">
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
                    <label for="title" class="text-sm font-bold font-medium text-gray-700"
                           style="font-size: 1rem;">제목</label>
                    <input type="text" v-model="updatedTitle" class="form-control circular-input"
                           readonly style="pointer-events: none; width: 100%;"/>
                  </div>

                  <div class="space-y-1">
                    <label for="category" class="text-sm font-bold font-medium text-gray-700"
                           style="font-size: 1rem;">카테고리</label>
                    <input type="text" v-model="updatedCategory" class="form-control circular-input"
                           readonly style="pointer-events: none; width: 100%;"/>
                  </div>

                  <div class="space-y-1">
                    <label for="content" class="text-sm font-medium text-gray-700" style="font-size: 1rem;">내용</label>
                    <v-md-editor v-model="updatedContent" :disabled-menus="[]" height="400px"
                      @upload-image="commonStore.imageUpload"></v-md-editor>
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
              <LoadingComponent v-show="isLoading" style="margin-top: 15rem" />
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
import { useCommonStore } from "@/store/useCommonStore";
import LoadingComponent from '@/components/Common/LoadingComponent.vue';

export default {
  name: "WikiUpdateComponent",
  components: {
    LoadingComponent,
    VMdEditor,
  },
  data() {
    return {
      updatedTitle: "",
      updatedCategory: "",
      updatedContent: "",
      updatedThumbnail: null,
      currentThumbnail: "",
      isLoading: false,
    };
  },
  computed: {
    ...mapStores(useWikiStore),
    ...mapStores(useCommonStore),
    isNullOrEmpty() {
      return this.updatedContent.trim() === "";
    },
  },
  async created() {
    const id = this.$route.query.id;

    if (id) {
      const success = await this.fetchWikiDetail(id);
      if (!success) {
        alert("존재하지 않는 URL입니다.");
        this.$router.push('/wiki/list');
      }
    }
    this.isLoading = false;
  },
  async mounted() {
    await this.checkGrade();
  },
  methods: {
    async checkGrade() {
      try {
        const success = await this.wikiStore.fetchUserDetails();
        if (!success) {
          alert("로그인이 필요합니다.");
          this.$router.go(-1); 
          return;
        }
        if (this.wikiStore.grade === "뉴비") {
          alert("견습 등급 이상만 위키 수정이 가능합니다.");
          this.$router.go(-1);
        }
      } catch (error) {
        console.error("사용자 정보를 불러오는 중 오류 발생:", error);
        this.$router.go(-1);
      }
    },
    async fetchWikiDetail(id) {
      this.isLoading = true;
      try {
        const success = await this.wikiStore.fetchWikiDetail(id);
        if (success) {
          this.updatedTitle = this.wikiStore.wikiDetail.title;
          this.updatedCategory = this.wikiStore.wikiDetail.category;
          this.updatedContent = this.wikiStore.wikiDetail.content;
          this.currentThumbnail = this.wikiStore.wikiDetail.thumbnail;
          return true;
        } else {
          return false;
        }
      }
      catch (error) {
        console.error("위키 상세 조회 중 오류:", error);
        return false; 
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
      this.isLoading = true;
      try {
        const id = this.$route.query.id;
        const success = await this.wikiStore.updateWiki(id, this.updatedContent, this.updatedThumbnail);
        if (success) {
          alert("수정이 완료되었습니다.");
          this.$router.push({ name: "WikiDetail", query: { id } });
        } else {
          throw new Error("수정 실패");
        }
      }
      catch (error) {
        console.error("수정 중 오류 발생:", error);
        alert("수정에 실패했습니다. 다시 시도해주세요.");
        this.$router.push('/wiki/list'); 
      }
      finally {
        this.isLoading = false;
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

