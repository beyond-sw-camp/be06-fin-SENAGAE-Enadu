<template>
  <div class="content">
    <div class="question-component qna-background color">
      <div class="rounded-box p-5 mb-10 md:p-8 bento-card" style="background: #efefef; border: 1px solid #19192c">
        <div class="mb-10">
          <div class="flex justify-between">
            <div class="question-answer mb-5 build-section-card-title">
              Q.
              {{ qnaDetail.title }}
            </div>
            <div
                class="w-16 h-8 transition"
            >
              <button class="red-button-size ui inverted red button">edit</button>
            </div>
          </div>
        </div>
        <div class="" style="font-size: 13px">
          <div>
            <v-md-preview :text="qnaDetail.content"/>
            <aside class="bg-black text-white p-6 rounded-lg w-full max-w-md font-mono">
              <div class="flex justify-between items-center">
                <div class="flex space-x-2 text-red-500">
                  <div class="w-3 h-3 rounded-full bg-red-500"></div>
                  <div class="w-3 h-3 rounded-full bg-yellow-500"></div>
                  <div class="w-3 h-3 rounded-full bg-green-500"></div>
                </div>
                <p class="text-sm">{{ qnaDetail.superCategoryName }}</p>
              </div>

              <div class="mt-4">
                <p class="text-green-400">@RestController</p>
                <p class="text-green-400">@RequestMapping("/test")</p>
                <p class="text-white">&nbsp;public class TestJenController {</p>
                <p class="text-green-400">&nbsp;&nbsp;@GetMapping()</p>
                <p class="text-white">&nbsp;&nbsp;public String saveTest() {</p>
                <p class="text-white">&nbsp;&nbsp;&nbsp;return "test";</p>
                <p class="text-white">&nbsp;&nbsp;}</p>
                <p class="text-white">}</p>
              </div>
            </aside>
          </div>
          <div data-v-472a7c05="" class="ans-button-divider">
            <button data-v-472a7c05="" class="mt-2 text-sm text-blue-500" @click="writeAnswer">
              {{ isAnswerRegister ? '작성 취소' : '답변 작성' }}
            </button>
          </div>
        </div>
        <div v-if="isAnswerRegister">
          <QnaAnswerRegisterComponent/>
        </div>
      </div>
    </div>
    <footer></footer>
  </div>
</template>

<script>
import {formatDateTime} from "@/utils/FormatDate";
import VMdPreview from "@kangc/v-md-editor/lib/preview";
import QnaAnswerRegisterComponent from "@/components/qna/QnaAnswerRegisterComponent.vue";

export default {
  name: "QuestionDetailComponent",
  data() {
    return {
      isAnswerRegister: false,
    };
  },
  props: ["qnaDetail"],
  mounted() {
    this.isAnswerRegister = false;
  },
  methods: {
    formatDateTime,
    writeAnswer() {
      this.isAnswerRegister = !this.isAnswerRegister;
    },
  },
  components: {
    QnaAnswerRegisterComponent,
    VMdPreview
  },
};
</script>

<style scoped>
.content {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
}

.ans-button-divider {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
  margin-bottom: 20px;
}

.rounded-box {
  border-radius: 20px;
}

