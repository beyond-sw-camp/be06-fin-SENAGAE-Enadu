<template>
    <div class="body">
        <LoadingComponent v-if="isLoading" style="z-index: 5000;"/>
        <div class="container">
            <SignInComponent v-if="signIn" @login="handleLogin" @social="handleSocialLogin"/>
            <SignUpComponent v-else @signup="handleSignup" />
            <OverlayComponent :signIn="signIn" @toggleSignIn="toggleSignIn" />
        </div>
    </div>
</template>
<script>
    import OverlayComponent from "@/components/user/OverlayComponent.vue";
    import SignInComponent from "@/components/user/SignInComponent.vue";
    import SignUpComponent from "@/components/user/SignUpComponent.vue";
    import { useUserStore } from '@/store/useUserStore';
    import {mapStores} from "pinia";
    import LoadingComponent from "@/components/Common/LoadingComponent.vue";

    export default {
        name: "LoginPage",
        data() {
            return {
                isLoading: true,
                signIn: true,
            };
        },
        computed: {
            ...mapStores(useUserStore)
        },
        methods: {
            checkRoute() {
                const mode = this.$route.query.mode;
                if (mode === 'login') {
                    this.signIn = true;
                } else if (mode === 'signup'){
                    this.signIn = false;
                } else {
                    this.signIn = true;
                }
                this.isLoading = false;
            },
            toggleSignIn(value) {
                console.log("toggleSignIn 호출됨, value:", value);
                this.signIn = value;
            },
            async handleLogin(user) {
                this.isLoading = true;
                try {
                    const loginSuccess = await this.userStore.login(user);
                    if (loginSuccess) {
                        this.$router.push('/');
                    } else {
                        alert('로그인 실패');
                    }
                } catch (error) {
                    console.error('로그인 에러:', error);
                    alert('로그인 중 오류가 발생했습니다.');
                } finally {
                    this.isLoading = false;
                }
            },
            async handleSignup(userInfo, selectedProfileFile) {
                this.isLoading = true;
                try {
                    const signupSuccess = await this.userStore.signup(userInfo, selectedProfileFile);
                    if (signupSuccess) {
                        alert('회원가입 성공');
                        this.$router.push('/login?mode=login');
                    } else {
                        alert('회원가입 실패');
                    }
                } catch (error) {
                    console.error('회원가입 에러:', error);
                    alert('회원가입 중 오류가 발생했습니다.');
                } finally {
                    this.isLoading = false;
                }
            },
            async handleSocialLogin(social) {
                this.isLoading = true;
                try {
                    await this.userStore.oauthLogin(social);
                } catch (error) {
                    console.error('로그인 에러:', error);
                } finally {
                    this.isLoading = false;
                }
            }
        },
        created() {
            this.checkRoute();
        },
        components: {
            LoadingComponent,
            OverlayComponent,
            SignInComponent,
            SignUpComponent,
        },
    }
</script>

<style scoped>
.body {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    height: 100vh;
    background: #f6f5f7;
}
.container {
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.08), 0 10px 10px rgba(0, 0, 0, 0.08);
    position: relative;
    overflow: hidden;
    width: 800px;
    max-width: 100%;
    min-height: 700px;
}
</style>