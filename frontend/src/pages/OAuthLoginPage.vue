<template>
    <div>
    </div>
</template>

<script>
import { useUserStore } from '@/store/useUserStore';
import {mapStores} from "pinia";

export default {
    name: 'OAuthLoginPage',
    data() {
        return {
            userId: null 
        };
    },
    computed: {
        ...mapStores(useUserStore)
    },
    created() {
        this.loginSuccess();
    },
    methods: {
        loginSuccess() {
            this.userId = this.$route.query.userId || null;
            if (this.userId) {
                this.userStore.setUserLoggedIn(Number(this.userId));
                alert('로그인에 성공했습니다.')
                this.$router.push('/');
            } else {
                alert('소셜 로그인에 실패했습니다.');
                this.$router.push('/login?mode=login');
            }
        },
    }
}
</script>

<style scoped>
</style>