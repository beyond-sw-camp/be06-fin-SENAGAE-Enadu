<template>
  <ul class="status">
    <div style="margin-left: 22px; display: flex">
      <li class="e-status" :class="{ active: currentStatus === 'ALL' }" data-status="ALL">
        <button class="ac-button is-md is-text tab-button" @click="setStatus('ALL')">전 체</button>
      </li>
      <li class="e-status" :class="{ active: currentStatus === 'RESOLVED' }" data-status="RESOLVED">
        <button class="ac-button is-md is-text tab-button" @click="setStatus('RESOLVED')">해결됨</button>
      </li>
      <li class="e-status" :class="{ active: currentStatus === 'UNSOLVED' }" data-status="UNSOLVED">
        <button class="ac-button is-md is-text tab-button" @click="setStatus('UNSOLVED')">미해결</button>
      </li>
    </div>
  </ul>
</template>

<script>
export default {
  name: "QnaResolvedComponent",
  data() {
    return {
      isLoading: true,
      currentStatus: 'ALL'
    };
  },
  methods: {
    setStatus(status) {
      this.isLoading = false;
      this.currentStatus = status;
      this.$emit("changeResolved", this.currentStatus);
    }
  }
}
</script>

<style scoped>
.status {
  margin-top: 20px;
  display: flex;
  width: 205px;
  border-bottom: 2px solid #E0E0E0;
  list-style: none;
  padding: 0;
  justify-content: flex-start;
}

.status li {
  margin-right: 20px;
}

.tab-button {
  background: none;
  border: none;
  padding: 5px 0;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  color: #C0C0C0; /* Default gray color */
  transition: color 0.3s;
}

/* Active tab styling */
.e-status.active .tab-button {
  border-bottom: 2px solid black;
  color: black; /* Change text color to black */
}

/* Hover effect on inactive tabs */
.tab-button:hover {
  color: #808080;
}
</style>