<template>
    <div v-if="isLoading"></div>
    <div id="root" v-else>
        <div class="sc-dPiLbb sc-bBHHxi kTIDXm">
            <div class="sc-TBWPX dXONqK sc-jQrDum fiOuRZ">
                <div class="head-wrapper">
                    <h1>{{ wikiDetail.title }}</h1>
                    <div class="sc-fvxzrP jGdQwA" style="display: flex; justify-content: space-between;">
                        <div class="information">
                            <span class="version">
                                <span class="sc-egiyK cyyZlI">버전 : V{{ wikiDetail.version }}</span>
                            </span>
                        </div>
                        <div class="sc-fbyfCU eYeYLy" style="margin-left: auto;">
                            <button class="ml-3 text-white px-4 py-2 rounded-md" style="background-color:#12B886"
                                @click="goToVersionList">
                                이전 버전 목록
                            </button>
                            <div class="bookmark-checkbox">
                                <input type="checkbox" id="bookmark-toggle" :checked="wikiDetail.checkScrap"
                                    class="bookmark-checkbox__input">
                                <label for="bookmark-toggle" class="bookmark-checkbox__label">
                                    <svg class="bookmark-checkbox__icon" viewBox="0 0 24 24">
                                        <path class="bookmark-checkbox__icon-back"
                                            d="M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z"></path>
                                        <path class="bookmark-checkbox__icon-check" d="M8 11l3 3 5-5"></path>
                                    </svg>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="sc-cZMNgc bpMcZw">
                    <a class="sc-dtMgUX gISUXI" href="/tags/LomBok">{{ wikiDetail.category }}</a>
                </div>

                <div class="sc-jlRLRk iGRQXB">
                    <div class="sc-dUbtfd kOYWDF">
                        <div class="sc-jHkVzv dyVEVs sc-htJRVC jfYEFP">
                            <div v-for="(anchor, idx) in titles" @click="handleAnchorClick(anchor)" :key="idx"
                                class="sc-cbTzjv kUqjof" :style="{ marginLeft: `${anchor.indent * 12}px` }">
                                <a style="cursor:pointer">{{ anchor.title }}</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="sc-dFtzxp bfzjcP" style="margin-top: 1px;">
            <div class="sc-bXTejn FTZwa">
                <div class="sc-eGRUor gdnhbG atom-one">
                    <v-md-preview ref="preview" :text="wikiDetail.content" />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { mapStores } from "pinia";
import { useWikiStore } from "@/store/useWikiStore";
import { useUserStore } from "@/store/useUserStore";
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/style/preview.css';
import hljs from 'highlight.js';

VMdPreview.use(githubTheme, {
    Hljs: hljs,
});

export default {
    name: "WikiVersionDetailComponent",
    data() {
        return {
            id: '',
            userGrade: 'GUEST', // 기본값 설정
            titles: [], // 목차 저장할 변수
            isLoading: true,
        };
    },
    computed: {
        ...mapStores(useWikiStore),
        ...mapStores(useUserStore),
        wikiDetail() {
            return this.wikiStore.wikiDetail || {};
        },
    },
    async mounted() {
        this.id = this.$route.query.id || this.$route.params.id;
        if (this.id) {
            await this.fetchWikiVersionDetail(); // 버전 상세 조회
        }
        this.isLoading = false;
        this.$nextTick(() => {
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
    },
    methods: {
        async fetchWikiVersionDetail() {
            try {
                await this.wikiStore.fetchWikiVersionDetail(this.id); // 버전 상세 조회 API 호출
                this.userGrade = this.wikiStore.wikiDetail.userGrade || 'GUEST';
            } catch (error) {
                console.error('Wiki Version Detail Fetch Error:', error);
            }
        },
        goToVersionList() {
            this.$router.push({ path: '/wiki/version/list', query: { id: this.id } });
        },
        handleAnchorClick(anchor) {
            const { preview } = this.$refs;
            const { lineIndex } = anchor;

            const heading = preview.$el.querySelector(`[data-v-md-line="${lineIndex}"]`);

            if (heading) {
                preview.scrollToTarget({
                    target: heading,
                    scrollContainer: window,
                    top: 60,
                });
            }
        },
    },
    components: {
        VMdPreview,
    },
};
</script>

<style scoped>
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
    color: #12B886;
    text-decoration: none;
    cursor: pointer;
}

.sc-egiyK:hover {
    text-decoration: underline;
}

v-md-preview {
    font-size: 1.125rem;
    line-height: 1.7;
    word-break: keep-all;
    overflow-wrap: break-word;
    color: var(--text1);
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

.jGdQwA .information .version {
    color: var(--text1);
    font-weight: bold;
}

.jGdQwA .information .separator {
    margin-left: 0.5rem;
    margin-right: 0.5rem;
}

.eYeYLy {
    display: flex;
}

.iIZjji {
    width: 96px;
    height: 32px;
    font-size: 16px;
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

.jGdQwA {
    font-size: 1rem;
    color: var(--text2);
    display: flex;
    -webkit-box-pack: justify;
    justify-content: space-between;
    -webkit-box-align: center;
    align-items: center;
}

.bpMcZw {
    margin-top: 0.875rem;
    margin-bottom: -0.875rem;
    min-height: 0.875rem;
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

.fb_reset>div {
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

.fb_dialog_content .dialog_title>span {
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
        width: 100% - 24px
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
        width: 100% - 24px
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

.FTZwa p+h1,
.FTZwa p+h2,
.FTZwa p+h3,
.FTZwa p+h4 {
    margin-top: 2.5rem;
}

.gdnhbG.atom-one:not(pre)>code[class*="language-"],
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

.gdnhbG.atom-one:not(pre)>code[class*="language-"],
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

.bookmark-checkbox__input:checked+.bookmark-checkbox__label .bookmark-checkbox__icon {
    fill: #ff5a5f;
}

.bookmark-checkbox__input:checked+.bookmark-checkbox__label .bookmark-checkbox__icon-back {
    stroke: #ff5a5f;
    transform: scale(1.1);
    animation: bookmark-pop 0.4s ease;
}

.bookmark-checkbox__input:checked+.bookmark-checkbox__label .bookmark-checkbox__icon-check {
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
  