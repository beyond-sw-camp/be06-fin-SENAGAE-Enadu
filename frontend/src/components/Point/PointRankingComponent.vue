<template>
    <div class="container">
        <div class="point">
            <a href="/point/rank" class="ranking">랭킹</a>
            <span class="divider">|</span>
            <a href="/point/info" class="points">포인트 내역</a>
        </div>

        <div class="rank-container">
            <div class="my-ranking">
                <h2>내 랭킹</h2>
                <div class="ranking-info">
                    <p>등급: <strong :class="gradeClass">{{ pointStore.myPointRank.grade }}</strong></p>
                    <p>순위: <strong>{{ pointStore.myPointRank.rank }}</strong></p>
                    <p>포인트: <strong>{{ pointStore.myPointRank.point }}P</strong></p>
                </div>
            </div>
            <table class="ranking-table">
                <thead>
                <tr>
                    <th>순위</th>
                    <th>유저</th>
                    <th>포인트</th>
                </tr>
                </thead>
                <tbody>
                <PointRankingUserComponent v-for="(pointRanking, idx) in pointStore.pointRankingList" :key="idx"
                                           :pointRanking="pointRanking"/>
                </tbody>
            </table>
        </div>
    </div>
</template>


<script>

import PointRankingUserComponent from "@/components/Point/PointRankingUserComponent.vue";
import {mapStores} from "pinia";
import {usePointStore} from "@/store/usePointStore";

export default {
    name: "PointRankingComponent",
    components: {PointRankingUserComponent},
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
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
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
    color: #333;
}

.point .points {
    color: #aaa;
}

.divider {
    font-size: 18px;
    color: #ccc;
    margin: 0 10px;
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

.rank-container {
    margin-top: 10px;
    padding-top: 20px;
    border-radius: 8px;
    padding: 15px;
}

.ranking-table {
    width: 100%;
    border-collapse: collapse;
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