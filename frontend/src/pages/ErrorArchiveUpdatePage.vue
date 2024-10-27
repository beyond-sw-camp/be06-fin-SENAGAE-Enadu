<template>
  <div class="custom-container">
    <div id="root">
      <div class="content-wrapper">
        <ErrorArchiveUpdateComponent
            :formData="formData"
            @submitUpdate="handleClick"/>
        <SubCategoryModal v-if="showSubModal" @close="showSubModal = false"/>
        <SuperCategoryModal v-if="showSuperModal" @close="showSuperModal = false"/>
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
  created() {
    this.checkUpdateAuthorization(this.$route.query.id);
  },
  methods: {
    async handleClick(updatedData) {
      try {
        await this.errorArchiveStore.updateErrorArchive(updatedData);
        console.log('Update successful');
      }
      catch (error) {
        console.error('Update error:', error);
      }
    },
    async checkUpdateAuthorization(id) {
      const errorArchiveStore = useErrorArchiveStore();
      const articleData = await errorArchiveStore.getErrorArchiveEditDetail(id);
      if (articleData === false) {
        this.$router.go(-1);
      }
    },
  }
};
</script>
