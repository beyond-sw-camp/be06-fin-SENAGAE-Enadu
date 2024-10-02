<template>
    <div class="custom-container">
        <h1 class="title">WIKI : {{ wikiTitle }}</h1>
        <p class="category">
            <CategoryComponent :category=category />
        </p>

        <!-- 페이지네이션과 롤백 문구 -->
        <div class="pagination-container" v-if="!isLoading">
            <PaginationComponent :totalPage="totalPages" :nowPage="page + 1" @updatePage="handlePageUpdate" />
            <span class="rollback-info" style="right: 40px;">버전 롤백은 <strong class="bold-text">견습</strong>부터 가능합니다. </span>
        </div>

        <table v-if="!isLoading">
            <thead>
                <tr>
                    <th>날짜</th>
                    <th>버전 명</th>
                    <th>작성자 명</th>
                    <th>되돌리기</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(version, index) in wikiVersions" :key="index">
                    <td>{{ version.createdAt }}</td>
                    <td>
                        <a @click="goToVersionDetail(version.wikiContentId)" class="version-link">
                            Version {{ version.version }}
                        </a>
                    </td>
                    <td>
                        <NicknameComponent :nickname=version.nickname />
                    </td>
                    <td>
                        <button @click="rollbackVersion(version.wikiContentId)" class="rollback-button">
                            이 버전으로 되돌리기
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
  
<script>
import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import { useWikiStore } from "@/store/useWikiStore";
import { mapState, mapActions } from "pinia";
import CategoryComponent from "@/components/Common/CategoryComponent.vue";
import NicknameComponent from "@/components/Common/NicknameComponent.vue";

export default {
    name: "WikiVersionListPage",
    components: {NicknameComponent, CategoryComponent, PaginationComponent },
    computed: {
        ...mapState(useWikiStore, ["wikiVersions", "wikiTitle", "category", "currentPage", "totalPages"]),
    },
    data() {
        return {
            selectedPage: 1,
            page: 0,
            isLoading: true,
        }
    },
    methods: {
        ...mapActions(useWikiStore, ["fetchWikiVersionList", "fetchWikiDetail", "rollbackWikiVersion"]),

        async handlePageUpdate(newPage) {
            if (newPage !== this.selectedPage) {
                this.selectedPage = newPage;
                await this.fetchWikiVersionList(this.$route.query.id, newPage - 1);
            }
        },
        goToVersionDetail(wikiContentId) {
            this.$router.push({ path: '/wiki/version/detail', query: { id: wikiContentId } });
        },
        async rollbackVersion(wikiContentId) {
            try {
                const success = await this.rollbackWikiVersion(wikiContentId);
                if (success) {
                    alert('롤백 성공 !');
                    await this.fetchWikiVersionList(this.$route.query.id, this.selectedPage - 1);
                }
            } catch (error) {
                console.error('롤백 중 오류 발생 :', error);
            }
        },
    },
    async mounted() {
        const id = this.$route.query.id;
        await this.fetchWikiDetail(id);
        await this.fetchWikiVersionList(id, this.selectedPage - 1);
        this.isLoading = false;
    },
};
</script>
  
<style scoped>
.rollback-info {
    position: absolute;
    top: 50px;
    right: 0;
    font-size: 14px;
    color: #555;
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

.category-badge {
    background-color: #f1f1f1;
    padding: 5px 20px;
    border-radius: 20px;
    display: inline-block;
    color: #333;
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
    background-color: #075baa21;
    font-weight: bold;
}

.version-link {
    color: #007bff;
    text-decoration: none;
    cursor: pointer;
}

.version-link:hover {
    text-decoration: underline;
}

.pagination-container {
    position: relative;
    display: flex;
    justify-content: center;
    margin-bottom: 10px;
}

.rollback-button {
    color: #e81f1f;
    border: none;
    background-color: transparent;
    cursor: pointer;
}

.rollback-button:hover {
    text-decoration: underline;
}
</style>
