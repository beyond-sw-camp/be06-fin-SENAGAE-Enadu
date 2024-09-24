<template>
  <div v-if="isLoading"></div>
  <div id="root" v-else>
    <div class="sc-dPiLbb sc-bBHHxi kTIDXm">
      <div class="sc-TBWPX dXONqK sc-jQrDum fiOuRZ">
        <div class="head-wrapper">
          <h1>{{ errorarchiveStore.errorArchiveDetail.title }}</h1>
          <div class="sc-fvxzrP jGdQwA" style="display: flex; justify-content: space-between;">
            <button v-if="isAuthor" @click="editErrorArchive">수정하기</button>
            <div class="information">
              <img class="profile" :src="errorarchiveStore.errorArchiveDetail.profileImg">
              <span class="username"><NicknameComponent :nickname="errorarchiveStore.errorArchiveDetail.nickname"/>
                </span>
              <span class="separator">·</span>
              <span style="font-size:16px">{{ lastModifiedDate }}</span>
              <span style="font-size:16px" class="grade">{{ errorarchiveStore.errorArchiveDetail.grade }}</span>
            </div>
            <div class="sc-fbyfCU eYeYLy" v-show="userStore.isLoggedIn">
              <div class="bookmark-checkbox">
                <input type="checkbox" id="bookmark-toggle" :checked="checkScrap" @click="clickScrap"
                       class="bookmark-checkbox__input">
                <label for="bookmark-toggle" class="bookmark-checkbox__label">
                  <svg class="bookmark-checkbox__icon" viewBox="0 0 24 24">
                    <path class="bookmark-checkbox__icon-back"
                          d="M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z"></path>
                    <path class="bookmark-checkbox__icon-check" d="M8 11l3 3 5-5"></path>
                  </svg>
                </label>
              </div>
              <div class="icons-box">
                <div class="icons">
                  <label class="btn-label" for="like-checkbox">
                    <span class="like-text-content">{{ likeCnt }}</span>
                    <input class="input-box" id="like-checkbox" type="radio" value=true @click="clickLike(true)"
                           name="like" v-model="selectedLike"/>
                    <svg
                        class="svgs"
                        id="icon-like-solid"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 512 512"
                    >
                      <path
                          d="M313.4 32.9c26 5.2 42.9 30.5 37.7 56.5l-2.3 11.4c-5.3 26.7-15.1 52.1-28.8 75.2H464c26.5 0 48 21.5 48 48c0 18.5-10.5 34.6-25.9 42.6C497 275.4 504 288.9 504 304c0 23.4-16.8 42.9-38.9 47.1c4.4 7.3 6.9 15.8 6.9 24.9c0 21.3-13.9 39.4-33.1 45.6c.7 3.3 1.1 6.8 1.1 10.4c0 26.5-21.5 48-48 48H294.5c-19 0-37.5-5.6-53.3-16.1l-38.5-25.7C176 420.4 160 390.4 160 358.3V320 272 247.1c0-29.2 13.3-56.7 36-75l7.4-5.9c26.5-21.2 44.6-51 51.2-84.2l2.3-11.4c5.2-26 30.5-42.9 56.5-37.7zM32 192H96c17.7 0 32 14.3 32 32V448c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32V224c0-17.7 14.3-32 32-32z"
                      ></path>
                    </svg>
                    <svg
                        class="svgs"
                        id="icon-like-regular"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 512 512"
                    >
                      <path
                          d="M323.8 34.8c-38.2-10.9-78.1 11.2-89 49.4l-5.7 20c-3.7 13-10.4 25-19.5 35l-51.3 56.4c-8.9 9.8-8.2 25 1.6 33.9s25 8.2 33.9-1.6l51.3-56.4c14.1-15.5 24.4-34 30.1-54.1l5.7-20c3.6-12.7 16.9-20.1 29.7-16.5s20.1 16.9 16.5 29.7l-5.7 20c-5.7 19.9-14.7 38.7-26.6 55.5c-5.2 7.3-5.8 16.9-1.7 24.9s12.3 13 21.3 13L448 224c8.8 0 16 7.2 16 16c0 6.8-4.3 12.7-10.4 15c-7.4 2.8-13 9-14.9 16.7s.1 15.8 5.3 21.7c2.5 2.8 4 6.5 4 10.6c0 7.8-5.6 14.3-13 15.7c-8.2 1.6-15.1 7.3-18 15.1s-1.6 16.7 3.6 23.3c2.1 2.7 3.4 6.1 3.4 9.9c0 6.7-4.2 12.6-10.2 14.9c-11.5 4.5-17.7 16.9-14.4 28.8c.4 1.3 .6 2.8 .6 4.3c0 8.8-7.2 16-16 16H286.5c-12.6 0-25-3.7-35.5-10.7l-61.7-41.1c-11-7.4-25.9-4.4-33.3 6.7s-4.4 25.9 6.7 33.3l61.7 41.1c18.4 12.3 40 18.8 62.1 18.8H384c34.7 0 62.9-27.6 64-62c14.6-11.7 24-29.7 24-50c0-4.5-.5-8.8-1.3-13c15.4-11.7 25.3-30.2 25.3-51c0-6.5-1-12.8-2.8-18.7C504.8 273.7 512 257.7 512 240c0-35.3-28.6-64-64-64l-92.3 0c4.7-10.4 8.7-21.2 11.8-32.2l5.7-20c10.9-38.2-11.2-78.1-49.4-89zM32 192c-17.7 0-32 14.3-32 32V448c0 17.7 14.3 32 32 32H96c17.7 0 32-14.3 32-32V224c0-17.7-14.3-32-32-32H32z"
                      ></path>
                    </svg>
                    <div class="fireworks">
                      <div class="checked-like-fx"></div>
                    </div>
                  </label>
                </div>
                <div class="icons">
                  <label class="btn-label" for="dislike-checkbox">
                    <input
                        class="input-box"
                        id="dislike-checkbox"
                        type="radio" name="like" v-model="selectedLike" value=false @click="clickLike(false)"
                    />
                    <svg
                        class="svgs"
                        id="icon-dislike-solid"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 512 512"
                    >
                      <path
                          d="M313.4 32.9c26 5.2 42.9 30.5 37.7 56.5l-2.3 11.4c-5.3 26.7-15.1 52.1-28.8 75.2H464c26.5 0 48 21.5 48 48c0 18.5-10.5 34.6-25.9 42.6C497 275.4 504 288.9 504 304c0 23.4-16.8 42.9-38.9 47.1c4.4 7.3 6.9 15.8 6.9 24.9c0 21.3-13.9 39.4-33.1 45.6c.7 3.3 1.1 6.8 1.1 10.4c0 26.5-21.5 48-48 48H294.5c-19 0-37.5-5.6-53.3-16.1l-38.5-25.7C176 420.4 160 390.4 160 358.3V320 272 247.1c0-29.2 13.3-56.7 36-75l7.4-5.9c26.5-21.2 44.6-51 51.2-84.2l2.3-11.4c5.2-26 30.5-42.9 56.5-37.7zM32 192H96c17.7 0 32 14.3 32 32V448c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32V224c0-17.7 14.3-32 32-32z"
                      ></path>
                    </svg>
                    <svg
                        class="svgs"
                        id="icon-dislike-regular"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 512 512"
                    >
                      <path
                          d="M323.8 34.8c-38.2-10.9-78.1 11.2-89 49.4l-5.7 20c-3.7 13-10.4 25-19.5 35l-51.3 56.4c-8.9 9.8-8.2 25 1.6 33.9s25 8.2 33.9-1.6l51.3-56.4c14.1-15.5 24.4-34 30.1-54.1l5.7-20c3.6-12.7 16.9-20.1 29.7-16.5s20.1 16.9 16.5 29.7l-5.7 20c-5.7 19.9-14.7 38.7-26.6 55.5c-5.2 7.3-5.8 16.9-1.7 24.9s12.3 13 21.3 13L448 224c8.8 0 16 7.2 16 16c0 6.8-4.3 12.7-10.4 15c-7.4 2.8-13 9-14.9 16.7s.1 15.8 5.3 21.7c2.5 2.8 4 6.5 4 10.6c0 7.8-5.6 14.3-13 15.7c-8.2 1.6-15.1 7.3-18 15.1s-1.6 16.7 3.6 23.3c2.1 2.7 3.4 6.1 3.4 9.9c0 6.7-4.2 12.6-10.2 14.9c-11.5 4.5-17.7 16.9-14.4 28.8c.4 1.3 .6 2.8 .6 4.3c0 8.8-7.2 16-16 16H286.5c-12.6 0-25-3.7-35.5-10.7l-61.7-41.1c-11-7.4-25.9-4.4-33.3 6.7s-4.4 25.9 6.7 33.3l61.7 41.1c18.4 12.3 40 18.8 62.1 18.8H384c34.7 0 62.9-27.6 64-62c14.6-11.7 24-29.7 24-50c0-4.5-.5-8.8-1.3-13c15.4-11.7 25.3-30.2 25.3-51c0-6.5-1-12.8-2.8-18.7C504.8 273.7 512 257.7 512 240c0-35.3-28.6-64-64-64l-92.3 0c4.7-10.4 8.7-21.2 11.8-32.2l5.7-20c10.9-38.2-11.2-78.1-49.4-89zM32 192c-17.7 0-32 14.3-32 32V448c0 17.7 14.3 32 32 32H96c17.7 0 32-14.3 32-32V224c0-17.7-14.3-32-32-32H32z"
                      ></path>
                    </svg>
                    <span class="dislike-text-content">{{ hateCnt }}</span>
                  </label>
                </div>
              </div>

            </div>
          </div>
        </div>

        <div class="sc-cZMNgc bpMcZw">
          <a class="sc-dtMgUX gISUXI" href="/tags/LomBok">{{ errorarchiveStore.errorArchiveDetail.superCategory }}</a>
          <a class="sc-dtMgUX gISUXI" href="/tags/LomBok">{{ errorarchiveStore.errorArchiveDetail.subCategory }}</a>

        </div>

        <div class="sc-jlRLRk iGRQXB">
          <div class="sc-dUbtfd kOYWDF">
            <div class="sc-jHkVzv dyVEVs sc-htJRVC jfYEFP">
              <div v-for="(anchor, idx) in titles" @click="handleAnchorClick(anchor)" :key="idx" class="sc-cbTzjv kUqjof" :style="{ marginLeft: `${anchor.indent * 12}px` }">
               <a style="cursor:pointer">{{anchor.title}}</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="sc-dFtzxp bfzjcP" style="margin-top: 1px;">
      <div class="sc-bXTejn FTZwa">
        <div class="sc-eGRUor gdnhbG atom-one">

          <!-- 마크다운 내용 -->
          <v-md-preview ref="preview" :text="errorarchiveStore.errorArchiveDetail.content"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import VMdPreview from "@kangc/v-md-editor/lib/preview";
