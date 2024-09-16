<template>
  <div class="card-list">
    <ErrorArchiveCardComponent
      v-for="errorarchiveCard in errorarchiveCards"
      :key="errorarchiveCard.id"
      :errorarchiveCard="errorarchiveCard"
    />
  </div>
</template>

<script>
import { useErrorArchiveStore } from '@/store/useErrorArchiveStore';
import ErrorArchiveCardComponent from '@/components/errorarchive/ErrorArchiveCardComponent.vue'

export default {
  name: 'ErrorArchiveListPage',
  data() {
    return {
      errorarchiveCards: [],  // 데이터는 여기에서 가져올 수 있도록 합니다.
    };
  },
  async mounted() {
    // Pinia 스토어 인스턴스를 가져옵니다.
    const store = useErrorArchiveStore();

    // 초기 정렬 기준과 페이지 번호를 설정합니다.
    const sort = 'date';  // 예시로 정렬 기준을 날짜로 설정
    const page = 1;       // 첫 페이지부터 시작

    try {
      // 스토어의 메소드 호출
      await store.getErrorArchiveList(sort, page);
      this.errorarchiveCards = store.errorarchiveCards; // 스토어에서 가져온 데이터로 업데이트
    } catch (error) {
      console.error("Error fetching errorarchive list:", error);
    }
  },
  components: {
    ErrorArchiveCardComponent,
  },
};
</script>

<style scoped>
.card-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
</style>
