<template>
    <div class="overflow-hidden rounded-lg border border-gray-500 border-opacity-30">
        <div class="mx-5 mb-5 mt-6 sm:mx-6">
            <div class="flex items-center justify-center">
                <img class="h-10 w-10 rounded-full border border-gray-500/30 sm:h-16 sm:w-16" alt="프로필 사진"
                     :src="userLogStore.userInfo.profileImg">
                <div class="ml-3.5 mr-2 flex flex-1 flex-col sm:ml-5 sm:text-2xl">
                    <div class="flex items-center gap-x-2">
                        <span class="line-clamp-1 w-fit break-all pl-0.5 font-bold">{{ userLogStore.userInfo.nickname }}</span>
                        <div class="flex items-center text-sm sm:text-base">
                            <span :class="dynamicClass">{{ userLogStore.userInfo.grade }}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="relative flex gap-x-6 border-t border-gray-500/30 px-6 sm:gap-x-8 dark:border-gray-500/70">
            <button @click="setActiveSection('archive')"
                    :class="getButtonClass('archive')">
                <span class="hover:no-underline">에러 아카이브</span>
                <span :class="getUnderlineClass('archive')" aria-hidden="true"></span>
            </button>
            <button @click="setActiveSection('wiki')"
                    :class="getButtonClass('wiki')">
                <span class="hover:no-underline">위키</span>
                <span :class="getUnderlineClass('wiki')" aria-hidden="true"></span>
            </button>
            <button @click="setActiveSection('question')"
                    :class="getButtonClass('question')">
                <span class="hover:no-underline">질문</span>
                <span :class="getUnderlineClass('question')" aria-hidden="true"></span>
            </button>
            <button @click="setActiveSection('answer')"
                    :class="getButtonClass('answer')">
                <span class="hover:no-underline">답변</span>
                <span :class="getUnderlineClass('answer')" aria-hidden="true"></span>
            </button>
        </div>
    </div>
    <div>
        <LoadingComponent v-if="isLoading" style="margin-top: 30px" />
        <div v-if="activeSection === 'archive'">
            <div class="errorarchive-inner">
                <div class="errorarchive-list-flex">
                    <ErrorArchiveCardComponent
                        v-for="errorarchiveCard in userLogStore.history.archiveList"
                        :key="errorarchiveCard.id"
                        v-bind:errorarchiveCard="errorarchiveCard"
                    />
                </div>
            </div>
        </div>
        <div v-if="activeSection === 'wiki'">
            <div class="wiki-list-grid" v-if="!isLoading">
                <WikiCardComponent v-for="wikiCard in userLogStore.history.wikiList" :key="wikiCard.id" :wikiCard="wikiCard" />
            </div>
        </div>
        <div v-if="activeSection === 'question'">
            <div class="qna-inner">
                <div class="qna-list-flex" v-if="!isLoading">
                    <QnaCardComponent
                        v-for="qnaCard in userLogStore.history.questionList"
                        :key="qnaCard.id"
                        :qnaCard="qnaCard"
                    />
                </div>
            </div>
        </div>
        <div v-if="activeSection === 'answer'">
            <div class="qna-inner">
                <div class="qna-list-flex" v-if="!isLoading">
                    <QnaCardComponent
                        v-for="qnaCard in userLogStore.history.answerList"
                        :key="qnaCard.id"
                        :qnaCard="qnaCard"
                    />
                </div>
            </div>
        </div>
        <div class="pagination-container" v-if="!isLoading && totalPage > 0">
            <PaginationComponent @updatePage="updatePage" :nowPage="page + 1" :totalPage="totalPage"/>
        </div>
    </div>
</template>

<script>
import { mapStores } from "pinia";
import { useUserLogStore } from "@/store/useUserLogStore";
import QnaCardComponent from "@/components/Qna/List/QnaListCardComponent.vue";
import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import WikiCardComponent from "@/components/wiki/WikiCardComponent.vue";
import LoadingComponent from "@/components/Common/LoadingComponent.vue";
import ErrorArchiveCardComponent from "@/components/errorarchive/ErrorArchiveCardComponent.vue";