import hljs from "highlight.js";
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/style/preview.css';
import {mapStores} from "pinia";
import {useErrorArchiveStore} from "@/store/useErrorArchiveStore";
import NicknameComponent from "@/components/Common/NicknameComponent.vue";
import {useUserStore} from "@/store/useUserStore";


VMdPreview.use(githubTheme, {
  Hljs: hljs,
});

export default {
  name: "ErrorArchiveDetailComponent",
  components: {NicknameComponent},
  data() {
    return {
      id: 0,
      isLoading: true,
      lastModifiedDate: "",
      selectedLike: null,
      likeCnt: 0,
      hateCnt: 0,
      index: [],
      titles: [],
      checkScrap: false,
    }
  },
  computed: {
    ...mapStores(useErrorArchiveStore),
    ...mapStores(useUserStore),
    // 로그인한 사용자 ID와 작성자 ID를 비교하여 isAuthor 값을 설정
    isAuthor() {
      return this.userStore.userId === this.errorarchiveStore.errorArchiveDetail.authorId; // userId와 authorId 비교
    }
  },
  watch: {
    selectedLike(newVal, oldVal) {
      if(this.isLoading){
        return;
      }
      if (newVal === true) {
        this.likeCnt++;
        if (oldVal === false) {
          this.hateCnt--;
        }
      } else if (newVal === false){
        this.hateCnt++;
        if (oldVal === true) {
          this.likeCnt--;
        }
      } else {
        if (oldVal === true){
          this.likeCnt --;
        } else if(oldVal===false) {
          this.hateCnt--;
        }
      }
    }
  },
  methods: {
    async getErrorArchiveDetail() {
      await this.errorarchiveStore.getErrorArchiveDetail(this.id);
      this.checkScrap = this.errorarchiveStore.errorArchiveDetail.checkScrap;
      this.setModifiedTime();
      this.checkLike();
      this.setLikeAndHateCnt();
    },
    setModifiedTime() {
      let date = this.errorarchiveStore.errorArchiveDetail.modifiedAt.split("T")[0].split("-")
      this.lastModifiedDate = date[0] + "년 " + date[1] + "월 " + date[2] + "일";
    },
    checkLike() {
      if (this.errorarchiveStore.errorArchiveDetail.checkLike) {
        this.selectedLike = true;
      } else if (this.errorarchiveStore.errorArchiveDetail.checkHate) {
        this.selectedLike = false;
      }
    },
    setLikeAndHateCnt() {
      this.hateCnt = this.errorarchiveStore.errorArchiveDetail.hateCnt
      this.likeCnt = this.errorarchiveStore.errorArchiveDetail.likeCnt
    },
    async clickLike(value) {
      this.selectedLike = await this.errorarchiveStore.likeErrorArchive(this.id, value)
    },
    async clickScrap(){
      await this.errorarchiveStore.scrapErrorArchive(this.id);
    },
    handleAnchorClick(anchor) {
      const { preview } = this.$refs;
      const { lineIndex } = anchor;

      const heading = preview.$el.querySelector(`[data-v-md-line="${lineIndex}"]`);

      if (heading) {
        // Note: If you are using the preview mode of the editing component, the method name here is changed to previewScrollToTarget
        preview.scrollToTarget({
          target: heading,
          scrollContainer: window,
          top: 60,
        });
      }
    },
    editErrorArchive() {
      this.$router.push({ name: 'EditErrorArchive', query: { id: this.id }});
    }
  },
  async mounted() {
    this.id = this.$route.query.id;
    await this.getErrorArchiveDetail();
    this.isLoading = false;

    this.$nextTick(() => {
      console.log(this.$el);  // DOM 업데이트가 완료된 후 접근
      const anchors = this.$refs.preview.$el.querySelectorAll('h1,h2,h3,h4,h5,h6');
      const titles = Array.from(anchors).filter((title) => !!title.innerText.trim());

      if (!titles.length) {
        this.titles = [];
        return;
      }

      const hTags = Array.from(new Set(titles.map((title) => title.tagName))).sort();

      this.titles = titles.map((el) => ({
        title: el.innerText,
        lineIndex: el.getAttribute('data-v-md-line'),
        indent: hTags.indexOf(el.tagName),
      }));
    });

  }
};
</script>


