<template>
  <div class="tag-box">
    <div class="tag-sub-box">
      <div class="tag-title">{{ tagTitle }}</div>
      <div class="tag-sub-title">{{ tagSubTitle }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "TagComponent",
  props: ["tagTitle", "tagSubTitle"]
}
</script>

<style scoped>
.tag-box {
  width: 100%;
  margin-bottom: 40px;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  display: inline-flex
}

.tag-sub-box {
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  gap: 8px;
  display: flex
}

.tag-title {
  align-self: stretch;
  text-align: center;
  color: #1E1E1E;
  font-size: 40px;
  font-family: Inter;
  font-weight: 700;
  word-wrap: break-word
}

.tag-sub-title {
  align-self: stretch;
  text-align: center;
  color: #757575;
  font-size: 25px;
  font-family: Inter;
  font-weight: 400;
  word-wrap: break-word
}
</style>