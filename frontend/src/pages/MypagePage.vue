<template>
    <div class="custom-container">
        <div class="qna-top">
            <p id="main-title">Mypage</p>
            <p id="sub-title">{{ subTitle }}</p>
        </div>
        <router-view @sub-title="handleSubTitleEvent"/>
    </div>
</template>

<script>

export default {
    name: "MypagePage",
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
#main-title {
    text-align: center;
    font-size: 35px;
}

#sub-title {
    text-align: center;
    font-size: 25px;
}
</style>