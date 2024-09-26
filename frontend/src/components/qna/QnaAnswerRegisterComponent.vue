<template>
  <div class="c-opinion__write">
    <fieldset>
      <legend>댓글 입력</legend>
      <div class="c-opinion__write-form">
        <div class="space-y-1">
          <label for="text" class="ans-register-head text-gray-700 dark:text-gray-300">답변 등록하기</label>
          <v-md-editor v-model="myAnswer" :disabled-menus="[]" @upload-image="commonStore.imageUpload"
                       height="400px"></v-md-editor>
        </div>
      </div>
    </fieldset>
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
</template>

<script>
import {mapStores} from "pinia";
import {useQnaStore} from "@/store/useQnaStore";
import {useCommonStore} from "@/store/useCommonStore";

export default {
  name: "QnaAnswerRegisterComponent",
  data() {
    return {
      myAnswer: '',
      myCategory: 0,
    };
  },
  computed: {
    ...mapStores(useQnaStore),
    ...mapStores(useCommonStore),
    isNullOrEmpty() {
      return this.myAnswer === '';
    },
  },
  methods: {
    async click() {
      if (!this.myAnswer) {
        alert('모든 필드를 올바르게 입력해 주세요.');
      } else {
        try {
          await useQnaStore().registerAnswer(this.$route.params.id, this.myAnswer);
          await useQnaStore().getQnaDetail(this.$route.params.id);
          alert('등록이 완료되었습니다.');
          this.cancel();
        } catch (error) {
          alert('등록 중 오류 발생');
        }
      }
    },
    cancel() {
      this.myAnswer = '';
    },
  }
}
</script>

<style scoped>
.space-y-1 > :not([hidden]) ~ :not([hidden]) {
  --tw-space-y-reverse: 0;
  margin-top: calc(0.9rem* calc(1 - var(--tw-space-y-reverse)));
  margin-bottom: calc(0.25rem* var(--tw-space-y-reverse));
}

.ans-register-head{
  text-align: center;
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 10px;
}

fieldset[data-v-0571ceb3] {
  margin-bottom: 13px;
  border: 0;
}

@media (prefers-color-scheme: dark) {
  .dark\:bg-gray-700 {
    --tw-bg-opacity: 1;
    background-color: #db282873;
  }
}

.endButtonIcon svg {
  pointer-events: none
}

.endIconButton[aria-disabled=false] .iconSendBlue path {
  fill: #3a6dff
}

@keyframes showButton {
  to {
    opacity: 1;
    bottom: 40px
  }
}

@keyframes layerActive {

0
{
  max-height: 100px
;
  opacity: 0
}
50
%
{
  opacity: 1
}
100
%
{
  max-height: 100vh
}
}
.c-opinion {
  position: relative
}

.c-opinion__write {
  position: relative;
  padding: 20px;
  border: 1.5px solid #dfe0e3;
  border-radius: 16px;
  background-color: #fff
}

.c-opinion__write:focus-within {
  outline-style: auto;
  outline-color: -webkit-focus-ring-color
}

.c-opinion__write-textarea {
  display: block;
  width: 100%;
  height: 82px;
  border: 0;
  color: #333;
  font-size: 16px;
  line-height: 26px;
  letter-spacing: -.3px;
  word-wrap: break-word;
  word-break: keep-all;
  resize: none;
  -webkit-appearance: none
}

.c-opinion__write-textarea.placeholder {
  opacity: .3
}

.c-opinion__write-textarea:focus {
  outline: 0
}

.c-opinion__write-upload {
  position: relative;
  height: 32px;
  margin-top: 12px
}

.c-opinion__write-upload:after {
  display: block;
  clear: both;
  content: ""
}

.c-opinion__write-upload .endIconButton {
  position: absolute;
  right: 0;
  bottom: 0
}

.c-opinion__write-count {
  display: inline-block;
  margin-top: 6px;
  color: #8f8f8f;
  font-size: 13px;
  line-height: 20px
}

.c-opinion__write-count__num {
  color: #333
}

@keyframes popup_loading_1 {
  20% {
    transform: scale(0)
  }
  60% {
    transform: scale(0)
  }
  70% {
    transform: scale(1)
  }
  to {
    transform: scale(1)
  }
}

