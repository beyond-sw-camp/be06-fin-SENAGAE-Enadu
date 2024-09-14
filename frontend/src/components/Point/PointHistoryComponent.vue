<template>
  <div class="point-item">
    <div class="description">
      <p>{{ pointHistory.description }}</p>
      <span>{{ pointHistory.createdAt.split("T")[0] }}</span>
    </div>
    <div class="points-value" :class="pointHistory.state ? 'positive' : 'negative'">{{ pointHistory.state ? "":"-" }}{{pointHistory.point}}P</div>
  </div>
</template>

<script>
export default {
  name: "PointHistoryComponent",
  props: ["pointHistory"]
}
</script>


<style scoped>
.point-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #f0f0f0;
}

.point-item:last-child {
  border-bottom: none;
}

.description p {
  margin-bottom: 5px;
  font-size: 16px;
  color: #333;
}

.description span {
  font-size: 14px;
  color: #999;
}

.points-value {
  font-size: 18px;
  font-weight: bold;
}

.points-value.positive {
  color: #1e88e5;
}

.points-value.negative {
  color: #e53935;
}
</style>