.circle {
  margin-left: 25px;
  margin-bottom: 10px;
  width: 120px;
  height: 120px;
  overflow: hidden;
  border-radius: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.circle img {
  width: 200%;
  height: auto;
}

img {
  max-width: 200%;
  height: auto;
}

/* 원형 이미지 */

.title-text {
  font-family: "auto";
  margin-left: 50px;
  font-size: 43px;
  font-weight: 900;
}

.username-text {
  margin-left: 20px;
  margin-bottom: 10px;
  font-size: 20px;
  font-weight: 900;
}

.datetime-text {
  margin-left: 10px;
  margin-bottom: 10px;
  font-size: 16px;
  font-weight: 600;
}

.label-custom {
  margin-left: 20px;
}

.qna-background-color {
  background-color: #efefef;
}

/* like */
.like-dislike-container {
  --dark-grey: #353535;
  --middle-grey: #767676;
  --border-radius-icon: 50px;
  position: relative;
  display: flex;
  text-align: center;
  flex-direction: row;
  align-items: center;
  cursor: default;
  color: var(--dark-grey);
  opacity: 0.9;
  margin-left: auto;
  padding: 0rem;
  font-weight: 600;
  background: var(--lightest-grey);
  max-width: max-content;
  border-radius: var(--border-radius-main);
  box-shadow: var(--shadow);
  transition: 0.2s ease all;
}

.like-dislike-container:hover {
  box-shadow: var(--shadow-active);
}

.like-dislike-container .tool-box {
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 2.5rem;
  height: 2.5rem;
  top: 0;
  right: 0;
  border-radius: var(--border-radius-main);
}

.like-dislike-container .btn-close {
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  width: 0.8rem;
  height: 0.8rem;
  color: transparent;
  font-size: 0;
  cursor: pointer;
  background-color: #ff000080;
  border: none;
  border-radius: var(--border-radius-main);
  transition: 0.2s ease all;
}

.like-dislike-container .btn-close:hover {
  width: 1rem;
  height: 1rem;
  font-size: 1rem;
  color: #ffffff;
  background-color: #ff0000cc;
  box-shadow: var(--shadow-active);
}

.like-dislike-container .btn-close:active {
  width: 0.9rem;
  height: 0.9rem;
  font-size: 0.9rem;
  color: #ffffffde;
  --shadow-btn-close: 0 3px 3px 0 #00000026;
  box-shadow: var(--shadow-btn-close);
}

.like-dislike-container .text-content {
  margin-bottom: 1rem;
  font-size: 18px;
  line-height: 1.6;
  cursor: default;
}

.like-dislike-container .icons-box {
  display: flex;
}

.like-dislike-container .icons {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 0.6;
  margin: 0 0.5rem;
  cursor: pointer;
  user-select: none;
  border: 1px solid var(--middle-grey);
  border-radius: var(--border-radius-icon);
  transition: 0.2s ease all;
}

.like-dislike-container .icons:hover {
  opacity: 0.9;
  box-shadow: var(--shadow);
}

.like-dislike-container .icons:active {
  opacity: 0.9;
  box-shadow: var(--shadow-active);
}

.like-dislike-container .icons .btn-label {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 0.5rem;
  cursor: pointer;
  position: relative;
}

.like-dislike-container .like-text-content {
  border-right: 0.1rem solid var(--dark-grey);
  padding: 0 0.6rem 0 0.5rem;
  pointer-events: none;
}

.like-dislike-container .dislike-text-content {
  border-left: 0.1rem solid var(--dark-grey);
  padding: 0 0.5rem 0 0.6rem;
  pointer-events: none;
}

.like-dislike-container .icons .svgs {
  width: 1.3rem;
  fill: #000000;
  box-sizing: content-box;
  padding: 10px 10px;
  transition: 0.2s ease all;
}

/* Hide the default checkbox */
.like-dislike-container .icons .input-box {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}

.like-dislike-container .icons #icon-like-regular {
  display: block;
}

.like-dislike-container .icons #icon-like-solid {
  display: none;
}

.like-dislike-container .icons:hover :is(#icon-like-solid, #icon-like-regular) {
  animation: rotate-icon-like 0.7s ease-in-out both;
}

.like-dislike-container .icons #like-checkbox:checked ~ #icon-like-regular {
  display: none;
  animation: checked-icon-like 0.5s;
}

.like-dislike-container .icons #like-checkbox:checked ~ #icon-like-solid {
  display: block;
  animation: checked-icon-like 0.5s;
}

.like-dislike-container .icons #icon-dislike-regular {
  display: block;
  transform: rotate(180deg);
}

.like-dislike-container .icons #icon-dislike-solid {
  display: none;
  transform: rotate(180deg);
}

.like-dislike-container
.icons:hover
:is(#icon-dislike-solid, #icon-dislike-regular) {
  animation: rotate-icon-dislike 0.7s ease-in-out both;
}

.like-dislike-container
.icons
#dislike-checkbox:checked
~ #icon-dislike-regular {
  display: none;
  animation: checked-icon-dislike 0.5s;
}