@keyframes popup_loading_2 {
  10% {
    transform: scale(1)
  }
  30% {
    transform: scale(0)
  }
  70% {
    transform: scale(0)
  }
  80% {
    transform: scale(1)
  }
  to {
    transform: scale(1)
  }
}

@keyframes popup_loading_3 {
  20% {
    transform: scale(1)
  }
  40% {
    transform: scale(0)
  }
  80% {
    transform: scale(0)
  }
  90% {
    transform: scale(1)
  }
  to {
    transform: scale(1)
  }
}

@keyframes bounce {
  20%, 53%, 80%, from, to {
    transform: translate3d(0, 0, 0);
    animation-timing-function: cubic-bezier(.215, .61, .355, 1)
  }
  40%, 43% {
    transform: translate3d(0, -30px, 0);
    animation-timing-function: cubic-bezier(.755, .05, .855, .06)
  }
  70% {
    transform: translate3d(0, -15px, 0);
    animation-timing-function: cubic-bezier(.755, .05, .855, .06)
  }
  90% {
    transform: translate3d(0, -4px, 0)
  }
}

@font-face {
  font-family: kin_iconfont;
  src: url(https://ssl.pstatic.net/static/kin/iconfont/kin_iconfont_883933.woff) format("woff")
}

fieldset {
  border: 0
}

.blind, legend {
  overflow: hidden;
  position: absolute;
  width: 1px;
  height: 1px;
  margin: -1px;
  clip: rect(0 0 0 0)
}

@font-face {
  font-family: NanumSquareNeo;
  font-weight: 400;
  src: url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-bRg.eot);
  src: url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-bRg.eot?#iefix) format("embedded-opentype"), url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-bRg.woff2) format("woff2"), url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-bRg.woff) format("woff"), url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-bRg.ttf) format("truetype")
}

@font-face {
  font-family: NanumSquareNeo;
  font-weight: 700;
  src: url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-cBd.eot);
  src: url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-cBd.eot?#iefix) format("embedded-opentype"), url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-cBd.woff2) format("woff2"), url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-cBd.woff) format("woff"), url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-cBd.ttf) format("truetype")
}

@font-face {
  font-family: NanumSquareNeo;
  font-weight: 800;
  src: url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-dEb.eot);
  src: url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-dEb.eot?#iefix) format("embedded-opentype"), url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-dEb.woff2) format("woff2"), url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-dEb.woff) format("woff"), url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-dEb.ttf) format("truetype")
}

@font-face {
  font-family: NanumSquareNeo;
  font-weight: 900;
  src: url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-eHv.eot);
  src: url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-eHv.eot?#iefix) format("embedded-opentype"), url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-eHv.woff2) format("woff2"), url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-eHv.woff) format("woff"), url(https://ssl.pstatic.net/static/kin/fonts/NanumSquareNeoTTF-eHv.ttf) format("truetype")
}

.data_none {
  text-align: center;
  padding: 48px 0 46px;
  font-size: 11px;
  line-height: 18px;
  color: #969696;
  border-bottom: 1px solid #f3f3f3;
  letter-spacing: -1px
}

@keyframes border_rotate {
  from {
    opacity: 0;
    -ms-transform: rotate(0);
    transform: rotate(0)
  }
  50% {
    opacity: 1
  }
  to {
    opacity: 0;
    -ms-transform: rotate(360deg);
    transform: rotate(360deg)
  }
}

@keyframes seq {
  from {
    background-position: 0 0
  }
  to {
    background-position: 0 -4800%
  }
}

@keyframes star_animate {
  from {
    background-position: 0 0
  }
  to {
    background-position: 0 -4800%
  }
}

@keyframes profile_btn_area {
  from {
    border-color: #00f
  }
  to {
    border-color: red
  }
}

@keyframes kini_profile {
  from {
    transform: translateY(0)
  }
  50% {
    transform: translateY(-100%)
  }
  to {
    transform: translateY(-100%)
  }
}

@-ms-keyframes kini_profile {
  from {
    top: 0
  }
  50% {
    top: -8900%
  }
  to {
    top: -8900%
  }
}
</style>