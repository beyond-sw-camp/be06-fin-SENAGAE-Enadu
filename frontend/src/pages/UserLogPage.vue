<template>
    <div class="custom-container">
        <div class="qna-top">
            <p id="main-title">User Log</p>
            <p id="sub-title">작성 내역</p>
        </div>
        <UserLogComponent />
    </div>
</template>

<script>

import UserLogComponent from "@/components/Mypage/Log/UserLogComponent.vue";

export default {
    name: "UserLogPage",
    components: {UserLogComponent},
}
</script>

<style scoped>
#main-title {
    text-align: center;
    font-size: 35px;
}

#sub-title {
    text-align: center;
    font-size: 25px;
}
</style>