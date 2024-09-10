<template>
    <div class="body">
        <div class="container">
            <SignInComponent v-if="signIn" @login="handleLogin" />
            <SignUpComponent v-else />
            <OverlayComponent :signIn="signIn" @toggleSignIn="toggleSignIn" />
            
        </div>
    </div>
</template>
<script>
    import OverlayComponent from "@/components/user/OverlayComponent.vue";
    import SignInComponent from "@/components/user/SignInComponent.vue";
    import SignUpComponent from "@/components/user/SignUpComponent.vue";
    import { useUserStore } from '@/store/useUserStore';

    export default {
        name: "LoginPage",
        data() {
            return {
                signIn: true,
            };
        },
        methods: {
            checkRoute() {
                const mode = this.$route.query.mode;
                if (mode === 'login') {
                    this.signIn = true;
                } else if (mode === 'signup' ){
                    this.signIn = false; 
                } else {
                    this.signIn = true;
                }
            },
            toggleSignIn(value) {
                console.log("toggleSignIn 호출됨, value:", value);
                this.signIn = value;
            },
            async handleLogin(user) {
                const userStore = useUserStore();
                const loginSuccess = await userStore.login(user);
                if (loginSuccess) {
                    this.$router.push('/');
                } else {
                    alert('로그인 실패');
                }
            }
        },
        created() {
            this.checkRoute();
        },
        components: {
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