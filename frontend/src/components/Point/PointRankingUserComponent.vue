<template>
  <tr>
    <td><strong>{{ pointRanking.rank }}</strong></td>
    <td>
      <p :class="gradeClass">{{ pointRanking.grade }}</p>
      <img :src="pointRanking.profileImg" alt="user2" class="user-icon">
      <span class="username">{{ pointRanking.nickname }}</span>
    </td>
    <td class="rating" :class="pointRanking.point < 0 ? 'negative' : ''">{{ pointRanking.point }}</td>
  </tr>
</template>


<script>
export default {
  name: "PointRankingUserComponent",
  props: ["pointRanking"],
    computed: {
        gradeClass() {
            switch (this.pointRanking.grade) {
                case '뉴비':
                    return 'grade newbie';
                case '견습':
                    return 'grade apprentice';
                case '프로':
                    return 'grade pro';
                case '마스터':
                    return 'grade master';
                case '신':
                    return 'grade god';
                default:
                    return 'grade';
            }
        }
    }
}
</script>


<style scoped>
.ranking-table th, .ranking-table td {
  padding: 15px;
  text-align: left;
}

.ranking-table th {
  background-color: #f0f0f0;
  font-weight: bold;
  font-size: 16px;
}

.ranking-table td  {
  border-bottom: 1px solid #ddd;
}

.ranking-table td:nth-child(2) {
  display: flex;
  align-items: center;
}
.ranking-table i {
  font-size: 20px;
  margin-right: 15px;
}

.ranking-table .user-icon {
  border-radius: 50%;
  margin-right: 20px;
  width: 30px;
  height: 30px;
}

.username {
  font-weight: bold;
}

.rating {
  text-align: right;
  font-weight: bold;
  color: #1e88e5;
}

.rating.negative {
  color: #e53935;
}

.ranking-table td:nth-child(1) {
    text-align: center;
}

.grade {
    font-weight: bold;
    margin-right: 10px;
    width: 50px;
    text-align: center;
}

</style>