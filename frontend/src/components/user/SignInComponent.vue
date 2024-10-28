<template>
  <div class="sign-in-container" :class="{ active: signIn }">
    <form @submit.prevent="submit">
      <h1 class="title">로그인</h1>
      <input type="email" placeholder="이메일" v-model="user.email"/>
      <input type="password" placeholder="비밀번호" v-model="user.password"/>
      <button type="submit">로그인</button>
      <img class="github" alt="깃허브로 로그인하기"
           src="https://dayun2024-s3.s3.ap-northeast-2.amazonaws.com/IMAGE/2024/09/14/dfa57956-15f7-44cc-afd6-b1c7ce31c0b2"
           @click="githubLogin">
    </form>
  </div>
</template>

<script>
export default {
  name: "SignInComponent",
  props: {
    signIn: Boolean,
  },
  data() {
    return {
      user: {
        email: "",
        password: "",
      }
    };
  },
  methods: {
    isValidEmail(email) {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 이메일 정규식
      return emailRegex.test(email);
    },
    submit() {
      if (!this.user.email || !this.user.password) {
        alert('이메일과 비밀번호를 입력해주세요.');
        return;
      } else if (!this.isValidEmail(this.user.email)) {
        alert('올바른 이메일 형식이 아닙니다.');
        return;
      }
      this.$emit('login', this.user);
    },
    githubLogin() {
      this.$emit('social', "github");
    }
  }
};
</script>
<style scoped>
.sign-in-container {
  position: absolute;
  top: 0;
  height: 100%;
  transition: all 0.6s ease-in-out;
  left: 0;
  width: 50%;
  z-index: 2;
}

.sign-in-container.active {
  transform: translateX(100%);
}

.title {
  font-size: 25px;
  font-weight: 600;
  margin: 10px;
  margin-bottom: 15px;
}

form {
  background-color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 50px;
  height: 100%;
  text-align: center;
}

input {
  background-color: #eee;
  border: none;
  padding: 12px 15px;
  margin-bottom: 20px;
  width: 90%;
  box-shadow: 4px 4px 8px 0 rgba(82, 82, 82, 0.25);
}

input :focus {
  border: 1px solid var(--main-color)
}

button {
  border-radius: 20px;
  border: 1px solid var(--main-color);
  background-color: var(--main-color);
  color: #ffffff;
  font-size: 12px;
  font-weight: bold;
  padding: 12px 45px;
  filter: drop-shadow(4px 4px 8px rgba(0, 0, 0, 0.08));
  letter-spacing: 1px;
  text-transform: uppercase;
  transition: transform 80ms ease-in;
}

button:active {
  transform: scale(0.95);
}

button:focus {
  outline: none;
}

.github {
  width: 70%;
  margin-top: 10px;
  padding: 10px;
  cursor: pointer;
}
</style>