.like-dislike-container .icons #dislike-checkbox:checked ~ #icon-dislike-solid {
  display: block;
  animation: checked-icon-dislike 0.5s;
}

.like-dislike-container .icons .fireworks {
  transform: scale(0.4);
}

.like-dislike-container
.icons
#like-checkbox:checked
~ .fireworks
> .checked-like-fx {
  position: absolute;
  width: 10px;
  height: 10px;
  right: 40px;
  border-radius: 50%;
  box-shadow: 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff;
  animation: 1s fireworks-bang ease-out forwards,
  1s fireworks-gravity ease-in forwards, 5s fireworks-position linear forwards;
  animation-duration: 1.25s, 1.25s, 6.25s;
}

.like-dislike-container
.icons
#dislike-checkbox:checked
~ .fireworks
> .checked-dislike-fx {
  position: absolute;
  width: 10px;
  height: 10px;
  left: 40px;
  border-radius: 50%;
  box-shadow: 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff;
  animation: 1s fireworks-bang ease-out forwards,
  1s fireworks-gravity ease-in forwards, 5s fireworks-position linear forwards;
  animation-duration: 1.25s, 1.25s, 6.25s;
}

/* Shake Animation */
@keyframes rotate-icon-like {
  0% {
    transform: rotate(0deg) translate3d(0, 0, 0);
  }

  25% {
    transform: rotate(3deg) translate3d(0, 0, 0);
  }

  50% {
    transform: rotate(-3deg) translate3d(0, 0, 0);
  }

  75% {
    transform: rotate(1deg) translate3d(0, 0, 0);
  }

  100% {
    transform: rotate(0deg) translate3d(0, 0, 0);
  }
}

@keyframes rotate-icon-dislike {
  0% {
    transform: rotate(180deg) translate3d(0, 0, 0);
  }

  25% {
    transform: rotate(183deg) translate3d(0, 0, 0);
  }

  50% {
    transform: rotate(177deg) translate3d(0, 0, 0);
  }

  75% {
    transform: rotate(181deg) translate3d(0, 0, 0);
  }

  100% {
    transform: rotate(180deg) translate3d(0, 0, 0);
  }
}

/* Checked Animation */
@keyframes checked-icon-like {
  0% {
    transform: scale(0);
    opacity: 0;
  }

  50% {
    transform: scale(1.2) rotate(-10deg);
  }
}

@keyframes checked-icon-dislike {
  0% {
    transform: scale(0) rotate(180deg);
    opacity: 0;
  }

  50% {
    transform: scale(1.2) rotate(170deg);
  }
}

/* Fireworks Animation */
@keyframes fireworks-position {
  0%,
  19.9% {
    margin-top: 10%;
    margin-left: 40%;
  }

  20%,
  39.9% {
    margin-top: 40%;
    margin-left: 30%;
  }

  40%,
  59.9% {
    margin-top: 20%;
    margin-left: 70%;
  }

  60%,
  79.9% {
    margin-top: 30%;
    margin-left: 20%;
  }

  80%,
  99.9% {
    margin-top: 30%;
    margin-left: 80%;
  }
}

@keyframes fireworks-gravity {
  to {
    transform: translateY(200px);
    opacity: 0;
  }
}

