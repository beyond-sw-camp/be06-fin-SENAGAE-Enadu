<template>
  <TagComponent :tagTitle="'User Log'" :tagSubTitle="'작성 내역'"/>
  <div class="custom-container" style="margin-top: 0; padding-top: 0;">
        <UserLogComponent />
    </div>
</template>

<script>

import UserLogComponent from "@/components/Mypage/Log/UserLogComponent.vue";
import TagComponent from "@/components/Common/TagComponent.vue";

export default {
    name: "UserLogPage",
    components: {TagComponent, UserLogComponent},
}
</script>

<style scoped>
</style>