<template>
        <div class="custom-container">
        <h1 class="title">WIKI : {{ wikiTitle }}</h1>
        <p class="category">
      <span class="category-badge"> category : {{ category }}</span>
    </p>

        <div class="pagination-container" v-if="!isLoading">
            <PaginationComponent :totalPage="totalPages" :nowPage="page + 1" @updatePage="handlePageUpdate" />
        </div>

        <table v-if="!isLoading">
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
                    <td>
                        <a @click="goToVersionDetail(version.wikiContentId)" class="version-link">
                            V{{ version.version }}
                        </a>
                    </td>
                    <td>{{ version.nickname }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
  
<script>
import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import { useWikiStore } from "@/store/useWikiStore";
import { mapState, mapActions } from "pinia";

export default {
    name: "WikiVersionListPage",
    components: { PaginationComponent },
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
        ...mapActions(useWikiStore, ["fetchWikiVersionList", "fetchWikiDetail"]),

        async handlePageUpdate(newPage) {
            if (newPage !== this.selectedPage) {
                this.selectedPage = newPage;
                await this.fetchWikiVersionList(this.$route.query.id, newPage - 1);
            }
        },
        goToVersionDetail(wikiContentId) {
            this.$router.push({ path: '/wiki/version/detail', query: { id: wikiContentId } });
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
}

.version-link:hover {
    text-decoration: underline;
}

.pagination-container {
  margin-top: 20px;
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
