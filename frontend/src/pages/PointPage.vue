<template>
  <TagComponent :tagTitle="'포인트 내역'" :tagSubTitle="'포인트 적립 및 차감 내역을 확인해보세요'"/>
  <div class="custom-container" style="margin-top: 0;">
    <div class="point-container">
      <div class="my-ranking">
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
      <PointHistoryComponent v-for="(pointHistory, idx) in pointStore.pointHistoryList" :key="idx"
                             :pointHistory="pointHistory"/>
    </div>
    <div class="pagination-container" v-if="isLoading">
      <PaginationComponent @updatePage="updatePage" :nowPage="page + 1" :totalPage="totalPage"/>
    </div>
  </div>


</template>

<script>

import PaginationComponent from "@/components/Common/PaginationComponent.vue";
import { mapStores } from "pinia";
import { usePointStore } from "@/store/usePointStore";
import TagComponent from "@/components/Common/TagComponent.vue";
import PointHistoryComponent from "@/components/Point/PointHistoryComponent.vue";

export default {
  name: "PointPage",
  components: { PointHistoryComponent, TagComponent, PaginationComponent },
  computed: {
    ...mapStores(usePointStore),
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
    }
  },
  data() {
    return {
      isLoading: false,
      page: 0,
      totalPage: 1
    }
  },
  methods: {
    async getMyRank() {
      await this.pointStore.getMyPointRanking();
    },
    updatePage(page) {
      this.page = page - 1;
      this.getPointHistory();
    },
    async getPointHistory() {
      await this.pointStore.getPointHistory(this.page);
      if (this.pointStore.pointHistoryList.length !== 0) {
        this.totalPage = this.pointStore.pointHistoryList[0].totalPage;
      }
      this.isLoading = true;
    },
  },
  mounted() {
    this.getMyRank();
    this.getPointHistory();
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
  width: 80%;
  max-width: 800px;
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

.point a {
  text-decoration: none;
  color: #666;
  padding: 0 10px;
}

.point a:hover {
  color: #1e88e5;
}

.point .ranking {
  color: #aaa;
}

.point .points {
  color: #333;
}

.point-container {
  padding-top: 20px;
  border-radius: 8px;
  padding: 15px;
  width: 80%;
  margin: 10px auto;
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

</style>

