<template>
  <button id="adoptedTag">채택 완료</button>
</template>

<script>
export default {
  name: "AdoptedTagComponent.vue"
}
</script>

<style scoped>
#adoptedTag {
  --green: #1BFD9C;
  height: min-content;
  font-size: 17px;
  font-weight: 900;
  text-align: center;
  letter-spacing: 0.01em;
  position: relative;
  font-family: inherit;
  border-radius: 0.6em;
  overflow: hidden;
  transition: all 0.3s;
  border: 2px solid #1bfd9c;
  background: linear-gradient(to right, #fff 1%, transparent 40%, transparent 60%, #fff 100%);
  color: #1bfd9c;
  box-shadow: inset 0 0 20px rgba(27, 253, 156, 0.4), 0 0 9px 3px rgba(27, 253, 156, 0.1);
  padding: 7px 17px;
}
</style>