export default {
    name: "UserLogComponent",
    components: {ErrorArchiveCardComponent, LoadingComponent, WikiCardComponent, PaginationComponent, QnaCardComponent },
    data() {
        return {
            isLoading: true,
            activeSection: 'archive',
            page: 0,
            totalPage: 1
        };
    },
    computed: {
        ...mapStores(useUserLogStore),
        nickname() {
            return this.$route.params.nickname
        },
        dynamicClass() {
            switch (this.userLogStore.userInfo.grade) {
                case '뉴비':
                    return 'newbie';
                case '견습':
                    return 'apprentice';
                case '프로':
                    return 'pro';
                case '마스터':
                    return 'master';
                case '신':
                    return 'god';
                default:
                    return '';
            }
        }
    },
    methods: {
        async fetchUserInfoAndLoadData() {
            try {
                await this.userLogStore.fetchUserInfo(this.nickname);
                await this.loadData();
            } catch (error) {
                console.error("데이터 로딩 중 에러:", error);
            }
        },
        setActiveSection(section) {
            this.activeSection = section;
            this.page = 0;
            this.loadData();
        },
        updatePage(page) {
            this.page = page - 1;
            if (page % 5 === 0 || page % 5 === 1) {
              this.isLoading = true;
            }
            this.loadData();
            this.isLoading = false;
        },
        async loadData() {
            this.isLoading = true;
            try {
                switch (this.activeSection) {
                    case 'archive':
                        await this.fetchArchiveList();
                        break;
                    case 'wiki':
                        await this.fetchWikiList();
                        break;
                    case 'question':
                        await this.fetchQuestionList();
                        break;
                    case 'answer':
                        await this.fetchAnswerList();
                        break;
                }
            } finally {
                this.isLoading = false;
            }
        },
        async fetchArchiveList() {
            await this.userLogStore.getLogArchiveList(this.page);
            const archiveList = this.userLogStore.history.archiveList || [];
            if (archiveList.length !== 0) {
                this.totalPage = archiveList[0].totalPage;
            } else {
                alert("작성한 아카이브 내역이 없습니다.");
            }
        },
        async fetchWikiList() {
            await this.userLogStore.getLogWikiList(this.page);
            const wikiList = this.userLogStore.history.wikiList || [];
            if (wikiList.length !== 0) {
                this.totalPage = wikiList[0].totalPage;
            } else {
                alert("작성한 위키 내역이 없습니다.");
            }
        },
        async fetchQuestionList() {
            await this.userLogStore.getLogQuestionList(this.page);
            const questionList = this.userLogStore.history.questionList || [];
            if (questionList.length !== 0) {
                this.totalPage = questionList[0].totalPage;
            } else {
                alert("작성한 질문 내역이 없습니다.");
            }
        },
        async fetchAnswerList() {
            await this.userLogStore.getLogAnswerList(this.page);
            const answerList = this.userLogStore.history.answerList || [];
            if (answerList.length !== 0) {
                this.totalPage = answerList[0].totalPage;
            } else {
                alert("작성한 답변 내역이 없습니다.");
            }
        },
        getButtonClass(section) {
            return this.activeSection === section
                ? 'text-blue-500 group relative flex min-w-0 items-center justify-center overflow-hidden px-1 py-4 text-center text-sm font-medium focus:z-10'
                : 'text-gray-500 hover:text-blue-500 dark:hover:text-blue-200 group relative flex min-w-0 items-center justify-center overflow-hidden px-1 py-4 text-center text-sm font-medium focus:z-10';
        },
        getUnderlineClass(section) {
            return this.activeSection === section
                ? 'bg-blue-500 absolute inset-x-0 bottom-0 h-0.5'
                : 'group-hover:bg-blue-500 absolute inset-x-0 bottom-0 h-0.5';
        }
    },
    mounted() {
        this.$emit("sub-title", "작성 내역");
        this.fetchUserInfoAndLoadData();
    }
};
</script>

<style scoped>
.border-gray-500 {
    border: 1px solid rgb(107 114 128 / 0.3);
}

.border-gray-500\/30 {
    border-top: 1px solid rgb(107 114 128 / 0.3);
}

.errorarchive-inner {
    width: auto;
    height: max-content;
    background-color: #fff;
    margin: 25px;
}

.errorarchive-list-flex {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    grid-auto-rows: auto;
    gap: 26px 36px;
    justify-items: stretch;
    max-width: 100%;
    margin: 0 auto
}

.wiki-list-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(240px, 270px));
    grid-auto-rows: auto;
    gap: 0 36px;
    justify-items: center;
    justify-content: center;
    align-content: center;
    max-width: 100%;
    margin: 35px 10px;
}

.qna-inner {
    width: auto;
    height: max-content;
    background-color: #fff;
    margin: 25px;
}

.qna-list-flex {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(230px, 1fr));
    grid-auto-rows: auto;
    gap: 26px 36px;
    justify-items: stretch;
    max-width: 100%;
    margin: 0 auto;
}

.pagination-container {
    display: flex;
    justify-content: center;
    margin: 20px auto;
}
</style>