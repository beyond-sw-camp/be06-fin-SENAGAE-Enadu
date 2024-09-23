<template>
    <div class="overflow-hidden rounded-lg border border-gray-500 border-opacity-30">
        <div class="mx-5 mb-5 mt-6 sm:mx-6">
            <div class="flex items-center justify-center">
                <img class="h-10 w-10 rounded-full border border-gray-500/30 sm:h-16 sm:w-16" alt="프로필 사진"
                     :src="mypageStore.userInfo.profileImg">
                <div class="ml-3.5 mr-2 flex flex-1 flex-col sm:ml-5 sm:text-2xl">
                    <div class="flex items-center gap-x-2">
                        <span class="line-clamp-1 w-fit break-all pl-0.5 font-bold">
                            {{ mypageStore.userInfo.nickname }}
                        </span>
                        <div class="flex items-center text-sm sm:text-base">
                            <span>{{ mypageStore.userInfo.grade }}</span>
                        </div>
                    </div>
                </div>
                <router-link :to="{ path: '/mypage/history' }"
                             class="inline-flex justify-center rounded-md border border-white-500/30 bg-white px-4 py-2 text-sm font-medium text-black-700 hover:border-white-500/70 focus:outline-none dark:bg-white-700 dark:text-black-300 mr-2">
                    작성 내역
                </router-link>
                <router-link :to="{ path: '/mypage/info' }"
                             class="inline-flex justify-center rounded-md border border-white-500/30 bg-white px-4 py-2 text-sm font-medium text-black-700 hover:border-white-500/70 focus:outline-none dark:bg-white-700 dark:text-black-300">
                    회원 정보 조회
                </router-link>
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
            <button @click="setActiveSection('qna')"
                    :class="getButtonClass('qna')">
                <span class="hover:no-underline">QnA</span>
                <span :class="getUnderlineClass('qna')" aria-hidden="true"></span>
            </button>
        </div>
    </div>
    <div>
        <div v-if="activeSection === 'archive'">
            에러 아카이브 페이지
        </div>
        <div v-if="activeSection === 'wiki'">
            위키
        </div>
        <div v-if="activeSection === 'qna'">
            <div class="qna-inner">
                <div class="qna-list-flex">
                    <QnaCardComponent
                        v-for="qnaCard in mypageStore.scrap.qnaList"
                        :key="qnaCard.id"
                        :qnaCard="qnaCard"
                    />
                </div>
            </div>
        </div>
        <div class="pagination-container">
            <pagination-component @updatePage="updatePage" :totalPage="totalPage"/>
        </div>
    </div>
</template>

<script>
import {mapStores} from "pinia";
import {useMypageStore} from "@/store/useMypageStore";
import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import QnaCardComponent from "@/components/qna/QnaListCardComponent.vue";

export default {
    name: "ScrapListComponent",
    components: {QnaCardComponent, PaginationComponent},
    data() {
        return {
            activeSection: 'qna',
            page: 0,
            totalPage: 1
        };
    },
    computed: {
        ...mapStores(useMypageStore)
    },
    methods: {
        setActiveSection(section) {
            this.activeSection = section;
            this.page = 0;
            this.loadData();
        },
        updatePage(page) {
            this.page = page - 1;
            this.loadData();
        },
        async loadData() {
            if (this.activeSection === 'qna') {
                await this.fetchQnaList();
            }
        },
        async fetchQnaList() {
            await this.mypageStore.getQnaScrapList(this.page);
            const qnaList = this.mypageStore.scrap.qnaList || [];
            if (qnaList.length !== 0) {
                this.totalPage = qnaList[0].totalPage;
            } else {
                alert("qna 스크랩한 내역이 없습니다.");
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
        this.$emit("sub-title", "스크랩 목록");
        this.mypageStore.fetchUserInfo();
        this.loadData();
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

.bg-white {
    border: 1px solid rgb(107 114 128 / 0.3);
}

.qna-inner {
    width: auto;
    height: max-content;
    background-color: #fff;
    margin: 25px;
}

.qna-list-flex {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
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