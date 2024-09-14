<template>
  <tr>
    <td><strong>{{ pointRanking.rank }}</strong></td>
    <td>
      <i class="fas fa-trophy"></i>
      <img :src="pointRanking.profileImg" alt="user2" class="user-icon">
      <span class="username">{{ pointRanking.nickname }}</span>
    </td>
    <td class="rating">{{ pointRanking.point }}</td>
  </tr>
</template>


<script>
export default {
  name: "PointRankingUserComponent",
  props: ["pointRanking"]
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
}

.username {
  font-weight: bold;
}

.rating {
  text-align: right;
  font-weight: bold;
  color: #1e88e5;
}

</style>