@keyframes fireworks-bang {
  to {
    box-shadow: 114px -107.3333333333px #8800ff, 212px -166.3333333333px #a600ff,
    197px -6.3333333333px #ff006a, 179px -329.3333333333px #3300ff,
    -167px -262.3333333333px #ff0062, 233px 65.6666666667px #ff008c,
    81px 42.6666666667px #0051ff, -13px 54.6666666667px #00ff2b,
    -60px -183.3333333333px #0900ff, 127px -259.3333333333px #ff00e6,
    117px -122.3333333333px #00b7ff, 95px 20.6666666667px #ff8000,
    115px 1.6666666667px #0004ff, -160px -328.3333333333px #00ff40,
    69px -242.3333333333px #000dff, -208px -230.3333333333px #ff0400,
    30px -15.3333333333px #e6ff00, 235px -15.3333333333px #fb00ff,
    80px -232.3333333333px #d5ff00, 175px -173.3333333333px #00ff3c,
    -187px -176.3333333333px #aaff00, 4px 26.6666666667px #ff6f00,
    227px -106.3333333333px #ff0099, 119px 17.6666666667px #00ffd5,
    -102px 4.6666666667px #ff0088, -16px -4.3333333333px #00fff7,
    -201px -310.3333333333px #00ffdd, 64px -181.3333333333px #f700ff,
    -234px -15.3333333333px #00fffb, -184px -263.3333333333px #aa00ff,
    96px -303.3333333333px #0037ff, -139px 10.6666666667px #0026ff,
    25px -205.3333333333px #00ff2b, -129px -322.3333333333px #40ff00,
    -235px -187.3333333333px #26ff00, -136px -237.3333333333px #0091ff,
    -82px -321.3333333333px #6a00ff, 7px -267.3333333333px #ff00c8,
    -155px 30.6666666667px #0059ff, -85px -73.3333333333px #6a00ff,
    60px -199.3333333333px #55ff00, -9px -289.3333333333px #00ffaa,
    -208px -167.3333333333px #00ff80, -13px -299.3333333333px #ff0004,
    179px -164.3333333333px #ff0044, -112px 12.6666666667px #0051ff,
    -209px -125.3333333333px #ff00bb, 14px -101.3333333333px #00ff95,
    -184px -292.3333333333px #ff0099, -26px -168.3333333333px #09ff00,
    129px -67.3333333333px #0084ff, -17px -23.3333333333px #0059ff,
    129px 34.6666666667px #7300ff, 35px -24.3333333333px #ffd900,
    -12px -297.3333333333px #ff8400, 129px -156.3333333333px #0dff00,
    157px -29.3333333333px #1a00ff, -221px 6.6666666667px #ff0062,
    0px -311.3333333333px #ff006a, 155px 50.6666666667px #00ffaa,
    -71px -318.3333333333px #0073ff;
  }
}

/* like */

/* book mark */
.bookmark-checkbox {
  display: inline-block;
}

.bookmark-checkbox__input {
  display: none;
}

.bookmark-checkbox__label {
  cursor: pointer;
}

.bookmark-checkbox__icon {
  margin-left: 20px;
  width: 2em;
  height: 2em;
  fill: none;
  stroke-width: 2;
  stroke-linecap: round;
  stroke-linejoin: round;
  transition: stroke 0.3s, fill 0.3s;
}

.bookmark-checkbox__icon-back {
  stroke: #333;
  transition: transform 0.3s;
}

.bookmark-checkbox__icon-check {
  stroke: #fff;
  stroke-dasharray: 16;
  stroke-dashoffset: 16;
  transition: stroke-dashoffset 0.3s, transform 0.3s;
  transform: translateX(2px);
}

.bookmark-checkbox__input:checked
+ .bookmark-checkbox__label
.bookmark-checkbox__icon {
  fill: #ff5a5f;
}

.bookmark-checkbox__input:checked
+ .bookmark-checkbox__label
.bookmark-checkbox__icon-back {
  stroke: #ff5a5f;
  transform: scale(1.1);
  animation: bookmark-pop 0.4s ease;
}

.bookmark-checkbox__input:checked
+ .bookmark-checkbox__label
.bookmark-checkbox__icon-check {
  stroke-dashoffset: 0;
  transition-delay: 0.2s;
}

@keyframes bookmark-pop {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1.1);
  }
}

/* book mark */

element.style {
  background: #fff;
  border: 1px solid #19192c;
}

.bg-black.text-white.p-6.rounded-lg.w-full.max-w-md.font-mono {
  max-width: 70rem;
  margin: auto;
}

/* 댓글 */
.mantine-9rx0rd {
  object-fit: cover;
  width: 100%;
  height: 100%;
  display: block;
}

.mantine-18l6s08 {
  -webkit-tap-highlight-color: transparent;
  box-sizing: border-box;
  position: relative;
  display: block;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  overflow: hidden;
  border-radius: 200%;
  border-top-left-radius: 200%;
  border-top-right-radius: 200%;
  border-bottom-right-radius: 200%;
  border-bottom-left-radius: 200%;
  -webkit-text-decoration: none;
  text-decoration: none;
  border: 0;
  background-color: transparent;
  padding: 0;
  width: 5rem;
  height: 5rem;
  border: 1px solid #dee2e6;
}