<style scoped>
.profile {
  border-radius: 50%;
  width: 25px;
  height: 25px;
  display: inline-block;
  margin-right: 5px;
}
v-md-preview {
  font-size: 1.125rem;
  line-height: 1.7;
  word-break: keep-all;
  overflow-wrap: break-word;
  color: var(--text1);
}
v-md-preview h1,
v-md-preview h2,
v-md-preview h3 {
  margin-bottom: 1rem;
  color: var(--text1);
}
v-md-preview p {
  margin-bottom: 1.5rem;
}
.dXONqK {
  width: 768px;
  margin-left: auto;
  margin-right: auto;
}

.head-wrapper {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 20px;
}

.sc-egiyK {
  color: #;
  text-decoration: none;
  cursor: pointer;
}

.sc-egiyK:hover {
  text-decoration: underline;
}

.thumbnail-image {
  width: 100%;
  max-width: 600px;
  margin-top: 20px;
}

.scrap-btn {
  margin-top: 20px;
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
}
.scrap-btn:hover {
  background-color: #0056b3;
}

body[data-theme="light"] {
  --bg-page1: #F8F9FA;
  --bg-page2: #FFFFFF;
  --bg-element1: #FFFFFF;
  --bg-element2: #F8F9FA;
  --bg-element3: #E9ECEF;
  --bg-element4: #DEE2E6;
  --bg-element5: #212529;
  --bg-element6: #343A40;
  --bg-element7: #FFFFFF;
  --bg-element8: #FBFDFC;
  --bg-invert: #1E1E1E;
  --bg-inline-code: #E9ECEF;
  --bg-tag: #F8F9FA;
  --text1: #212529;
  --text2: #495057;
  --text3: #868E96;
  --text4: #CED4DA;
  --border1: #343A40;
  --border2: #ADB5BD;
  --border3: #DEE2E6;
  --border4: #F1F3F5;
  --primary1: #12B886;
  --primary2: #20C997;
  --destructive1: #FF6B6B;
  --destructive2: #FF8787;
  --button-text: #FFFFFF;
  --slight-layer: rgba(0, 0, 0, 0.05);
  --opaque-layer: rgba(249, 249, 249, 0.85);
  --editor-footer: #FFFFFF;
  --prism-bg: #fbfcfd;
  --prism-default-text: #24292e;
  --prism-selection-bg: rgba(0, 0, 0, 0.15);
  --prism-code-block-bg: #fbfcfd;
  --prism-code-1: #969896;
  --prism-code-2: #24292e;
  --prism-code-3: #a626a4;
  --prism-code-4: #63a35c;
  --prism-code-5: #0184bc;
  --prism-code-6: #50a14f;
  --prism-code-7: #a626a4;
  --prism-code-8: #005cc5;
  --prism-code-9: #a626a4;
  --prism-line-number: #585c63;
}
.__jazzbar {
  z-index: 1000;
  position: fixed;
  top: 0;
  width: 100%;
  height: 4px;
  background: #63e6be;
  opacity: 1;
  -webkit-transition: all .4s ease-in;
  transition: all .4s ease-in;
}
.kTIDXm {
  padding-bottom: 1rem;
}

.jEdNvQ {
  height: 4rem;
}
.edit-button {
  background-color: #39415e; /* 버튼 배경색 */
  color: white; /* 글자색 */
  border: none; /* 테두리 없애기 */
  padding: 10px 15px; /* 패딩 */
  text-align: center; /* 중앙 정렬 */
  text-decoration: none; /* 밑줄 없애기 */
  display: inline-block; /* 인라인 블록으로 설정 */
  font-size: 16px; /* 글자 크기 */
  margin: 4px 2px; /* 여백 */
  cursor: pointer; /* 마우스 포인터 변경 */
  border-radius: 5px; /* 둥근 모서리 */
  transition: background-color 0.3s; /* 배경색 변화 애니메이션 */
}

.edit-button:hover {
  background-color: #45a049; /* 마우스 오버 시 색상 변화 */
}
.hrgwyc {
  height: 100%;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: justify;
  justify-content: space-between;
}
@media (max-width: 1440px) {
  .cQvXTx {
    width: 1024px;
  }
}
@media (max-width: 1919px) {
  .cQvXTx {
    width: 1376px;
  }
}

.ddFdew {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  font-weight: bold;
  color: var(--text1);
  font-size: 1.3125rem;
  text-decoration: none;
  font-family: "Fira Mono", monospace;
}

.ddFdew a {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  color: inherit;
  text-decoration: none;
}

.kdLiUF svg {
  color: inherit;
  margin-right: 1rem;
  width: 1.75rem;
  height: 1.75rem;
  display: block;
}

.ddFdew a {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  color: inherit;
  text-decoration: none;
}

.ddFdew .user-logo {
  display: block;
  max-width: calc(-250px + 100vw);
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}

.ddFdew .user-logo {
  display: block;
  max-width: calc(-250px + 100vw);
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}

.gHrJRn {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  position: relative;
}

.pAGEY {
  position: relative;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  background: transparent;
  border: none;
  width: 2.5rem;
  height: 2.5rem;
  outline: none;
  border-radius: 50%;
  color: var(--text1);
  cursor: pointer;
  margin-right: 4px;
}

.pAGEY svg {
  width: 24px;
  height: 24px;
}

