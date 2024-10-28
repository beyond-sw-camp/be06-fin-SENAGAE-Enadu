<template>
  <div
      class="my-3 divide-y divide-gray-500/30 border-y border-gray-500/30 dark:divide-gray-500/70 dark:border-gray-500/70"></div>
  <li id="reply-1917142" class="pb-0.5 pt-1.5 sm:pb-1 sm:pt-2.5" :class="{'recomment': !isTopComment}">
    <div class="flex items-center text-xs sm:text-sm">
      <div class="flex flex-1 items-center gap-x-2">
        <div class="shrink-0"><a><img class="h-6 w-6 rounded-full sm:h-7 sm:w-7"
                                      :src="qnaComment.profileImage"
                                      alt="프로필 사진"></a></div>
        <div class="flex flex-col gap-x-1 sm:flex-row sm:items-center">
          <div class="flex items-center gap-x-1 text-gray-700 dark:text-gray-300"><a
              class="font-medium text-gray-900"
          >
            <NicknameComponent :nickname="qnaComment.nickname"/>
            <span>·</span> <span
              class="rounded-md bg-sky-500/20 px-1.5 py-0.5 text-xs">{{ qnaComment.grade }}</span></a></div>
          <div class="flex items-center gap-x-1"><span class="hidden sm:inline">·</span><a
              class="text-gray-700"
          >{{ formatDateTime(qnaComment.createdAt) }}</a></div>
        </div>
      </div>
    </div>
    <div
        class="remirror-editor break-all text-xs leading-relaxed text-gray-700 sm:text-sm dark:text-gray-300 comment-content">
      <div class="remirror-theme">
        <div class="remirror-editor-wrapper">
          <div contenteditable="false" role="textbox" aria-multiline="true" aria-readonly="true" aria-label=""
               aria-placeholder="내용을 입력해주세요." translate="no" id="comment-content"
               class="ProseMirror remirror-editor remirror-a11y-dark"><p
              column-span="none">{{ qnaComment.answerComment }}</p>
          </div>
        </div>
      </div>
    </div>
    <div class="comment-button">
      <button v-if="isSuperNull" @click="toggleContent" class="mt-2 text-sm text-blue-500">
        {{ isContentVisible ? '대댓글 숨기기' : '대댓글 보기' }}
      </button>
      <button v-if="(isSuperNull || isTopComment) && useUserStore().isLoggedIn" @click="writeRipple"
              class="mt-2 text-sm text-blue-500" style="margin-left: 10px;">
        {{ isRegistered ? '작성 취소' : '대댓글 작성' }}
      </button>
    </div>
    <div v-if="isRegistered">
      <QnaCommentRegisterComponent v-bind:answer="qnaAnswer" v-bind:comment="qnaComment"
                                   @requestUpdate="updateComment" @recomment-registered="handleRecommentRegistered"/>
    </div>
    <div v-if="isContentVisible">
      <div v-if="isLoading"></div>
      <QnaCommentDetailComponent v-else
                                 v-for="qnaComment in filteredComments"
                                 :key="qnaComment.id"
                                 :qnaComment="qnaComment"
      />
    </div>
  </li>
</template>

<script>
import {formatDateTime} from "@/utils/FormatDate";
import QnaCommentRegisterComponent from "@/components/Qna/Register/QnaCommentRegisterComponent.vue";
import NicknameComponent from "@/components/Common/NicknameComponent.vue";
import {useUserStore} from "@/store/useUserStore";

export default {
  name: "QnaCommentDetailComponent",
  data() {
    return {
      isLoading: true,
      isRegistered: false,
      isContentVisible: false,
      isSuperNull: true,
      isTopComment: true,
      myComments: []

    };
  },
  props: ["qnaComment", "qnaAnswer"],
  methods: {
    useUserStore,
    formatDateTime,
    toggleContent() {
      this.isContentVisible = !this.isContentVisible;
    },
    writeRipple() {
      this.isRegistered = !this.isRegistered;
    },
    updateComment() {
      if (this.qnaAnswer !== undefined) {
        this.myComments = this.qnaAnswer.comments
        this.isSuperNull = true;
      } else {
        this.myComments = [];
        this.isTopComment = false;
      }
    },
    handleRecommentRegistered(success) {
      if (success) {
        this.isRegistered = false;
        this.isContentVisible = true;
      }
    },
  },
  mounted() {
    this.isLoading = false
    this.isRegistered = false
    this.isSuperNull = true
    if (this.qnaAnswer !== undefined) {
      this.myComments = this.qnaAnswer.comments
      this.isSuperNull = true;
    } else {
      this.myComments = [];
      this.isTopComment = false;
    }
  },
  components: {NicknameComponent, QnaCommentRegisterComponent},
  computed: {
    filteredComments() {
      return Array.isArray(this.myComments) ?
          this.myComments.filter(c => c.superCommentId !== null && (c.superCommentId === this.qnaComment.id)) : [];
    },
  },
  watch: {
    filteredComments(newComments) {
      this.isSuperNull = newComments.length === 0 ? false : this.isSuperNull;
    },
  },
}
</script>

<style>
@media (prefers-color-scheme: dark) {
  .dark\:text-gray-100 {
    --tw-text-opacity: 1;
    color: black;
  }
}

#comment-content {
  color: black;
}

.recomment {
  margin-left: 30px;
}

.comment-content {
  margin-top: 5px;
  margin-left: 45px;
  font-weight: 700;
  font-size: 13px;
}

.comment-button {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 10px;

}
</style>