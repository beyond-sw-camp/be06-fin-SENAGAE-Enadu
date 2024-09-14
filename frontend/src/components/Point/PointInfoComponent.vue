<template>
  <div class="container" >
    <div class="point">
      <a href="/point/rank" class="ranking">랭킹</a>
      <span class="divider">|</span>
      <a href="/point/info" class="points">포인트 내역</a>
    </div>

    <div class="point-container">
      <div class="my-ranking">
        <h2>내 랭킹</h2>
        <div class="ranking-info">
          <p>순위: <strong>{{ pointStore.myPointRank.rank }}</strong></p>
          <p>포인트: <strong>{{ pointStore.myPointRank.point }}P</strong></p>
        </div>
      </div>
      <PointHistoryComponent v-for="(pointHistory, idx) in pointStore.pointHistoryList" :key="idx"
                             :pointHistory="pointHistory"/>
    </div>
  </div>


</template>

<script>
import PointHistoryComponent from "@/components/Point/PointHistoryComponent.vue";
import {mapStores} from "pinia";
import {usePointStore} from "@/store/usePointStore";

export default {
  name: "MyPointInfoComponent",
  components: {PointHistoryComponent},
  computed: {
    ...mapStores(usePointStore),
  }
}
</script>

<style scoped>
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

.divider {
  font-size: 18px;
  color: #ccc;
  margin: 0 10px;
}

.point-container {
  margin-top: 10px;
  padding-top: 20px;
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

</style>