.glTvbH {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  background: transparent;
  border: none;
  width: 2.5rem;
  height: 2.5rem;
  outline: none;
  border-radius: 50%;
  color: var(--text1);
  cursor: pointer;
  margin-right: 0.5rem;
}

.bnYoDz {
  height: 2rem;
  padding-left: 1rem;
  padding-right: 1rem;
  font-size: 1rem;
  border-radius: 1rem;
  outline: none;
  font-weight: bold;
  word-break: keep-all;
  background: var(--bg-element2);
  border: 1px solid var(--bg-element5);
  color: var(--bg-element5);
  transition: 0.125s ease-in;
  cursor: pointer;
}

.gegLws {
  cursor: pointer;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
}

.gegLws img {
  display: block;
  height: 2.5rem;
  width: 2.5rem;
  box-shadow: rgba(0, 0, 0, 0.086) 0px 0px 8px;
  border-radius: 50%;
  object-fit: cover;
  transition: 0.125s ease-in;
}

.gegLws svg {
  font-size: 1.5rem;
  margin-left: 0.25rem;
  color: var(--text3);
  transition: 0.125s ease-in;
  margin-right: -0.4375rem;
}

.hrgwyc {
  height: 100%;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: justify;
  justify-content: space-between;
}

.jEdNvQ {
  height: 4rem;
}

@media (max-width: 1440px) {
  .cQvXTx {
    width: 1024px;
  }
}

.kdLiUF svg {
  color: inherit;
  margin-right: 1rem;
  width: 1.75rem;
  height: 1.75rem;
  display: block;
}

* {
  box-sizing: inherit;
}

.ddFdew .user-logo {
  display: block;
  max-width: calc(-250px + 100vw);
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}

.gHrJRn {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  position: relative;
}

.pAGEY {
  position: relative;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  background: transparent;
  border: none;
  width: 2.5rem;
  height: 2.5rem;
  outline: none;
  border-radius: 50%;
  color: var(--text1);
  cursor: pointer;
  margin-right: 4px;
}

.pAGEY svg {
  width: 24px;
  height: 24px;
}

.bnYoDz {
  height: 2rem;
  padding-left: 1rem;
  padding-right: 1rem;
  font-size: 1rem;
  border-radius: 1rem;
  outline: none;
  font-weight: bold;
  word-break: keep-all;
  background: var(--bg-element2);
  border: 1px solid var(--bg-element5);
  color: var(--bg-element5);
  transition: 0.125s ease-in;
  cursor: pointer;
}

.gegLws {
  cursor: pointer;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
}

.fiOuRZ h1 {
  font-size: 3rem;
  line-height: 1.5;
  letter-spacing: -0.004em;
  margin-top: 0px;
  font-weight: 800;
  color: var(--text1);
  margin-bottom: 2rem;
  word-break: keep-all;
  overflow-wrap: break-word;
  transition: color 0.125s ease-in;
}

.jGdQwA {
  font-size: 1rem;
  color: var(--text2);
  display: flex;
  -webkit-box-pack: justify;
  justify-content: space-between;
  -webkit-box-align: center;
  align-items: center;
  width: 100%;
}


.jGdQwA .information .username {
  color: var(--text1);
  font-weight: bold;
  font-size: 16px;
}

.jGdQwA .information .separator {
  margin-left: 0.5rem;
  margin-right: 0.5rem;
}

.eYeYLy {
  display: flex;
}

.iIZjji .follow-button {
  color: #12B886;
  border: 1px solid #12B886;
}

.iIZjji .button {
  display: flex;
  box-shadow: none;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  background-color: var(--bg-element1);
  cursor: pointer;
  border-radius: 16px;
  font-weight: 700;
  width: 100%;
  height: 100%;
  white-space: nowrap;
  outline: none;
  font-size: 16px;
}

input,
button,
textarea {
  font-family: inherit;
}

.gyXrCE {
  display: none;
  background: var(--bg-element1);
  border: 1px solid var(--border2);
  padding-left: 0.75rem;
  padding-right: 0.75rem;
  -webkit-box-align: center;
  align-items: center;
  height: 1.5rem;
  border-radius: 0.75rem;
  outline: none;
}

.gyXrCE svg {
  width: 0.75rem;
  height: 0.75rem;
  margin-right: 0.75rem;
  color: var(--text3);
}

.gyXrCE span {
  font-size: 0.75rem;
  font-weight: bold;
  color: var(--text3);
}

.bpMcZw {
  margin-top: 0.875rem;
  margin-bottom: -0.875rem;
  min-height: 0.875rem;
}

.grade {
  background: #F8F9FA;
  padding-left: 1rem;
  padding-right: 1rem;
  height: 2rem;
  border-radius: 1rem;
  display: inline-flex;
  -webkit-box-align: center;
  align-items: center;
  color: #FF6B6B;
  text-decoration: none;
  font-weight: 700;
  font-size: 1rem;
}

.gISUXI {
  margin-bottom: 0.875rem;
  background: #F8F9FA;
  padding-left: 1rem;
  padding-right: 1rem;
  height: 2rem;
  border-radius: 1rem;
  display: inline-flex;
  -webkit-box-align: center;
  align-items: center;
  margin-right: 0.875rem;
  color: #12B886;
  text-decoration: none;
  font-weight: 700;
  font-size: 1rem;
}

.kzTxBt {
  position: relative;
  margin-top: 2rem;
}

.brUzDR {
  position: absolute;
  left: -7rem;
}

.knjEeh {
  width: 4rem;
  background: var(--bg-element2);
  border: 1px solid var(--border4);
  border-radius: 2rem;
  padding: 0.5rem;
  display: flex;
  flex-direction: column;
  -webkit-box-align: center;
  align-items: center;
}

.gCPYbd {
  height: 3rem;
  width: 3rem;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  background: var(--bg-element1);
  border: 1px solid var(--border3);
  border-radius: 1.5rem;
  color: var(--text3);
  cursor: pointer;
  z-index: 5;
}

.gCPYbd svg {
  width: 24px;
  height: 24px;
}

.djwcYU {
  margin-top: 0.5rem;
  color: var(--text2);
  line-height: 1;
  font-size: 0.75rem;
  margin-bottom: 1rem;
  font-weight: bold;
}

.dZrsFD {
  position: relative;
  width: 100%;
  z-index: 5;
}

.dZrsFD .positioner {
  position: absolute;
}

.bxhFtZ {
  top: 0px;
  left: 0px;
  position: absolute;
  width: 48px;
  height: 48px;
}

.gCPYbd {
  height: 3rem;
  width: 3rem;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  background: var(--bg-element1);
  border: 1px solid var(--border3);
  border-radius: 1.5rem;
  color: var(--text3);
  cursor: pointer;
  z-index: 5;
}

@media (max-width: 1365px) {
  .iGRQXB {
    display: none;
  }
}

.iGRQXB {
  position: relative;
  margin-top: 2rem;
}

