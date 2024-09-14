<template>
  <div class="card">
    <div class="mantine-2j9uwr">
      <div class="mantine-1uguyhf">
        <a class="mantine-Avatar-root mantine-18l6s08">
          <img
              class="mantine-9rx0rd mantine-Avatar-image"
              :src="qnaCard.profileImage"
          />
        </a>
        <div class="mantine-Stack-root mantine-1l47z8p">
          <div class="mantine-824czz">
            <a class="mantine-Text-root mantine-3qdwx9">{{
                qnaCard.nickname
              }}</a>
            <div class="mantine-Badge-root mantine-11jjpd0">
              <span class="mantine-1jlwn9k mantine-Badge-inner">{{
                  qnaCard.grade
                }}</span>
            </div>
          </div>
          <p class="mantine-Text-root mantine-1q4x896">
            {{ formatDateTime(qnaCard.createdAt) }}
          </p>
        </div>
      </div>
    </div>
    <div class="qna-title">
      {{ qnaCard.title }}
    </div>
    <div class="footer">
      <div class="test">
        <div>
          <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
          >
            <g stroke-width="0" id="SVGRepo_bgCarrier"></g>
            <g
                stroke-linejoin="round"
                stroke-linecap="round"
                id="SVGRepo_tracerCarrier"
            ></g>
            <g id="SVGRepo_iconCarrier">
              <path
                  stroke-linejoin="round"
                  stroke-linecap="round"
                  stroke-width="1.5"
                  d="M16 10H16.01M12 10H12.01M8 10H8.01M3 10C3 4.64706 5.11765 3 12 3C18.8824 3 21 4.64706 21 10C21 15.3529 18.8824 17 12 17C11.6592 17 11.3301 16.996 11.0124 16.9876L7 21V16.4939C4.0328 15.6692 3 13.7383 3 10Z"
              ></path>
            </g>
          </svg
          >
          {{ qnaCard.answerCnt }}
        </div>
        <div>
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M14 9V5a3 3 0 00-6 0v4H3.75a2.25 2.25 0 00-2.25 2.25v7.5A2.25 2.25 0 003.75 21h12.5a2.25 2.25 0 002.25-2.25v-7.5A2.25 2.25 0 0016.25 9H14zM9 9v12" />
          </svg>
          {{ qnaCard.likeCnt }}
        </div>
      </div>
      <div class="qna-category-highlow">
        <span>{{ qnaCard.superCategoryName }}</span>
        <span v-show="qnaCard.superCategoryName"> / </span>
        <span>{{ qnaCard.subCategoryName }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import {formatDateTime} from "@/utils/FormatDate";

export default {
  name: "QnaCardComponent",
  data() {
    return {};
  },
  props: ["qnaCard"],
  methods: {
    formatDateTime
  },
  components: {
  },
};
</script>

<style scoped>
/* QnA card */
.card {
  position: relative;
  background-color: #ffffff;
  padding: 1em;
  z-index: 5;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
  width: 100%;
  min-height: 150px;
  display: flex;
  flex-direction: column;
  transition: 200ms ease-in-out;
  justify-content: space-between;
  overflow: hidden;
}

.qna-category-highlow {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: var(--main-color);
  font-size: 1em;
  font-weight: 800;
  text-align: right;
  margin-left: auto;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 150px;
}

.qna-category-highlow span:nth-child(1) {
  flex-grow: 3;
  text-align: left;
}

.qna-category-highlow span:nth-child(2) {
  flex-grow: 1;
  text-align: center;
  margin: 2px;
}

.qna-category-highlow span:nth-child(3) {
  flex-grow: 3;
  text-align: right;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.body .text {
  margin: 0 10px 0 0;
  white-space: pre-line;
  color: #c0c3d7;
  font-weight: 400;
  line-height: 1.5;
  margin-bottom: 4px;
}

.footer {
  width: 100%;
  color: #9fa4aa;
  font-size: 12px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: none;
  margin-top: 10px;
}

.footer div {
  height: 20px;
  display: flex;
  align-items: center;
  cursor: pointer;
}

.test > div {
  margin-right: 1rem;
}

.footer svg {
  margin-right: 5px;
  height: 100%;
  stroke: #9fa4aa;
}

.viewer span {
  height: 20px;
  width: 20px;
  background-color: var(--main-color);
  margin-right: -6px;
  border-radius: 50%;
  border: 1px solid #fff;
  display: grid;
  align-items: center;
  text-align: center;
  font-weight: bold;
  font-size: 8px;
  color: #fff;
  padding: 2px;
}

.viewer span svg {
  stroke: #fff;
}

/* QnA card */

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

.mantine-1uguyhf {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  gap: 0.5rem;
}

.mantine-1q4x896 {
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

.mantine-19ok7fu svg {
  width: 14px;
  height: 14px;
}

.mantine-2j9uwr {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  height: 40px;
  margin-bottom: 20px;
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
  font-size: 0.6875rem;
  height: 1.25rem;
  line-height: calc(1.25rem - 0.125rem);
  -webkit-text-decoration: none;
  text-decoration: none;
  padding: 0 calc(1rem / 1.5);
  box-sizing: border-box;
  //display: -webkit-inline-box;
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

.mantine-17p85na:not(.mantine-17p85na:first-of-type) .re-comment-arrow {
  padding-top: 36px;
}

.mantine-17p85na:not(.mantine-17p85na:first-of-type) .re-comment {
  padding-top: 24px;
  border-top: 1px solid #e9ecef;
}

h1 {
  font-size: 2em;
}

pre {
  font-size: 1em;
}

a {
  background: 0 0;
  //text-decoration-skip: objects;
}

a:active,
a:hover {
  outline-width: 0;
}

b {
  font-weight: bolder;
}

code {
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
  //appearance: button;
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

a,
b,
code,
div,
h1,
h2,
h3,
h4,
h5,
h6,
img,
label,
p,
pre,
q,
s,
span {
  margin: 0;
  padding: 0;
  border: 0;
  font-size: 11px;
  vertical-align: baseline;
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

.qna-title {
  text-align: left;
  font-size: 16px;
  font-weight: 700;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

#good-image {
  width: 5px;
  height: 5px;
}
</style>
