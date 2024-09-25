<template>
  <div class="c-opinion__write">
    <fieldset>
      <legend>댓글 입력</legend>
      <div class="c-opinion__write-form">
        <textarea
            class="c-opinion__write-textarea"
            maxlength="1000"
            title="댓글 입력"
            placeholder="댓글은 최대 1000자 까지만 입력이 가능합니다. 상대방과 더 깊은 대화를 나누기를 원하시면, 1:1 채팅 서비스를 이용하실 수 있습니다."
            v-model="myComment"
        ></textarea>
      </div>
      <div class="c-opinion__write-upload">
        <div class="c-opinion__write-count">
          <span class="blind">현재 입력한 글자수</span>
          <span class="c-opinion__write-count__num _currentCharCount">0</span>
          /
          <span class="blind">전체 입력 가능한 글자수 </span>
          <span class="c-opinion__write-count__total">1000</span>
        </div>
        <button type="button" id="register_comment_button_0" class="endIconButton _registerCommentBtn"
                @click="registerComment"
                aria-disabled="true">
					<span class="endButtonIcon iconSendBlue">
						<svg width="32" height="32" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
							<g clip-path="url(#clip0_380_168339)">
								<path fill-rule="evenodd" clip-rule="evenodd"
                      d="M24.4835 18.1846C25.951 17.5146 25.951 15.4298 24.4835 14.7599L10.5406 8.39467C9.04145 7.71025 7.44883 9.14642 7.97513 10.7082L9.60051 15.5313H12.2352C12.7549 15.5313 13.1763 15.9527 13.1763 16.4725C13.1763 16.9923 12.7549 17.4136 12.2351 17.4136H9.60035L7.97513 22.2363C7.44883 23.798 9.04145 25.2342 10.5406 24.5498L24.4835 18.1846Z"
                      fill="#DCE0E3"></path>
								<path fill-rule="evenodd" clip-rule="evenodd"
                      d="M24.4835 18.1846C25.951 17.5146 25.951 15.4298 24.4835 14.7599L10.5406 8.39467C9.04145 7.71025 7.44883 9.14642 7.97513 10.7082L9.60051 15.5313H12.2352C12.7549 15.5313 13.1763 15.9527 13.1763 16.4725C13.1763 16.9923 12.7549 17.4136 12.2351 17.4136H9.60035L7.97513 22.2363C7.44883 23.798 9.04145 25.2342 10.5406 24.5498L24.4835 18.1846Z"
                      fill="#DCE0E3"></path>
							</g>
							<defs>
								<clipPath id="clip0_380_168339">
									<rect width="32" height="32" fill="white"></rect>
								</clipPath>
							</defs>
						</svg>
						<span class="blind">등록</span>
					</span>
        </button>
      </div>
    </fieldset>
  </div>
</template>

<script>
import {mapStores} from "pinia";
import {useQnaStore} from "@/store/useQnaStore";

export default {
  name: "QnACommentRegisterComponent",
  data() {
    return {
      myComment: '',
      qnaAnswerId: this.answer !== undefined || null ? this.answer.id : null,
      qnaCommentId: this.comment !== undefined || null ? this.comment.id : null
    };
  },
  computed: {
    ...mapStores(useQnaStore),
  },
  props: ["comment", "answer"],
  methods: {
    async registerComment() {
      if (!this.myComment) {
        alert('댓글이 입력되어 있지 않습니다.');
      } else {
        await useQnaStore().registerComment(this.qnaAnswerId, this.qnaCommentId, this.myComment);
        await useQnaStore().getQnaDetail(this.$route.params.id);
        this.requestUpdate();
        this.commentClear();
          alert('등록이 완료되었습니다.');
      }
    },
    requestUpdate() {
      this.$emit('requestUpdate');
    },
    commentClear(){
      this.myComment="";
    }
  },
}
</script>

<style scoped>
.endButtonIcon {
  display: inline-block;
  line-height: 0;
  vertical-align: top
}

.endButtonIcon svg {
  pointer-events: none
}

.endIconButton {
  vertical-align: top
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

body, button, div, fieldset, form, h1, h2, h3, h4, h5, h6, legend, textarea {
  margin: 0;
  padding: 0
}

body, button, textarea {
  font-family: -apple-system, BlinkMacSystemFont, Helvetica, "Apple SD Gothic Neo", "Malgun Gothic", sans-serif;
  font-size: 12px;
  line-height: 1.2
}

button, fieldset {
  border: 0
}

button {
  outline: 0;
  background-color: rgba(0, 0, 0, 0);
  cursor: pointer
}

button[disabled] {
  cursor: default
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