.kOYWDF {
  position: absolute;
  left: 100%;
}

/* @media (max-width: 1440px) {
    .jfYEFP {
        margin-left: 3rem;
    }
} */
.jfYEFP {
  position: fixed;
  width: 240px;
  margin-left: 5rem;
  border-left: 2px solid #F1F3F5;
  padding: 0.25rem 0.75rem;
  color: #868E96;
  line-height: 1.5;
  font-size: 1.2rem;
  max-height: calc(-128px + 100vh);
  overflow: hidden auto;
}

.jfYEFP::-webkit-scrollbar {
  border-radius: 1px;
  width: 2px;
  background: var(--bg-element2);
}

.jfYEFP::-webkit-scrollbar-thumb {
  z-index: 100;
  background: rgb(52, 58, 64);
}
.m0 {
  margin-left: 0px;
}
.m12 {
  margin-left: 12px;
}
.m24 {
  margin-left: 24px;
}
.fb_hidden {
  position: absolute;
  top: -10000px;
  z-index: 10001
}

.fb_reposition {
  overflow: hidden;
  position: relative
}

.fb_invisible {
  display: none
}

.fb_reset {
  background: none;
  border: 0;
  border-spacing: 0;
  color: #000;
  cursor: auto;
  direction: ltr;
  font-family: 'lucida grande', tahoma, verdana, arial, sans-serif;
  font-size: 11px;
  font-style: normal;
  font-variant: normal;
  font-weight: normal;
  letter-spacing: normal;
  line-height: 1;
  margin: 0;
  overflow: visible;
  padding: 0;
  text-align: left;
  text-decoration: none;
  text-indent: 0;
  text-shadow: none;
  text-transform: none;
  visibility: visible;
  white-space: normal;
  word-spacing: normal
}

.fb_reset > div {
  overflow: hidden
}

@keyframes fb_transform {
  from {
    opacity: 0;
    transform: scale(.95)
  }

  to {
    opacity: 1;
    transform: scale(1)
  }
}

.fb_animate {
  animation: fb_transform .3s forwards
}

.fb_dialog {
  background: rgba(82, 82, 82, .7);
  position: absolute;
  top: -10000px;
  z-index: 10001
}

.fb_dialog_advanced {
  border-radius: 8px;
  padding: 10px
}

.fb_dialog_content {
  background: #fff;
  color: #373737
}

