<template>
    <div class="qna-top">
        <p id="main-title">Mypage</p>
        <p id="sub-title">모시깽</p>
    </div>
    <div class="overflow-hidden rounded-lg border border-gray-500 border-opacity-30">
        <div class="mx-5 mb-5 mt-6 sm:mx-6">
            <div class="flex items-center justify-center">
                <img class="h-10 w-10 rounded-full border border-gray-500/30 sm:h-16 sm:w-16" alt="프로필 사진"
                     :src="userStore.userInfo.profileImg">
                <div class="ml-3.5 mr-2 flex flex-1 flex-col sm:ml-5 sm:text-2xl">
                    <div class="flex items-center gap-x-2">
                        <span class="line-clamp-1 w-fit break-all pl-0.5 font-bold">{{ userStore.userInfo.nickname }}</span>
                        <div class="flex items-center text-sm sm:text-base">
                            <span>{{ userStore.userInfo.grade }}</span>
                        </div>
                    </div>
                </div>
                <router-link :to="firstButtonHref"
                   class="inline-flex justify-center rounded-md border border-white-500/30 bg-white px-4 py-2 text-sm font-medium text-black-700 hover:border-white-500/70 focus:outline-none dark:bg-white-700 dark:text-black-300 mr-2">
                    {{ firstButtonText }}
                </router-link>
                <router-link :to="secondButtonHref"
                   class="inline-flex justify-center rounded-md border border-white-500/30 bg-white px-4 py-2 text-sm font-medium text-black-700 hover:border-white-500/70 focus:outline-none dark:bg-white-700 dark:text-black-300">
                    {{ secondButtonText }}
                </router-link>
            </div>
        </div>
        <div class="relative flex gap-x-6 border-t border-gray-500/30 px-6 sm:gap-x-8 dark:border-gray-500/70" v-show="!isInfoRoute">
            <a
                aria-current="page"
                class="text-blue-500 group relative flex min-w-0 items-center justify-center overflow-hidden px-1 py-4 text-center text-sm font-medium focus:z-10"
                href="/users/165673/articles">
                <span class="hover:no-underline">에러 아카이브</span>
                <span aria-hidden="true" class="bg-blue-500 absolute inset-x-0 bottom-0 h-0.5"></span>
            </a>
            <a class="text-gray-500 hover:text-blue-500 dark:hover:text-blue-200 group relative flex min-w-0 items-center justify-center overflow-hidden px-1 py-4 text-center text-sm font-medium focus:z-10"
               href="/users/165673/questions">
                <span class="hover:no-underline">위키</span>
                <span aria-hidden="true"
                      class="group-hover:bg-blue-500 dark:group-hover:bg-blue-200 absolute inset-x-0 bottom-0 h-0.5"></span>
            </a>
            <a
                class="text-gray-500 hover:text-blue-500 dark:hover:text-blue-200 group relative flex min-w-0 items-center justify-center overflow-hidden px-1 py-4 text-center text-sm font-medium focus:z-10"
                href="/users/165673/activity">
                <span class="hover:no-underline">질문</span>
                <span aria-hidden="true"
                      class="group-hover:bg-blue-500 dark:group-hover:bg-blue-200 absolute inset-x-0 bottom-0 h-0.5"></span>
            </a>
            <a
                class="text-gray-500 hover:text-blue-500 dark:hover:text-blue-200 group relative flex min-w-0 items-center justify-center overflow-hidden px-1 py-4 text-center text-sm font-medium focus:z-10"
                href="/users/165673/scraped" v-show="isHistoryRoute">
                <span class="hover:no-underline">답변</span>
                <span aria-hidden="true"
                      class="group-hover:bg-blue-500 dark:group-hover:bg-blue-200 absolute inset-x-0 bottom-0 h-0.5"></span>
            </a>
        </div>
    </div>
    <router-view />
</template>

<script>
import { mapStores } from "pinia";
import {useUserStore} from "@/store/useUserStore";

export default {
    name: "MypageComponent",
    data() {
        return {
        };
    },
    computed: {
        ...mapStores(useUserStore),
        firstButtonHref() {
            return this.$route.path.includes('/history') ? '/mypage/scrap' : '/mypage/history';
        },
        secondButtonHref() {
            return this.$route.path.includes('/info') ? '/mypage/scrap' : '/mypage/info';
        },
        firstButtonText() {
            return this.$route.path.includes('/history') ? '스크랩 목록' : '작성 내역';
        },
        secondButtonText() {
            return this.$route.path.includes('/info') ? '스크랩 목록' : '회원 정보 조회';
        },
        isHistoryRoute() {
            return this.$route.path.includes('/history');
        },
        isInfoRoute() {
            return this.$route.path.includes('/info');
        }
    },
    mounted() {
        this.userStore.fetchUserInfo();
    }
}
</script>

<style scoped>

#main-title {
    text-align: center;
    font-size: 35px;
}

#sub-title {
    text-align: center;
    font-size: 25px;
}
.border-gray-500 {
    border: 1px solid rgb(107 114 128 / 0.3);
}

.border-gray-500\/30 {
    border-top: 1px solid rgb(107 114 128 / 0.3);
}

.bg-white {
    border: 1px solid rgb(107 114 128 / 0.3);
}
</style>