.mantine-18l6s09 {
  -webkit-tap-highlight-color: transparent;
  box-sizing: border-box;
  position: relative;
  display: block;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  overflow: hidden;
  border-radius: 200%;
  border-top-left-radius: 200%;
  border-top-right-radius: 200%;
  border-bottom-right-radius: 200%;
  border-bottom-left-radius: 200%;
  -webkit-text-decoration: none;
  text-decoration: none;
  border: 0;
  background-color: transparent;
  padding: 0;
  width: 5rem;
  height: 5rem;
  border: 1px solid #dee2e6;
  justify-content: center;
  align-items: center;
}

.mantine-9p81l6 {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
  sans-serif;
  -webkit-tap-highlight-color: transparent;
  color: #495057;
  font-size: 0.75rem;
  line-height: 1.5;
  -webkit-text-decoration: none;
  text-decoration: none;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  line-height: 1.5;
  text-underline-position: under;
}

.mantine-9p81l6:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}

.mantine-9p81l6:focus:not(:focus-visible) {
  outline: 0;
}

.mantine-1uguyhf {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  gap: 0.5rem;
}

.mantine-1q4x896 {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
  sans-serif;
  -webkit-tap-highlight-color: transparent;
  color: #868e96;
  font-size: 0.875rem;
  line-height: 1.5;
  -webkit-text-decoration: none;
  text-decoration: none;
  line-height: 1.5;
  text-underline-position: under;
}

.mantine-1q4x896:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}

.mantine-1q4x896:focus:not(:focus-visible) {
  outline: 0;
}

.mantine-1yjkc96 {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  -webkit-justify-content: center;
  justify-content: center;
  height: 100%;
  overflow: visible;
  pointer-events: none;
}

.mantine-1ryt1ht {
  white-space: nowrap;
  height: 100%;
  overflow: hidden;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}

.mantine-19ok7fu {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  margin-right: 0.625rem;
  margin-right: 6px;
}

.mantine-19ok7fu svg {
  width: 14px;
  height: 14px;
}

.mantine-k7c4r8 {
  -webkit-tap-highlight-color: transparent;
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
  sans-serif;
  cursor: pointer;
  border: 0;
  padding: 0;
  -webkit-appearance: none;
  -moz-appearance: none;
  -ms-appearance: none;
  appearance: none;
  font-size: 1rem;
  background-color: transparent;
  text-align: left;
  color: #000;
  -webkit-text-decoration: none;
  text-decoration: none;
  box-sizing: border-box;
  height: 2.25rem;
  padding-left: calc(1.125rem / 1.5);
  padding-right: 1.125rem;
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
  sans-serif;
  -webkit-tap-highlight-color: transparent;
  display: inline-block;
  width: auto;
  border-radius: 2rem;
  font-weight: 600;
  position: relative;
  line-height: 1;
  font-size: 0.875rem;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  cursor: pointer;
  border: 0.0625rem solid #ced4da;
  background-color: #fff;
  color: #000;
  color: #212529;
  padding-left: 18px;
}

.mantine-k7c4r8:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}

.mantine-k7c4r8:focus:not(:focus-visible) {
  outline: 0;
}

.mantine-k7c4r8:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}

.mantine-k7c4r8:focus:not(:focus-visible) {
  outline: 0;
}

@media (hover: hover) {
  .mantine-k7c4r8:hover {
    background-color: #f8f9fa;
  }
}

@media (hover: none) {
  .mantine-k7c4r8:active {
    background-color: #f8f9fa;
  }
}

.mantine-k7c4r8:active {
  -webkit-transform: translateY(0.0625rem);
  -moz-transform: translateY(0.0625rem);
  -ms-transform: translateY(0.0625rem);
  transform: translateY(0.0625rem);
}