.fb_dialog_close_icon {
  background: url(https://connect.facebook.net/rsrc.php/v3/yq/r/IE9JII6Z1Ys.png) no-repeat scroll 0 0 transparent;
  cursor: pointer;
  display: block;
  height: 15px;
  position: absolute;
  right: 18px;
  top: 17px;
  width: 15px
}

.fb_dialog_mobile .fb_dialog_close_icon {
  left: 5px;
  right: auto;
  top: 5px
}

.fb_dialog_padding {
  background-color: transparent;
  position: absolute;
  width: 1px;
  z-index: -1
}

.fb_dialog_close_icon:hover {
  background: url(https://connect.facebook.net/rsrc.php/v3/yq/r/IE9JII6Z1Ys.png) no-repeat scroll 0 -15px transparent
}

.fb_dialog_close_icon:active {
  background: url(https://connect.facebook.net/rsrc.php/v3/yq/r/IE9JII6Z1Ys.png) no-repeat scroll 0 -30px transparent
}

.fb_dialog_iframe {
  line-height: 0
}

.fb_dialog_content .dialog_title {
  background: #6d84b4;
  border: 1px solid #365899;
  color: #fff;
  font-size: 14px;
  font-weight: bold;
  margin: 0
}

.fb_dialog_content .dialog_title > span {
  background: url(https://connect.facebook.net/rsrc.php/v3/yd/r/Cou7n-nqK52.gif) no-repeat 5px 50%;
  float: left;
  padding: 5px 0 7px 26px
}

body.fb_hidden {
  height: 100%;
  left: 0;
  margin: 0;
  overflow: visible;
  position: absolute;
  top: -10000px;
  transform: none;
  width: 100%
}

.fb_dialog.fb_dialog_mobile.loading {
  background: url(https://connect.facebook.net/rsrc.php/v3/ya/r/3rhSv5V8j3o.gif) white no-repeat 50% 50%;
  min-height: 100%;
  min-width: 100%;
  overflow: hidden;
  position: absolute;
  top: 0;
  z-index: 10001
}

.fb_dialog.fb_dialog_mobile.loading.centered {
  background: none;
  height: auto;
  min-height: initial;
  min-width: initial;
  width: auto
}

.fb_dialog.fb_dialog_mobile.loading.centered #fb_dialog_loader_spinner {
  width: 100%
}

.fb_dialog.fb_dialog_mobile.loading.centered .fb_dialog_content {
  background: none
}

.loading.centered #fb_dialog_loader_close {
  clear: both;
  color: #fff;
  display: block;
  font-size: 18px;
  padding-top: 20px
}

#fb-root #fb_dialog_ipad_overlay {
  background: rgba(0, 0, 0, .4);
  bottom: 0;
  left: 0;
  min-height: 100%;
  position: absolute;
  right: 0;
  top: 0;
  width: 100%;
  z-index: 10000
}

#fb-root #fb_dialog_ipad_overlay.hidden {
  display: none
}

.fb_dialog.fb_dialog_mobile.loading iframe {
  visibility: hidden
}

.fb_dialog_mobile .fb_dialog_iframe {
  position: sticky;
  top: 0
}

.fb_dialog_content .dialog_header {
  background: linear-gradient(from(#738aba), to(#2c4987));
  border-bottom: 1px solid;
  border-color: #043b87;
  box-shadow: white 0 1px 1px -1px inset;
  color: #fff;
  font: bold 14px Helvetica, sans-serif;
  text-overflow: ellipsis;
  text-shadow: rgba(0, 30, 84, .296875) 0 -1px 0;
  vertical-align: middle;
  white-space: nowrap
}

.fb_dialog_content .dialog_header table {
  height: 43px;
  width: 100%
}

.fb_dialog_content .dialog_header td.header_left {
  font-size: 12px;
  padding-left: 5px;
  vertical-align: middle;
  width: 60px
}

.fb_dialog_content .dialog_header td.header_right {
  font-size: 12px;
  padding-right: 5px;
  vertical-align: middle;
  width: 60px
}

.fb_dialog_content .touchable_button {
  background: linear-gradient(from(#4267B2), to(#2a4887));
  background-clip: padding-box;
  border: 1px solid #29487d;
  border-radius: 3px;
  display: inline-block;
  line-height: 18px;
  margin-top: 3px;
  max-width: 85px;
  padding: 4px 12px;
  position: relative
}

.fb_dialog_content .dialog_header .touchable_button input {
  background: none;
  border: none;
  color: #fff;
  font: bold 12px Helvetica, sans-serif;
  margin: 2px -12px;
  padding: 2px 6px 3px 6px;
  text-shadow: rgba(0, 30, 84, .296875) 0 -1px 0
}

.fb_dialog_content .dialog_header .header_center {
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  line-height: 18px;
  text-align: center;
  vertical-align: middle
}

.fb_dialog_content .dialog_content {
  background: url(https://connect.facebook.net/rsrc.php/v3/y9/r/jKEcVPZFk-2.gif) no-repeat 50% 50%;
  border: 1px solid #4a4a4a;
  border-bottom: 0;
  border-top: 0;
  height: 150px
}

.fb_dialog_content .dialog_footer {
  background: #f5f6f7;
  border: 1px solid #4a4a4a;
  border-top-color: #ccc;
  height: 40px
}

#fb_dialog_loader_close {
  float: left
}

.fb_dialog.fb_dialog_mobile .fb_dialog_close_icon {
  visibility: hidden
}

#fb_dialog_loader_spinner {
  animation: rotateSpinner 1.2s linear infinite;
  background-color: transparent;
  background-image: url(https://connect.facebook.net/rsrc.php/v3/yD/r/t-wz8gw1xG1.png);
  background-position: 50% 50%;
  background-repeat: no-repeat;
  height: 24px;
  width: 24px
}

@keyframes rotateSpinner {
  0% {
    transform: rotate(0deg)
  }

  100% {
    transform: rotate(360deg)
  }
}

.fb_iframe_widget {
  display: inline-block;
  position: relative
}

.fb_iframe_widget span {
  display: inline-block;
  position: relative;
  text-align: justify
}

.fb_iframe_widget iframe {
  position: absolute
}

.fb_iframe_widget_fluid_desktop,
.fb_iframe_widget_fluid_desktop span,
.fb_iframe_widget_fluid_desktop iframe {
  max-width: 100%
}

.fb_iframe_widget_fluid_desktop iframe {
  min-width: 220px;
  position: relative
}

.fb_iframe_widget_lift {
  z-index: 1
}

.fb_iframe_widget_fluid {
  display: inline
}

.fb_iframe_widget_fluid span {
  width: 100%
}

.fb_mpn_mobile_landing_page_slide_out {
  animation-duration: 200ms;
  animation-name: fb_mpn_landing_page_slide_out;
  transition-timing-function: ease-in
}

.fb_mpn_mobile_landing_page_slide_out_from_left {
  animation-duration: 200ms;
  animation-name: fb_mpn_landing_page_slide_out_from_left;
  transition-timing-function: ease-in
}

.fb_mpn_mobile_landing_page_slide_up {
  animation-duration: 500ms;
  animation-name: fb_mpn_landing_page_slide_up;
  transition-timing-function: ease-in
}

.fb_mpn_mobile_bounce_in {
  animation-duration: 300ms;
  animation-name: fb_mpn_bounce_in;
  transition-timing-function: ease-in
}

.fb_mpn_mobile_bounce_out {
  animation-duration: 300ms;
  animation-name: fb_mpn_bounce_out;
  transition-timing-function: ease-in
}

.fb_mpn_mobile_bounce_out_v2 {
  animation-duration: 300ms;
  animation-name: fb_mpn_fade_out;
  transition-timing-function: ease-in
}

.fb_customer_chat_bounce_in_v2 {
  animation-duration: 300ms;
  animation-name: fb_bounce_in_v2;
  transition-timing-function: ease-in
}

.fb_customer_chat_bounce_in_from_left {
  animation-duration: 300ms;
  animation-name: fb_bounce_in_from_left;
  transition-timing-function: ease-in
}

.fb_customer_chat_bounce_out_v2 {
  animation-duration: 300ms;
  animation-name: fb_bounce_out_v2;
  transition-timing-function: ease-in
}

.fb_customer_chat_bounce_out_from_left {
  animation-duration: 300ms;
  animation-name: fb_bounce_out_from_left;
  transition-timing-function: ease-in
}

.fb_invisible_flow {
  display: inherit;
  height: 0;
  overflow-x: hidden;
  width: 0
}

@keyframes fb_mpn_landing_page_slide_out {
  0% {
    margin: 0 12px;
    width: 100% -24px
  }

  60% {
    border-radius: 18px
  }

  100% {
    border-radius: 50%;
    margin: 0 24px;
    width: 60px
  }
}

@keyframes fb_mpn_landing_page_slide_out_from_left {
  0% {
    left: 12px;
    width: 100% -24px
  }

  60% {
    border-radius: 18px
  }

  100% {
    border-radius: 50%;
    left: 12px;
    width: 60px
  }
}

@keyframes fb_mpn_landing_page_slide_up {
  0% {
    bottom: 0;
    opacity: 0
  }

  100% {
    bottom: 24px;
    opacity: 1
  }
}

@keyframes fb_mpn_bounce_in {
  0% {
    opacity: .5;
    top: 100%
  }

  100% {
    opacity: 1;
    top: 0
  }
}

@keyframes fb_mpn_fade_out {
  0% {
    bottom: 30px;
    opacity: 1
  }

  100% {
    bottom: 0;
    opacity: 0
  }
}

@keyframes fb_mpn_bounce_out {
  0% {
    opacity: 1;
    top: 0
  }

  100% {
    opacity: .5;
    top: 100%
  }
}

@keyframes fb_bounce_in_v2 {
  0% {
    opacity: 0;
    transform: scale(0, 0);
    transform-origin: bottom right
  }

  50% {
    transform: scale(1.03, 1.03);
    transform-origin: bottom right
  }

  100% {
    opacity: 1;
    transform: scale(1, 1);
    transform-origin: bottom right
  }
}

@keyframes fb_bounce_in_from_left {
  0% {
    opacity: 0;
    transform: scale(0, 0);
    transform-origin: bottom left
  }

  50% {
    transform: scale(1.03, 1.03);
    transform-origin: bottom left
  }

  100% {
    opacity: 1;
    transform: scale(1, 1);
    transform-origin: bottom left
  }
}

@keyframes fb_bounce_out_v2 {
  0% {
    opacity: 1;
    transform: scale(1, 1);
    transform-origin: bottom right
  }

  100% {
    opacity: 0;
    transform: scale(0, 0);
    transform-origin: bottom right
  }
}

@keyframes fb_bounce_out_from_left {
  0% {
    opacity: 1;
    transform: scale(1, 1);
    transform-origin: bottom left
  }

  100% {
    opacity: 0;
    transform: scale(0, 0);
    transform-origin: bottom left
  }
}

@keyframes slideInFromBottom {
  0% {
    opacity: .1;
    transform: translateY(100%)
  }

  100% {
    opacity: 1;
    transform: translateY(0)
  }
}

@keyframes slideInFromBottomDelay {
  0% {
    opacity: 0;
    transform: translateY(100%)
  }

  97% {
    opacity: 0;
    transform: translateY(100%)
  }

  100% {
    opacity: 1;
    transform: translateY(0)
  }
}

.bfzjcP {
  width: 768px;
  margin: 5rem auto 0px;
}

.FTZwa {
  font-size: 1.125rem;
  color: var(--text1);
  transition: color 0.125s ease-in;
  line-height: 1.7;
  letter-spacing: -0.004em;
  word-break: keep-all;
  overflow-wrap: break-word;
}

.FTZwa h1,
.FTZwa h2,
.FTZwa h3,
.FTZwa h4 {
  line-height: 1.5;
  margin-bottom: 1rem;
}

.FTZwa h2 {
  font-size: 2rem;
}

.FTZwa ul code,
.FTZwa ol code,
.FTZwa p code {
  background: var(--bg-inline-code);
  padding: 0.2em 0.4em;
  font-size: 85%;
  border-radius: 3px;
}

.FTZwa p + h1,
.FTZwa p + h2,
.FTZwa p + h3,
.FTZwa p + h4 {
  margin-top: 2.5rem;
}

.gdnhbG.atom-one:not(pre) > code[class*="language-"],
.gdnhbG.atom-one pre[class*="language-"] {
  background: var(--prism-code-block-bg);
}

.gdnhbG.atom-one pre[class*="language-"] {
  padding: 1em;
  margin: 0.5em 0px;
  overflow: auto;
}

.gdnhbG.atom-one code[class*="language-"],
.gdnhbG.atom-one pre[class*="language-"] {
  color: var(--prism-default-text);
  background: none;
  text-align: left;
  white-space: pre;
  word-spacing: normal;
  word-break: normal;
  overflow-wrap: normal;
  tab-size: 4;
  hyphens: none;
}

.gdnhbG.atom-one .token.atrule,
.gdnhbG.atom-one .token.keyword {
  color: var(--prism-code-7);
}

.gdnhbG.atom-one .token.punctuation {
  color: var(--prism-code-2);
}

.bfzjcP {
  width: 768px;
  margin: 5rem auto 0px;
}

.dXONqK {
  width: 768px;
  margin-left: auto;
  margin-right: auto;
}

element.style {
  position: fixed;
  top: 112px;
}

body[data-theme="light"] {
  --bg-page1: #F8F9FA;
  --bg-page2: #FFFFFF;
  --bg-element1: #FFFFFF;
  --bg-element2: #F8F9FA;
  --bg-element3: #E9ECEF;
  --bg-element4: #DEE2E6;
  --bg-element5: #212529;
  --bg-element6: #343A40;
  --bg-element7: #FFFFFF;
  --bg-element8: #FBFDFC;
  --bg-invert: #1E1E1E;
  --bg-inline-code: #E9ECEF;
  --bg-tag: #F8F9FA;
  --text1: #212529;
  --text2: #495057;
  --text3: #868E96;
  --text4: #CED4DA;
  --border1: #343A40;
  --border2: #ADB5BD;
  --border3: #DEE2E6;
  --border4: #F1F3F5;
  --primary1: #12B886;
  --primary2: #20C997;
  --destructive1: #FF6B6B;
  --destructive2: #FF8787;
  --button-text: #FFFFFF;
  --slight-layer: rgba(0, 0, 0, 0.05);
  --opaque-layer: rgba(249, 249, 249, 0.85);
  --editor-footer: #FFFFFF;
  --prism-bg: #fbfcfd;
  --prism-default-text: #24292e;
  --prism-selection-bg: rgba(0, 0, 0, 0.15);
  --prism-code-block-bg: #fbfcfd;
  --prism-code-1: #969896;
  --prism-code-2: #24292e;
  --prism-code-3: #a626a4;
  --prism-code-4: #63a35c;
  --prism-code-5: #0184bc;
  --prism-code-6: #50a14f;
  --prism-code-7: #a626a4;
  --prism-code-8: #005cc5;
  --prism-code-9: #a626a4;
  --prism-line-number: #585c63;
}

.kUqjof a {
  text-decoration: none;
  color: inherit;
}
.kUqjof a:hover {
  color: #212529;
}

/* a:-webkit-any-link {
    color: -webkit-link;
    cursor: pointer;
    text-decoration: underline;
} */
.gdnhbG.atom-one code[class*="language-"],
.gdnhbG.atom-one pre[class*="language-"] {
  color: var(--prism-default-text);
  background: none;
  text-align: left;
  white-space: pre;
  word-spacing: normal;
  word-break: normal;
  overflow-wrap: normal;
  tab-size: 4;
  hyphens: none;
}

.gdnhbG.atom-one:not(pre) > code[class*="language-"],
.gdnhbG.atom-one pre[class*="language-"] {
  background: var(--prism-code-block-bg);
}

.gdnhbG.atom-one pre[class*="language-"] {
  padding: 1em;
  margin: 0.5em 0px;
  overflow: auto;
}

.gdnhbG.atom-one code[class*="language-"],
.gdnhbG.atom-one pre[class*="language-"] {
  color: var(--prism-default-text);
  background: none;
  text-align: left;
  white-space: pre;
  word-spacing: normal;
  word-break: normal;
  overflow-wrap: normal;
  tab-size: 4;
  hyphens: none;
}

.gdnhbG pre {
  font-family: "Fira Mono", source-code-pro, Menlo, Monaco, Consolas, "Courier New", monospace;
  font-size: 0.875rem;
  padding: 1rem;
  border-radius: 4px;
  line-height: 1.5;
  overflow-x: auto;
  letter-spacing: 0px;
}

.gdnhbG.atom-one code[class*="language-"],
.gdnhbG.atom-one pre[class*="language-"] {
  color: var(--prism-default-text);
  background: #f8f9fa;
  text-align: left;
  white-space: pre;
  word-spacing: normal;
  word-break: normal;
  overflow-wrap: normal;
  tab-size: 4;
  hyphens: none;
}

.icons-box {
  display: flex
}

.icons {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: .6;
  margin: 0 .5rem;
  cursor: pointer;
  user-select: none;
  border: 1px solid #767676;
  border-radius: 50px;
  transition: .2s ease all
}

.icons:hover {
  opacity: .9;
  box-shadow: var(--shadow)
}

.icons:active {
  opacity: .9;
  box-shadow: var(--shadow-active)
}

.icons .btn-label {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 .5rem;
  cursor: pointer;
  position: relative
}

.like-text-content {
  border-right: .1rem solid #353535;
  padding: 0 .6rem 0 .5rem;
  pointer-events: none
}

.dislike-text-content {
  border-left: .1rem solid #353535;
  padding: 0 .5rem 0 .6rem;
  pointer-events: none
}

.icons .svgs {
  width: 1.3rem;
  fill: #000;
  box-sizing: content-box;
  padding: 10px 10px;
  transition: .2s ease all
}

.icons .input-box {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0
}

.icons #icon-like-regular {
  display: block
}

.icons #icon-like-solid {
  display: none
}

.icons:hover :is(#icon-like-solid,#icon-like-regular) {
  animation: rotate-icon-like .7s ease-in-out both
}

.icons #like-checkbox:checked ~ #icon-like-regular {
  display: none;
  animation: checked-icon-like .5s
}

.icons #like-checkbox:checked ~ #icon-like-solid {
  display: block;
  animation: checked-icon-like .5s
}

.icons #icon-dislike-regular {
  display: block;
  transform: rotate(180deg)
}

