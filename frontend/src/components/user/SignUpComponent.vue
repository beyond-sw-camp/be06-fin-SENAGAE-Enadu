<template>
  <div class="sign-up-container" :class="{ active: !signIn }">
    <form @submit.prevent>
      <h1 class="title">회원가입</h1>
      <p class="explanation">아이디(E-mail)</p>

      <div class="half-form">
        <input type="email" placeholder="sample@gmail.com" v-model="email" />
        <button type="button" class="check-button" @click="checkEmail">중복 확인</button>
      </div>
      
      <p class="explanation">비밀번호</p>
      <input type="password" placeholder="비밀번호를 8자 이상 입력해주세요" v-model="password" />

      <p class="explanation">
        비밀번호 확인
        <span v-if="password && confirmPassword">
          {{ password === confirmPassword ? ' (일치)' : ' (불일치)' }}
        </span>
      </p>
      <input type="password" placeholder="비밀번호를 한 번 더 입력해주세요" v-model="confirmPassword" />

      <p class="explanation">이름</p>
      <input type="text" placeholder="홍길동" v-model="name" />

      <p class="explanation">닉네임</p>
      <div class="half-form">
        <input type="text" placeholder="닉네임" v-model="nickname" />
        <button type="button" class="check-button" @click="checkNickname">중복 확인</button>
      </div>

      <p class="explanation">프로필 이미지</p>
      <div class="half-form">
        <div class="profile-preview">
          <img :src="profileImage" alt="" class="profile-image" />
        </div>
        <input type="file" @change="handleProfileImageUpload" accept="image/*" />
      </div>

      <button type="button" @click="handleSignup">회원가입</button>
    </form>
  </div>
</template>

<script>
  export default {
    name: "SignUpComponent",
    props: {
      signIn: Boolean,
    },
    data() {
      return {
        email: "",
        password: "",
        confirmPassword: "",
        name: "",
        nickname: "",
        profileImage: "http://www.pngarts.com/files/10/Default-Profile-Picture-PNG-Download-Image.png",
      };
    },
    methods: {
      handleSignup() {
        if (this.password !== this.confirmPassword) {
          alert("비밀번호가 일치하지 않습니다.");
          return;
        }
        alert("회원가입 완료");
        this.$emit("toggleSignIn", true);
      },
      checkEmail() {
        alert("이메일 중복 확인을 실행합니다.");
      },
      checkNickname() {
        alert("닉네임 중복 확인을 실행합니다.");
      },
      handleProfileImageUpload(event) {
        const file = event.target.files[0];
        if (file) {
          this.profileImage = URL.createObjectURL(file);
        }
      },
    },
  };
</script>

<style scoped>
.sign-up-container {
  position: absolute;
  top: 0;
  height: 100%;
  transition: all 0.6s ease-in-out;
  left: 0;
  width: 50%;
  opacity: 0;
  z-index: 1;
}

.sign-up-container.active {
  transform: translateX(100%);
  opacity: 1;
  z-index: 5;
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

.title {
  font-size: 25px;
  font-weight: 600;
  margin: 10px;
  margin-bottom: 15px;
}

.explanation {
  color: rgba(0, 0, 0, 0.80);
  font-size: 15px;
  font-style: normal;
  font-weight: 500;
  line-height: normal;
  width: 100%;
  height: 22px;
  margin: 0;
  text-align: left;
}

input {
  background-color: #eee;
  border: none;
  padding: 12px 15px;
  margin-bottom: 20px;
  width: 100%;
  box-shadow: 4px 4px 8px 0px rgba(82, 82, 82, 0.25);
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

.check-button {
  margin: 10px 0 25px 15px;
  padding: 12px 20px;
  font-size: 10px;
  font-weight: bold;
  width: 120px;
  height: 40px;
  background-color: #fff;
  color: var(--main-color);
  border-radius: 20px;
  border: 1px solid var(--main-color);
  cursor: pointer;
}

.half-form {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

input[type="file"] {
  background-color: #fff;
  border: 1px solid #ccc;
  padding: 8px;
  width: 50%;
  margin-bottom: 20px;
}

.profile-preview {
  margin: 20px 0;
}

.profile-image {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}
</style>