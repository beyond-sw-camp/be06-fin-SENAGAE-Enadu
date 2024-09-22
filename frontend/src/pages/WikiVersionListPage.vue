<template>
    <div class="container">
        <h1 class="title">WIKI : {{ wikiTitle }}</h1>
        <p class="category">category : {{ category }}</p>

        <table>
            <thead>
                <tr>
                    <th>날짜</th>
                    <th>버전 명</th>
                    <th>작성자 명</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(version, index) in wikiVersions" :key="index">
                    <td>{{ version.createdAt }}</td>
                    <td><a @click="goToVersionDetail(version.wikiContentId)" class="version-link">V{{ version.version }}</a></td>
                    <td>{{ version.nickname }}</td>
                </tr>
            </tbody>
        </table>

        <div v-if="!isLoading"></div>
        <pagination-component v-else style="margin-top: 20px;" @updatePage="updatePage" :totalPage="totalPage" />
    </div>
</template>
  
<script>
import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import { useWikiStore } from "@/store/useWikiStore";
import { mapState, mapActions } from "pinia";

export default {
    name: "WikiVersionListPage",
    components: {
        PaginationComponent
    },
    computed: {
        ...mapState(useWikiStore, ["wikiVersions", "wikiTitle", "category", "currentPage", "totalPages"]),
    },
    data() {
        return {
            selectedPage: 1,
            totalPages: 1,
            isLoading: true,
        }
    },
    methods: {
        ...mapActions(useWikiStore, ["fetchWikiVersionList", "fetchWikiDetail"]),

        updatePage(page) {
            this.page = page-1
        },
        goToVersionDetail(wikiContentId) {
            // 해당 버전의 위키 상세 페이지로 이동
            this.$router.push(`/wiki/version/detail?id=${wikiContentId}`);
        },
    },
    async mounted() {
        const id = this.$route.query.id;
        await this.fetchWikiDetail(id);
        await this.fetchWikiVersionList(id);
    },
};
</script>
  
<style scoped>
.container {
    width: 80%;
    margin: 50px auto;
}

.title {
    font-size: 2.5rem;
    font-weight: bold;
    margin-bottom: 10px;
}

.category {
    font-size: 1.2rem;
    color: #666;
    margin-bottom: 30px;
}

table {
    width: 100%;
    border-collapse: collapse;
    background-color: white;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

table th,
table td {
    padding: 12px 15px;
    text-align: center;
    border-bottom: 1px solid #ddd;
}

table th {
    background-color: #f1f1f1;
    font-weight: bold;
}

.version-link {
    color: #007bff;
    text-decoration: none;
}

.version-link:hover {
    text-decoration: underline;
}
</style>
  