.icons #icon-dislike-solid {
  display: none;
  transform: rotate(180deg)
}

.icons:hover :is(#icon-dislike-solid,#icon-dislike-regular) {
  animation: rotate-icon-dislike .7s ease-in-out both
}

.icons #dislike-checkbox:checked ~ #icon-dislike-regular {
  display: none;
  animation: checked-icon-dislike .5s
}

.icons #dislike-checkbox:checked ~ #icon-dislike-solid {
  display: block;
  animation: checked-icon-dislike .5s
}

.icons .fireworks {
  transform: scale(.4)
}

.icons #like-checkbox:checked ~ .fireworks > .checked-like-fx {
  position: absolute;
  width: 10px;
  height: 10px;
  right: 40px;
  border-radius: 50%;
  box-shadow: 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff;
  animation: 1s fireworks-bang ease-out forwards, 1s fireworks-gravity ease-in forwards, 5s fireworks-position linear forwards;
  animation-duration: 1.25s, 1.25s, 6.25s
}

.icons #dislike-checkbox:checked ~ .fireworks > .checked-dislike-fx {
  position: absolute;
  width: 10px;
  height: 10px;
  left: 40px;
  border-radius: 50%;
  box-shadow: 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff;
  animation: 1s fireworks-bang ease-out forwards, 1s fireworks-gravity ease-in forwards, 5s fireworks-position linear forwards;
  animation-duration: 1.25s, 1.25s, 6.25s
}