.mantine-k7c4r8:disabled,
.mantine-k7c4r8[data-disabled] {
  border-color: transparent;
  background-color: #e9ecef;
  color: #adb5bd;
  cursor: not-allowed;
  background-image: none;
  pointer-events: none;
}

.mantine-k7c4r8:disabled:active,
.mantine-k7c4r8[data-disabled]:active {
  -webkit-transform: none;
  -moz-transform: none;
  -ms-transform: none;
  transform: none;
}

.mantine-k7c4r8[data-loading] {
  pointer-events: none;
}

.mantine-k7c4r8[data-loading]::before {
  content: "";
  position: absolute;
  top: -0.0625rem;
  right: -0.0625rem;
  left: -0.0625rem;
  bottom: -0.0625rem;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 2rem;
  cursor: not-allowed;
}

.mantine-1qj7q0z {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  -webkit-justify-content: center;
  justify-content: center;
  width: 2.25rem;
  pointer-events: none;
}

.mantine-1ttseea {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-flex-direction: column;
  -ms-flex-direction: column;
  flex-direction: column;
  -webkit-align-items: stretch;
  -webkit-box-align: stretch;
  -ms-flex-align: stretch;
  align-items: stretch;
  -webkit-box-pack: start;
  -ms-flex-pack: start;
  -webkit-justify-content: flex-start;
  justify-content: flex-start;
  gap: 0;
  width: 100%;
  min-width: 0;
}

.mantine-2j9uwr {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  height: 30px;
  -webkit-box-pack: justify;
  -webkit-justify-content: space-between;
  justify-content: space-between;
}

.mantine-18l6s08 {
  -webkit-tap-highlight-color: transparent;
  box-sizing: border-box;
  position: relative;
  display: block;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  overflow: hidden;
  border-radius: 2rem;
  -webkit-text-decoration: none;
  text-decoration: none;
  border: 0;
  background-color: transparent;
  padding: 0;
  width: 2.375rem;
  min-width: 2.375rem;
  height: 2.375rem;
  border: 1px solid #dee2e6;
}

.mantine-18l6s08:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}

.mantine-18l6s08:focus:not(:focus-visible) {
  outline: 0;
}

.mantine-1l47z8p {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-flex-direction: column;
  -ms-flex-direction: column;
  flex-direction: column;
  -webkit-align-items: stretch;
  -webkit-box-align: stretch;
  -ms-flex-align: stretch;
  align-items: stretch;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  -webkit-justify-content: center;
  justify-content: center;
  gap: 0;
}

.mantine-824czz {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  gap: 0.25rem;
}

.mantine-3qdwx9 {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
  sans-serif;
  -webkit-tap-highlight-color: transparent;
  color: #495057;
  font-size: 0.875rem;
  line-height: 1.5;
  -webkit-text-decoration: none;
  text-decoration: none;
  font-weight: 700;
  line-height: 1.5;
  text-underline-position: under;
}

.mantine-3qdwx9:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}

.mantine-3qdwx9:focus:not(:focus-visible) {
  outline: 0;
}

.mantine-1jlwn9k {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 100%;
  letter-spacing: normal;
}

.mantine-11jjpd0 {
  -webkit-tap-highlight-color: transparent;
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
  sans-serif;
  font-size: 0.6875rem;
  height: 25px;
  line-height: calc(1.25rem - 0.125rem);
  -webkit-text-decoration: none;
  text-decoration: none;
  padding: 0 calc(1rem / 1.5);
  box-sizing: border-box;
  display: -webkit-inline-box;
  display: -webkit-inline-flex;
  display: -ms-inline-flexbox;
  display: inline-flex;
  -webkit-align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  -webkit-justify-content: center;
  justify-content: center;
  width: auto;
  text-transform: uppercase;
  border-radius: 0.25rem;
  font-weight: 700;
  letter-spacing: 0.015625rem;
  cursor: inherit;
  text-overflow: ellipsis;
  overflow: hidden;
  background: rgba(229, 249, 241, 1);
  color: #00894f;
  border: 0.0625rem solid transparent;
  text-transform: none;
  padding: 0 8px;
}

.mantine-11jjpd0:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}

.mantine-11jjpd0:focus:not(:focus-visible) {
  outline: 0;
}

