<template>
  <TagComponent :tagTitle="'유저 랭킹'" :tagSubTitle="'활동에 참여하여 랭킹을 올려보세요'"/>
  <div class="custom-container" style="margin-top: 0;">
    <div class="container">
      <div class="point-container">
        <div class="my-ranking" v-show="userStore.isLoggedIn">
          <h2>내 랭킹</h2>
          <div class="ranking-info">
            <p>등급 : <strong :class="gradeClass">{{ pointStore.myPointRank.grade }}</strong></p>
            <p>누적 포인트 : <strong>{{ pointStore.myPointRank.point }}P</strong></p>
            <div class="ranking-div">
              <span>일간 순위 : <strong>{{ pointStore.myPointRank.dailyRanking }}</strong></span>
              <span>주간 순위 : <strong>{{ pointStore.myPointRank.weeklyRanking }}</strong></span>
            </div>
          </div>
        </div>
        <div class="point">
                    <span :class="pageType === 'daily' ? 'selected ranking' : 'ranking'"
                          @click="changePageType('daily')">
                        일간 랭킹
                    </span>
          <span class="divider">|</span>
          <span :class="pageType === 'weekly' ? 'selected ranking' : 'ranking'"
                @click="changePageType('weekly')">
                        주간 랭킹
                    </span>
        </div>
        <p v-if="pageType === 'daily'">매일 00:00 기준으로 업데이트된 누적 순위입니다.</p>
        <p v-else-if="pageType === 'weekly'">지난주 동안 누적된 포인트를 기준으로 한 순위입니다.</p>
        <LoadingComponent v-if="isLoading" style="margin-top: 90px"/>
        <div class="rank-container" v-else>
          <table class="ranking-table">
            <thead>
            <tr>
              <th>순위</th>
              <th>유저</th>
              <th>포인트</th>
            </tr>
            </thead>
            <tbody>
            <RankingListComponent v-for="pointRanking in pointStore.rankingList"
                                  :key="pointRanking.id" :pointRanking="pointRanking"/>
            </tbody>
          </table>
        </div>
      </div>
      <div class="pagination-container" v-if="!isLoading">
        <PaginationComponent @updatePage="updatePage" :nowPage="page + 1" :totalPage="totalPage"/>
      </div>
    </div>
  </div>


</template>

<script>

import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import { mapStores } from "pinia";
import { usePointStore } from "@/store/usePointStore";
import TagComponent from "@/components/Common/TagComponent.vue";
import { useUserStore } from "@/store/useUserStore";
import RankingListComponent from "@/components/Point/RankingListComponent.vue";
import LoadingComponent from "@/components/Common/LoadingComponent.vue";

export default {
  name: "RankingPage",
  components: { LoadingComponent, RankingListComponent, TagComponent, PaginationComponent },
  computed: {
    ...mapStores(usePointStore, useUserStore),
    gradeClass() {
      switch (this.pointStore.myPointRank.grade) {
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
    },
  },
  data() {
    return {
      isLoading: false,
      page: 0,
      totalPage: 1,
      pageType: "daily"
    }
  },
  methods: {
    async getMyRank() {
      await this.pointStore.getMyPointRanking();
    },
    async fetchRankingData() {
      this.isLoading = true;
      try {
        await this.pointStore.fetchRanking(this.page, this.pageType);

        if (this.pointStore.rankingList.length > 0) {
          this.totalPage = this.pointStore.rankingList[0].totalPage;
        } else {
          this.totalPage = 1;
        }
      }
      catch (error) {
        console.error("랭킹 데이터를 가져오는 중 오류 발생:", error);
      } finally {
        this.isLoading = false;
      }
    },
    async changePageType(type) {
      this.pageType = type;
      this.page = 0;
      await this.fetchRankingData();
    },
    async updatePage(page) {
      this.page = page - 1;
      await this.fetchRankingData();
    },
  },
  mounted() {
    if (this.userStore.isLoggedIn) {
      this.getMyRank();
    }
    this.fetchRankingData();
  },
}
</script>

<style scoped>
.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.container {
  max-width: 1000px;
  margin: 0 auto;
  background-color: #fff;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.point {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  margin-bottom: 0;
  font-size: 25px;
  font-weight: bold;
  border-bottom: 1px solid #ddd;
  padding-bottom: 30px;
}

.point .selected.ranking {
  color: #333;
  pointer-events: none;
}

.point .ranking {
  color: #aaa;
  padding: 0 10px;
}

.point .ranking:hover {
  color: var(--main-color);
  cursor: pointer;
}

.point ~ p {
  margin-top: 10px;
  text-align: right;
}

.divider {
  font-size: 18px;
  color: #ccc;
  margin: 0 10px;
}

.point-container {
  margin-top: 10px;
  border-radius: 8px;
  padding: 15px;
}

.my-ranking {
  text-align: center;
  margin-bottom: 30px;
}

.my-ranking h2 {
  font-size: 24px;
  margin-bottom: 10px;
  font-weight: 600 !important;
}

.my-ranking .ranking-info {
  font-size: 18px;
  color: #333;
}

.my-ranking .ranking-info p {
  margin: 5px 0;
}

.ranking-div span {
  margin: 0 10px;
}

.rank-container {
  padding: 15px;
}

.ranking-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.ranking-table th, .ranking-table td {
  padding: 15px;
  text-align: left;
}

.ranking-table th {
  background-color: #f0f0f0;
  font-weight: bold;
  font-size: 16px;
}

tbody tr:hover {
  background-color: #f5f7f7;
}

.ranking-table th:nth-child(1) {
  text-align: center;
}

.ranking-table th:nth-child(2) {
  padding-left: 30px;
}

</style>