@keyframes rotate-icon-like {
  0% {
    transform: rotate(0) translate3d(0, 0, 0)
  }
  25% {
    transform: rotate(3deg) translate3d(0, 0, 0)
  }
  50% {
    transform: rotate(-3deg) translate3d(0, 0, 0)
  }
  75% {
    transform: rotate(1deg) translate3d(0, 0, 0)
  }
  100% {
    transform: rotate(0) translate3d(0, 0, 0)
  }
}

@keyframes rotate-icon-dislike {
  0% {
    transform: rotate(180deg) translate3d(0, 0, 0)
  }
  25% {
    transform: rotate(183deg) translate3d(0, 0, 0)
  }
  50% {
    transform: rotate(177deg) translate3d(0, 0, 0)
  }
  75% {
    transform: rotate(181deg) translate3d(0, 0, 0)
  }
  100% {
    transform: rotate(180deg) translate3d(0, 0, 0)
  }
}

@keyframes checked-icon-like {
  0% {
    transform: scale(0);
    opacity: 0
  }
  50% {
    transform: scale(1.2) rotate(-10deg)
  }
}

@keyframes checked-icon-dislike {
  0% {
    transform: scale(0) rotate(180deg);
    opacity: 0
  }
  50% {
    transform: scale(1.2) rotate(170deg)
  }
}

@keyframes fireworks-position {
  0%, 19.9% {
    margin-top: 10%;
    margin-left: 40%
  }
  20%, 39.9% {
    margin-top: 40%;
    margin-left: 30%
  }
  40%, 59.9% {
    margin-top: 20%;
    margin-left: 70%
  }
  60%, 79.9% {
    margin-top: 30%;
    margin-left: 20%
  }
  80%, 99.9% {
    margin-top: 30%;
    margin-left: 80%
  }
}

@keyframes fireworks-gravity {
  to {
    transform: translateY(200px);
    opacity: 0
  }
}

@keyframes fireworks-bang {
  to {
    box-shadow: 114px -107.3333333333px #80f, 212px -166.3333333333px #a600ff, 197px -6.3333333333px #ff006a, 179px -329.3333333333px #30f, -167px -262.3333333333px #ff0062, 233px 65.6666666667px #ff008c, 81px 42.6666666667px #0051ff, -13px 54.6666666667px #00ff2b, -60px -183.3333333333px #0900ff, 127px -259.3333333333px #ff00e6, 117px -122.3333333333px #00b7ff, 95px 20.6666666667px #ff8000, 115px 1.6666666667px #0004ff, -160px -328.3333333333px #00ff40, 69px -242.3333333333px #000dff, -208px -230.3333333333px #ff0400, 30px -15.3333333333px #e6ff00, 235px -15.3333333333px #fb00ff, 80px -232.3333333333px #d5ff00, 175px -173.3333333333px #00ff3c, -187px -176.3333333333px #af0, 4px 26.6666666667px #ff6f00, 227px -106.3333333333px #f09, 119px 17.6666666667px #00ffd5, -102px 4.6666666667px #f08, -16px -4.3333333333px #00fff7, -201px -310.3333333333px #0fd, 64px -181.3333333333px #f700ff, -234px -15.3333333333px #00fffb, -184px -263.3333333333px #a0f, 96px -303.3333333333px #0037ff, -139px 10.6666666667px #0026ff, 25px -205.3333333333px #00ff2b, -129px -322.3333333333px #40ff00, -235px -187.3333333333px #26ff00, -136px -237.3333333333px #0091ff, -82px -321.3333333333px #6a00ff, 7px -267.3333333333px #ff00c8, -155px 30.6666666667px #0059ff, -85px -73.3333333333px #6a00ff, 60px -199.3333333333px #5f0, -9px -289.3333333333px #0fa, -208px -167.3333333333px #00ff80, -13px -299.3333333333px #ff0004, 179px -164.3333333333px #f04, -112px 12.6666666667px #0051ff, -209px -125.3333333333px #f0b, 14px -101.3333333333px #00ff95, -184px -292.3333333333px #f09, -26px -168.3333333333px #09ff00, 129px -67.3333333333px #0084ff, -17px -23.3333333333px #0059ff, 129px 34.6666666667px #7300ff, 35px -24.3333333333px #ffd900, -12px -297.3333333333px #ff8400, 129px -156.3333333333px #0dff00, 157px -29.3333333333px #1a00ff, -221px 6.6666666667px #ff0062, 0 -311.3333333333px #ff006a, 155px 50.6666666667px #0fa, -71px -318.3333333333px #0073ff
  }
}

.bookmark-checkbox {
  display: inline-block;
  margin-right: 20px;
}

.bookmark-checkbox__input {
  display: none;
}

.bookmark-checkbox__label {
  cursor: pointer;
}

.bookmark-checkbox__icon {
  height: 45px;
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
</style>