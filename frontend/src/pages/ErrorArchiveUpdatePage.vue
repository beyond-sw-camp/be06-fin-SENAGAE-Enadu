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
import { useUserStore } from "@/store/useUserStore";

export default {
  name: "ErrorArchiveUpdatePage",
  data() {
    return {
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
  components: {
    ErrorArchiveUpdateComponent,
    SubCategoryModal,
    SuperCategoryModal
  },
  computed: {
    ...mapStores(useErrorArchiveStore, useUserStore)
  },
  async beforeRouteEnter(to, from, next) {
    const userStore = useUserStore();
    const errorArchiveStore = useErrorArchiveStore();
    const { id } = to.query;
    const loggedInUserId = userStore.userId;

    try {
      const articleData = await errorArchiveStore.getErrorArchiveDetail(id);
      console.log("API Response:", articleData); // 응답 확인

      if (articleData) {
        if (articleData.authorId !== loggedInUserId) {
          alert('수정 권한이 없습니다. 목록 페이지로 이동합니다.');
          next('/errorarchive/list'); // 권한 없음
        } else {
          next(vm => {
            vm.formData = {
              title: articleData.title,
              content: articleData.content,
              superCategory: articleData.superCategory,
              subCategory: articleData.subCategory,
            };

          });
        }
      } else {
        alert('글 데이터를 찾을 수 없습니다. 목록 페이지로 이동합니다.');
        next('/errorarchive/list'); // 글 데이터 없음
      }
    } catch (error) {
      console.error("글 데이터를 가져오는 중 오류 발생:", error);
      next('/errorarchive/list'); // 오류 발생 시 목록 페이지로 이동
    }
  },
  methods: {
    async handleClick(updatedData) {
      try {
        await this.errorArchiveStore.updateErrorArchive(updatedData);
        console.log('Update successful');
      } catch (error) {
        console.error('Update error:', error);
      }
    },
  },
};
</script>
