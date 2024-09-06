<template>
  <div class="card">
    <div class="mantine-2j9uwr">
      <div class="mantine-1uguyhf">
        <a
          class="mantine-Avatar-root mantine-18l6s08"
          href="https://www.inflearn.com/users/14769/@weekendcode"
          ><img
            class="mantine-9rx0rd mantine-Avatar-image"
            src="https://cdn.inflearn.com/public/users/thumbnails/14769/aa751976-0548-441b-9084-d71ff6327348?w=76"
            alt="주말코딩님의 프로필 이미지"
        /></a>
        <div class="mantine-Stack-root mantine-1l47z8p">
          <div class="mantine-824czz">
            <a
              class="mantine-Text-root mantine-3qdwx9"
              href="https://www.inflearn.com/users/14769/@weekendcode"
              >MR KIM</a
            >
            <div class="mantine-Badge-root mantine-11jjpd0">
              <span class="mantine-1jlwn9k mantine-Badge-inner">질문자</span>
            </div>
          </div>
          <p class="mantine-Text-root mantine-1q4x896">2024. 09. 04. 12:06</p>
        </div>
      </div>
    </div>
    <div class="qna-title">
      RestContorller와 view의 활용에 대해 질문드립니다?
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
            </g></svg
          >18
        </div>
        <div>
          <svg
            fill="#000000"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            xmlns="http://www.w3.org/2000/svg"
            version="1.1"
            viewBox="-2.5 0 32 32"
          >
            <g stroke-width="0" id="SVGRepo_bgCarrier"></g>
            <g
              stroke-linejoin="round"
              stroke-linecap="round"
              id="SVGRepo_tracerCarrier"
            ></g>
            <g id="SVGRepo_iconCarrier">
              <g id="icomoon-ignore"></g>
              <path
                fill="#000000"
                d="M0 10.284l0.505 0.36c0.089 0.064 0.92 0.621 2.604 0.621 0.27 0 0.55-0.015 0.836-0.044 3.752 4.346 6.411 7.472 7.060 8.299-1.227 2.735-1.42 5.808-0.537 8.686l0.256 0.834 7.63-7.631 8.309 8.309 0.742-0.742-8.309-8.309 7.631-7.631-0.834-0.255c-2.829-0.868-5.986-0.672-8.686 0.537-0.825-0.648-3.942-3.3-8.28-7.044 0.11-0.669 0.23-2.183-0.575-3.441l-0.352-0.549-8.001 8.001zM1.729 10.039l6.032-6.033c0.385 1.122 0.090 2.319 0.086 2.334l-0.080 0.314 0.245 0.214c7.409 6.398 8.631 7.39 8.992 7.546l-0.002 0.006 0.195 0.058 0.185-0.087c2.257-1.079 4.903-1.378 7.343-0.836l-13.482 13.481c-0.55-2.47-0.262-5.045 0.837-7.342l0.104-0.218-0.098-0.221-0.031 0.013c-0.322-0.632-1.831-2.38-7.498-8.944l-0.185-0.215-0.282 0.038c-0.338 0.045-0.668 0.069-0.981 0.069-0.595 0-1.053-0.083-1.38-0.176z"
              ></path>
            </g></svg
          >7
        </div>
      </div>
      <div class="qna-category-highlow">
        <span>Spring</span>
        /
        <span>MVC pattern</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "QnaListCardComponent",
  data() {
    return {};
  },
  props: [],
  created() {},
  methods: {},
};
</script>

<style>
/* QnA card */
.card {
  position: relative;
  background-color: #ffffff;
  padding: 1em;
  z-index: 5;
  box-shadow: 4px 4px 20px rgba(0, 0, 0, 0.3);
  border-radius: 10px;
  width: 400px;
  height: 220px;
  display: flex;
  flex-direction: column;
  transition: 200ms ease-in-out;
  justify-content: space-between;
}

.qna-category-highlow {
  color: #84ceee;
  font-size: 1em;
  font-weight: 800;
}

.qna-foot-division {
  display: flex;
}

.body {
  display: flex;
  flex-direction: column;
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
  margin-right: 1rem;
  height: 20px;
  display: flex;
  align-items: center;
  cursor: pointer;
}

.footer svg {
  margin-right: 5px;
  height: 100%;
  stroke: #9fa4aa;
}

