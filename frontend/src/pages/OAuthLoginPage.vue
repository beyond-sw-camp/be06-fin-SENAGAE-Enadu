<template>
    <div>
    </div>
</template>

<script>
import { useUserStore } from '@/store/useUserStore';
import { useRouter, useRoute } from 'vue-router';

export default {
    name: 'OAuthLoginPage',
    data() {
        return {
            userId: null 
        };
    },
    created() {
        this.loginSuccess();
    },
    methods: {
        loginSuccess() {
            const userStore = useUserStore();
            const router = useRouter();
            const route = useRoute();

            this.userId = route.query.userId;

            if (this.userId) {
                userStore.setUserLoggedIn(this.userId);
                router.push('/');
            } else {
                console.error('userId가 쿼리 파라미터에 없습니다.');
            }
        }
    }
}
</script>

<style scoped>
</style>