.mantine-6va1do {
  margin-top: 0.5rem;
}

.mantine-12kvhdu {
  margin-top: 0.75rem;
}

.mantine-1qj7haw {
  box-sizing: border-box;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-flex-direction: row;
  -ms-flex-direction: row;
  flex-direction: row;
  -webkit-align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-flex-wrap: wrap;
  -webkit-flex-wrap: wrap;
  -ms-flex-wrap: wrap;
  flex-wrap: wrap;
  -webkit-box-pack: start;
  -ms-flex-pack: start;
  -webkit-justify-content: flex-start;
  justify-content: flex-start;
  gap: 0.75rem;
}

.mantine-1qj7haw > * {
  box-sizing: border-box;
  -webkit-box-flex: 0;
  -webkit-flex-grow: 0;
  -ms-flex-positive: 0;
  flex-grow: 0;
}

.mantine-17p85na {
  padding-left: 28px;
  position: relative;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  gap: 0.75rem;
}

.mantine-17p85na:not(.mantine-17p85na:first-of-type) .re-comment-arrow {
  padding-top: 36px;
}

.mantine-17p85na:not(.mantine-17p85na:first-of-type) .re-comment {
  padding-top: 24px;
  border-top: 1px solid #e9ecef;
}

.mantine-155ln8z {
  left: 0;
  font-size: 0;
  position: absolute;
  padding-top: 0.75rem;
  padding-bottom: 0.75rem;
}

.mantine-5n4x4z {
  width: 100%;
}

.mantine-11k5015 {
  border-radius: 16px;
  border: 1px solid #e9ecef;
  padding: 1.5rem;
}

h1 {
  font-size: 2em;
}

pre {
  font-family: monospace, monospace;
  font-size: 1em;
}

a {
  background: 0 0;
  text-decoration-skip: objects;
}

a:active,
a:hover {
  outline-width: 0;
}

b {
  font-weight: bolder;
}

code {
  font-family: monospace, monospace;
  font-size: 1em;
}

img {
  border-style: none;
  vertical-align: middle;
}

svg:not(:root) {
  overflow: hidden;
}

button {
  font-family: sans-serif;
  font-size: 100%;
  line-height: 1.15;
  margin: 0;
}

button {
  overflow: visible;
}

button {
  text-transform: none;
}

[type="reset"],
[type="submit"],
button {
  -webkit-appearance: button;
}

[type="button"]::-moz-focus-inner,
[type="reset"]::-moz-focus-inner,
[type="submit"]::-moz-focus-inner,
button::-moz-focus-inner {
  border-style: none;
  padding: 0;
}

[type="button"]:-moz-focusring,
[type="reset"]:-moz-focusring,
[type="submit"]:-moz-focusring,
button:-moz-focusring {
  outline: 0.0625rem dotted ButtonText;
}

[type="checkbox"],
[type="radio"] {
  box-sizing: border-box;
  padding: 0;
}

[type="number"]::-webkit-inner-spin-button,
[type="number"]::-webkit-outer-spin-button {
  height: auto;
}

[type="search"] {
  -webkit-appearance: none;
  -moz-appearance: none;
  -ms-appearance: none;
  appearance: none;
}

[type="search"]::-webkit-search-cancel-button,
[type="search"]::-webkit-search-decoration {
  -webkit-appearance: none;
  -moz-appearance: none;
  -ms-appearance: none;
  appearance: none;
}

::-webkit-file-upload-button {
  -webkit-appearance: button;
  -moz-appearance: button;
  -ms-appearance: button;
  appearance: button;
  font: inherit;
}

::after,
::before {
  box-sizing: border-box;
}

html {
  -webkit-print-color-scheme: light;
  color-scheme: light;
}

q {
  quotes: none;
}

q:after,
q:before {
  content: none;
}

.answer-name-text {
  font-size: 20px;
}

/* 댓글 */

.red-button-size {
  height: 35px;
}

.question-answer {
  font-size: 30px;
  font-weight: 900;
}

#answer-title-text {
  margin-top: 4rem;
}

#user-profile-image {
  max-width: 200%;
}
</style>
