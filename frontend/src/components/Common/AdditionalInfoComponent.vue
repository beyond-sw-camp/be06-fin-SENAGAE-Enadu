<template>
  <div class="relative inline-block text-left">
    <div>
      <button
          class="inline-flex h-9 items-center justify-center space-x-0.5 rounded-md border border-gray-500/30 bg-white px-3.5 py-2 text-gray-700 shadow-sm hover:border-gray-500/70 sm:px-3 sm:pr-4 dark:bg-gray-700 dark:text-gray-300"
          id="headlessui-menu-button-:r1:" type="button" aria-haspopup="menu" aria-controls="headlessui-menu-items-:r5:"
          @click="toggleMenu">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
             aria-hidden="true" data-slot="icon" class="h-5 w-5">
          <path stroke-linecap="round" stroke-linejoin="round"
                d="M3 4.5h14.25M3 9h9.75M3 13.5h9.75m4.5-4.5v12m0 0-3.75-3.75M17.25 21 21 17.25"></path>
        </svg>
      </button>
    </div>
    <div
        class="absolute right-0 mt-2 w-[152px] origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none md:mt-4 dark:border dark:border-gray-700 dark:bg-gray-800"
        aria-labelledby="headlessui-menu-button-:r1:" id="headlessui-menu-items-:r5:" role="menu" tabindex="0"
        v-show="isOpen">
      <div class="py-1" role="none">
        <button v-if="question"
                class="text-blue-500 block px-4 py-2 text-sm" id="headlessui-menu-item-:r6:" role="menuitem"
                tabindex="-1" data-headlessui-state="" @click="doQuestion">다른 질문하기
        </button>
        <button v-if="adopted"
                class="text-gray-600 hover:text-blue-500 dark:text-gray-400 dark:hover:text-blue-200 block px-4 py-2 text-sm"
                id="headlessui-menu-item-:r7:" role="menuitem" tabindex="-1" data-headlessui-state=""
                @click="doAdopting">채택하기
        </button>
        <button v-if="editedAndDelete"
                class="text-gray-600 hover:text-blue-500 dark:text-gray-400 dark:hover:text-blue-200 block px-4 py-2 text-sm"
                id="headlessui-menu-item-:r8:" role="menuitem" tabindex="-1" data-headlessui-state=""
                @click="doEditing">수정하기
        </button>
        <button v-if="editedAndDelete"
                class="text-gray-600 hover:text-blue-500 dark:text-gray-400 dark:hover:text-blue-200 block px-4 py-2 text-sm"
                id="headlessui-menu-item-:r9:" role="menuitem" tabindex="-1" data-headlessui-state=""
                @click="doDeleting">
          삭제하기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import {mapStores} from "pinia";
import {useQnaStore} from "@/store/useQnaStore";
import {useUserStore} from "@/store/useUserStore";

export default {
  name: "AdditionalInfoComponent",
  data() {
    return {
      isOpen: false,
      editedAndDelete: false,
      question: false,
      isEdit: false,
    }
  },
  props: ["adopted", "detail", "isQuestion"],
  mounted() {
    console.log(this.adopted)
    this.showEditAndDelete();
    this.isOpen = false;
    this.question = false;
    this.checkQna();
  },
  methods: {
    toggleMenu() {
      this.isOpen = !this.isOpen;
    },
    doAdopting() {
      useQnaStore().adoptAnswer(this.$route.params.id, this.detail.id);
      useQnaStore().getQnaDetail(this.$route.params.id);
    },
    doEditing() {
      this.isEdit = true
      this.$emit("clickEdit", this.isEdit);
    },
    doQuestion() {
      this.$router.push('/qna/register');
    },
    doDeleting() {
      if (confirm("삭제한 게시글은 되돌릴 수 없습니다 정말 삭제하시겠습니까")) {
        if (this.isQuestion === true) {
          useQnaStore().deleteQuestion(this.$route.params.id);
        } else {
          useQnaStore().deleteAnswer(this.$route.params.id, this.detail.id);
        }
        alert("해당 글이 제거되었습니다.");
        useQnaStore().getQnaDetail(this.$route.params.id);
        window.location.reload();
      } else {
        alert("삭제가 취소되었습니다.");
      }
    },
    showEditAndDelete() {
      if (this.detail.userId === useUserStore().$state.userId) {
        console.log(this.detail.userId);
        if (this.isQuestion === true) {
          if (this.detail.answers.length === 0) {
            this.editedAndDelete = true;
          } else {
            this.editedAndDelete = false;
          }
        } else {
          console.log(this.detail.checkAdopted);
          if (!this.detail.checkAdopted) {
            this.editedAndDelete = true;
          } else {
            this.editedAndDelete = false;
          }

        }

      }
    },
    checkQna() {
      if (this.isQuestion === true) {
        this.question = true;
      }
    },
  },
  computed: {
    ...mapStores(useQnaStore),
  },
}
</script>

<style scoped>

</style>