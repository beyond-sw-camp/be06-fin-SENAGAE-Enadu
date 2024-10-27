<template>
  <TagComponent :tagTitle="'Mypage'" :tagSubTitle="subTitle"/>
  <div class="custom-container" style="margin-top: 0;">
        <router-view @sub-title="handleSubTitleEvent"/>
    </div>
</template>

<script>

import TagComponent from "@/components/Common/TagComponent.vue";

export default {
    name: "MypagePage",
  components: {TagComponent},
    data() {
        return {
            subTitle: ""
        }
    },
    methods: {
        handleSubTitleEvent(subTitle) {
            this.subTitle = subTitle;
        }
    }
}
</script>

<style scoped>
</style>