<template>
    <div class="full-page">
        <div class="container">
            <div class="message">이메일 인증이 완료되었습니다</div>
            <router-link to="/login" class="login-btn" >로그인 하러 가기</router-link>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'EmailVerifyPage',
    data() {
        return {
            message: '',
            isverified: false, // 인증 상태
        };
    },
    methods: {
        async verifyEmail() {
            const email = this.$route.query.email;
            const uuid = this.$route.query.uuid;

            try {
                const response = await axios.get(`http://localhost:8080/email/verify`, {
                    params: {
                        email,
                        uuid,
                    },
                });
                if (response.status === 302) {
                    this.message = '이메일 인증이 완료되었습니다';
                    this.isverified = true; // 인증 성공 상태로 변경
                    alert('이메일 인증에 성공했습니다!');
                }
            } catch (error) {
                this.message = '이메일 인증에 실패했습니다';
                alert('이메일 인증에 실패했습니다.');
            }
        },
    },
    mounted() {
        this.verifyEmail(); // 페이지 로드 시 이메일 인증 호출
    },
};
</script>

<style scoped>
html, body {
    height: 100%;
    margin: 0;
}

.full-page {
    display: flex;
    justify-content: center; /* 수평 중앙 정렬 */
    align-items: center; /* 수직 중앙 정렬 */
    height: 100vh; /* 전체 화면 높이 설정 */
    background-color: #f4f4f4;
}

.container {
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.08), 0 10px 10px rgba(0, 0, 0, 0.08);
    width: 25%; /* 너비를 4분의 1로 설정 */
    max-width: 400px; /* 최대 너비 */
    padding: 20px; /* 패딩 추가 */
    text-align: center; /* 텍스트 중앙 정렬 */
    min-height: 200px; /* 최소 높이 설정 */
}
.message {
    font-size: 24px;
    color: #333;
    margin-bottom: 20px;
}

.login-btn {
    display: inline-block;
    padding: 10px 20px;
    font-size: 16px;
    color: white;
    background-color: #737475;
    border: none;
    border-radius: 5px;
    text-decoration: none;
    cursor: pointer;
}

.login-btn:hover {
    background-color: #545455;
}
</style>