.viewer span {
  height: 20px;
  width: 20px;
  background-color: rgb(28, 117, 219);
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
  height: 1.25rem;
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
body {
  margin: 0;
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
body {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
    "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
    "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
    sans-serif;
  background-color: #fff;
  color: #000;
  line-height: 1.5;
  font-size: 1rem;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
@font-face {
  font-family: Pretendard;
  font-weight: 700;
  src: url(https://cdn.inflearn.com/font/Pretendard-Bold.woff2) format("woff2"),
    url(https://cdn.inflearn.com/font/Pretendard-Bold.woff) format("woff");
}
@font-face {
  font-family: Pretendard;
  font-weight: 600;
  src: url(https://cdn.inflearn.com/font/Pretendard-SemiBold.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/Pretendard-SemiBold.woff) format("woff");
}
@font-face {
  font-family: Pretendard;
  font-weight: 500;
  src: url(https://cdn.inflearn.com/font/Pretendard-Medium.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/Pretendard-Medium.woff) format("woff");
}
@font-face {
  font-family: Pretendard;
  font-weight: 400;
  src: url(https://cdn.inflearn.com/font/Pretendard-Regular.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/Pretendard-Regular.woff) format("woff");
}
@font-face {
  font-family: Pretendard;
  font-weight: 300;
  src: url(https://cdn.inflearn.com/font/Pretendard-Light.woff2) format("woff2"),
    url(https://cdn.inflearn.com/font/Pretendard-Light.woff) format("woff");
}
@font-face {
  font-family: KaTeX_AMS;
  font-weight: 400;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_AMS-Regular.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_AMS-Regular.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_AMS-Regular.ttf) format("truetype");
}
@font-face {
  font-family: KaTeX_Caligraphic;
  font-weight: 700;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_Caligraphic-Bold.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Caligraphic-Bold.woff)
      format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Caligraphic-Bold.ttf)
      format("truetype");
}
@font-face {
  font-family: KaTeX_Caligraphic;
  font-weight: 400;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_Caligraphic-Regular.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Caligraphic-Regular.woff)
      format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Caligraphic-Regular.ttf)
      format("truetype");
}
@font-face {
  font-family: KaTeX_Fraktur;
  font-weight: 700;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_Fraktur-Bold.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Fraktur-Bold.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Fraktur-Bold.ttf) format("truetype");
}
@font-face {
  font-family: KaTeX_Fraktur;
  font-weight: 400;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_Fraktur-Regular.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Fraktur-Regular.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Fraktur-Regular.ttf)
      format("truetype");
}
@font-face {
  font-family: KaTeX_Main;
  font-weight: 700;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_Main-Bold.woff2) format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Main-Bold.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Main-Bold.ttf) format("truetype");
}
@font-face {
  font-family: KaTeX_Main;
  font-weight: 700;
  font-style: italic;
  src: url(https://cdn.inflearn.com/font/KaTeX_Main-BoldItalic.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Main-BoldItalic.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Main-BoldItalic.ttf)
      format("truetype");
}
@font-face {
  font-family: KaTeX_Main;
  font-weight: 400;
  font-style: italic;
  src: url(https://cdn.inflearn.com/font/KaTeX_Main-Italic.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Main-Italic.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Main-Italic.ttf) format("truetype");
}
@font-face {
  font-family: KaTeX_Main;
  font-weight: 400;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_Main-Regular.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Main-Regular.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Main-Regular.ttf) format("truetype");
}
@font-face {
  font-family: KaTeX_Math;
  font-weight: 700;
  font-style: italic;
  src: url(https://cdn.inflearn.com/font/KaTeX_Math-BoldItalic.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Math-BoldItalic.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Math-BoldItalic.ttf)
      format("truetype");
}
@font-face {
  font-family: KaTeX_Math;
  font-weight: 400;
  font-style: italic;
  src: url(https://cdn.inflearn.com/font/KaTeX_Math-Italic.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Math-Italic.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Math-Italic.ttf) format("truetype");
}
@font-face {
  font-family: KaTeX_SansSerif;
  font-weight: 700;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_SansSerif-Bold.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_SansSerif-Bold.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_SansSerif-Bold.ttf)
      format("truetype");
}
@font-face {
  font-family: KaTeX_SansSerif;
  font-weight: 400;
  font-style: italic;
  src: url(https://cdn.inflearn.com/font/KaTeX_SansSerif-Italic.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_SansSerif-Italic.woff)
      format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_SansSerif-Italic.ttf)
      format("truetype");
}
@font-face {
  font-family: KaTeX_SansSerif;
  font-weight: 400;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_SansSerif-Regular.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_SansSerif-Regular.woff)
      format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_SansSerif-Regular.ttf)
      format("truetype");
}
@font-face {
  font-family: KaTeX_Script;
  font-weight: 400;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_Script-Regular.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Script-Regular.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Script-Regular.ttf)
      format("truetype");
}
@font-face {
  font-family: KaTeX_Size1;
  font-weight: 400;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_Size1-Regular.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Size1-Regular.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Size1-Regular.ttf)
      format("truetype");
}
@font-face {
  font-family: KaTeX_Size2;
  font-weight: 400;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_Size2-Regular.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Size2-Regular.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Size2-Regular.ttf)
      format("truetype");
}
@font-face {
  font-family: KaTeX_Size3;
  font-weight: 400;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_Size3-Regular.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Size3-Regular.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Size3-Regular.ttf)
      format("truetype");
}
@font-face {
  font-family: KaTeX_Size4;
  font-weight: 400;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_Size4-Regular.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Size4-Regular.woff) format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Size4-Regular.ttf)
      format("truetype");
}
@font-face {
  font-family: KaTeX_Typewriter;
  font-weight: 400;
  font-style: normal;
  src: url(https://cdn.inflearn.com/font/KaTeX_Typewriter-Regular.woff2)
      format("woff2"),
    url(https://cdn.inflearn.com/font/KaTeX_Typewriter-Regular.woff)
      format("woff"),
    url(https://cdn.inflearn.com/font/KaTeX_Typewriter-Regular.ttf)
      format("truetype");
}
body,
html {
  margin: 0;
  padding: 0;
  border: 0;
  font-size: 100%;
  vertical-align: baseline;
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
  margin-bottom: 30px;
  text-align: center;
  font-size: 26px;
  font-weight: 900;
}

#good-image {
  width: 5px;
  height: 5px;
}
</style>
