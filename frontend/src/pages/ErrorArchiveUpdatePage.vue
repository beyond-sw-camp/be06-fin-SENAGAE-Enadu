<template>
  <div class="custom-container">
    <div id="root">
      <div class="content-wrapper">
        <ErrorArchiveUpdateComponent 
          :formData="formData" 
          @submitUpdate="handleClick" />
        <SubCategoryModal v-if="showSubModal" @close="showSubModal = false" />
        <SuperCategoryModal v-if="showSuperModal" @close="showSuperModal = false" />
      </div>
    </div>
  </div>
</template>

<script>
import ErrorArchiveUpdateComponent from '@/components/errorarchive/ErrorArchiveUpdateComponent.vue';
import { mapStores } from "pinia";
import { useErrorArchiveStore } from "@/store/useErrorArchiveStore";
import SuperCategoryModal from '@/components/Category/SuperCategoryModal.vue';
import SubCategoryModal from '@/components/Category/SubCategoryModal.vue';

export default {
  name: "ErrorArchiveUpdatePage",
  data() {
    return {
      isLoading: true,
      formData: {
        title: "",
        content: "",
        superCategory: "",
        subCategory: "",
      },
      showSubModal: false,
      showSuperModal: false,
    };
  },
  created() {
    this.loadData();
  },
  components: {
    ErrorArchiveUpdateComponent,
    SubCategoryModal,
    SuperCategoryModal
  },
  computed: {
    ...mapStores(useErrorArchiveStore) // Pinia store 연결
  },
  methods: {
    async loadData() {
      // 데이터 로드 로직 추가
      const { title, content, superCategory, subCategory } = this.$route.query;
      this.formData.title = title || '';
      this.formData.content = content || '';
      this.formData.superCategory = superCategory || '';
      this.formData.subCategory = subCategory || '';
    },
    async handleClick(updatedData) {
      console.log('Received data: ', updatedData);
      try {
        // store의 updateErrorArchive 메서드에 updatedData를 전달
        await this.errorArchiveStore.updateErrorArchive(updatedData);
        console.log('Update successful');
        // 성공적으로 업데이트된 후 필요한 추가 작업
      } catch (error) {
        console.error('Update error:', error);
      }
    },
  },
};
</script>
