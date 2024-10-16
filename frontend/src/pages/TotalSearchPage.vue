<template>
    <TagComponent :tagTitle="'통합 검색 결과'" :tagSubTitle="'한번에 모두 검색'"/>
    <div class="custom-container" style="margin-top: 0;">

        <div v-if="isLoading" style="text-align:center;"></div>
        <div v-else class="subject-container">
            <div class="subject-box">
                <router-link :to="'/errorarchive/list?type=tc&keyword='+keyword" class="subject">에러 아카이브</router-link>
                <router-link :to="'/errorarchive/list?type=tc&keyword='+keyword" class="show-all-button">더 보기</router-link>
            </div>
            <div class="errorarchive-inner">
                <div class="errorarchive-list-flex">
                    <ErrorArchiveCardComponent
                        v-for="errorarchiveCard in mainStore.searchInfo.errorArchiveResList"
                        :key="errorarchiveCard.id"
                        v-bind:errorarchiveCard="errorarchiveCard"
                    />
                </div>
            </div>
            <div v-if="mainStore.searchInfo.errorArchiveResList.length === 0" style="display:flex">
                <div style="margin:auto;">
                    <span style="font-size: 20px; color: rgb(111, 111, 111);">검색 결과가 없습니다.</span>
                </div>
            </div>
        </div>

        <div v-if="isLoading" style="text-align:center;"></div>
        <div v-else class="subject-container">
            <div class="subject-box">
                <router-link :to="'/wiki/list?keyword='+keyword" class="subject">위키</router-link>
                <router-link :to="'/wiki/list?keyword='+keyword" class="show-all-button">더 보기</router-link>
            </div>
            <div class="wiki-list-grid" v-if="!isLoading">
                <WikiCardComponent v-for="wikiCard in mainStore.searchInfo.wikiListResList" :key="wikiCard.id"
                                   :wikiCard="wikiCard"/>
            </div>
            <div v-if="mainStore.searchInfo.wikiListResList.length === 0" style="display:flex">
                <div style="margin:auto;">
                    <span style="font-size: 20px; color: rgb(111, 111, 111);">검색 결과가 없습니다.</span>
                </div>
            </div>
        </div>

        <LoadingComponent v-if="isLoading"/>
        <div v-else class="subject-container">
            <div class="subject-box">
                <router-link :to="'/qna/list?keyword='+keyword" class="subject">QnA</router-link>
                <router-link :to="'/qna/list?keyword='+keyword" class="show-all-button">더 보기</router-link>
            </div>
            <div class="qna-inner">
                <div class="qna-list-flex">
                    <QnaCardComponent
                        v-for="qnaCard in mainStore.searchInfo.qnaListResList"
                        :key="qnaCard.id"
                        v-bind:qnaCard="qnaCard"
                    />
                </div>
            </div>
            <div v-if="mainStore.searchInfo.qnaListResList.length === 0" style="display:flex">
                <div style="margin:auto;">
                    <span style="font-size: 20px; color: rgb(111, 111, 111);">검색 결과가 없습니다.</span>
                </div>
            </div>
        </div>
    </div>
</template>


<script>


import {mapStores} from "pinia";
import {useMainStore} from "@/store/useMainStore";
import ErrorArchiveCardComponent from "@/components/errorarchive/ErrorArchiveCardComponent.vue";
import WikiCardComponent from "@/components/wiki/WikiCardComponent.vue";
import QnaCardComponent from "@/components/Qna/List/QnaListCardComponent.vue";
import LoadingComponent from "@/components/Common/LoadingComponent.vue";
import TagComponent from "@/components/Common/TagComponent.vue";
import {useQnaStore} from "@/store/useQnaStore";
import {useWikiStore} from "@/store/useWikiStore";

export default {
    name: "TotalSearchPage",
    components: {TagComponent, LoadingComponent, QnaCardComponent, WikiCardComponent, ErrorArchiveCardComponent},
    computed: {
        ...mapStores(useMainStore,useWikiStore,useQnaStore),
    },
    data() {
        return {
            isLoading: true,
            keyword: "",
        }
    },
    methods: {
        async getTotalSearchPageInfo() {
            await this.mainStore.getTotalSearchInfo(this.keyword);
            this.isLoading = false;
        }
    },
    mounted() {
        this.keyword = this.$route.query.keyword;
        this.getTotalSearchPageInfo();
    }
}
</script>


<style scoped>
.subject-container {
    margin-bottom: 70px;
}

.subject-box {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-bottom: 15px;
    font-weight: bold;
}

.subject {
    font-size: 25px;
}

.qna-list-flex {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(230px, 1fr));
    grid-auto-rows: auto;
    gap: 26px 36px;
    justify-items: stretch;
    max-width: 100%;
    margin: 0 auto
}

.qna-inner {
    width: auto;
    height: max-content;
    background-color: #fff;
}

.errorarchive-inner {
    width: auto;
    height: max-content;
    background-color: #fff;
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
    grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
    grid-auto-rows: auto;
    gap: 0 36px;
    justify-items: stretch;
    max-width: 100%;
    margin: 0 auto;
}
</style>