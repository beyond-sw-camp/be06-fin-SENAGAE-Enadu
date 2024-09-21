<template>
  <div class="custom-container">
    <ErrorArchiveDetailComponent :errorId="errorId"/> <!-- props로 전달 -->
  </div>
</template>


<script>
import ErrorArchiveDetailComponent from "@/components/errorarchive/ErrorArchiveDetailComponent.vue";
import { ref, onMounted } from 'vue'; 
import { useRoute } from 'vue-router';
import axios from 'axios';

export default {
  name: "ErrorArchiveDetailPage",
  components: {
    ErrorArchiveDetailComponent
  },
  setup() {
    const route = useRoute(); // 현재 라우트 정보 가져오기
    const errorId = route.query.id; // 쿼리 파라미터에서 id 가져오기
    const errorData = ref(null); // 상세 데이터를 저장할 ref
    const isLoading = ref(true); // 로딩 상태

    onMounted(()=> {
      if (errorId){
        fetchErrorArchiveDetail(errorId);
      } else {
        console.log("error id is not provided.");
        isLoading.value = false;
      }

    });
    const fetchErrorArchiveDetail = async (id) => {
      try {
        const response = await axios.get(`api/erorarchive/detail?id=${id}`); // api호출
        errorData.value = response.data // 응답 데이터 저장
      } catch (error) {
        console.log("error fetching errorarchive detail:", error);
      } finally {
        isLoading.value = false; // 로딩 완료
      }
    };
    return {
      errorData,
      isLoading
    };
  }
  
}
</script>

<style scoped>

</style>