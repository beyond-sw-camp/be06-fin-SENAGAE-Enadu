<template>
  <div class="sign-up-container" :class="{ active: !signIn }">
    <form @submit.prevent>
      <h1 class="title">회원가입</h1>
      <p class="explanation">아이디(E-mail)</p>

      <div class="half-form">
        <input type="email" placeholder="sample@gmail.com" v-model="userInfo.email" @input="validateEmail" />
        <button type="button" class="check-button" @click="checkEmail">중복 확인</button>
      </div>
      <p class="explanation">비밀번호</p>
      <input type="password" placeholder="비밀번호를 8자 이상 입력해주세요" v-model="userInfo.password" @input="validatePassword" />

      <p class="explanation">
        비밀번호 확인
        <span v-if="userInfo.password && userInfo.confirmPassword">
          {{ userInfo.password === userInfo.confirmPassword ? ' (일치)' : ' (불일치)' }}
        </span>
      </p>
      <input type="password" placeholder="비밀번호를 한 번 더 입력해주세요" v-model="userInfo.confirmPassword" />


      <p class="explanation">닉네임</p>
      <div class="half-form">
        <input type="text" placeholder="닉네임" v-model="userInfo.nickname" @input="validateNickname"/>
        <button type="button" class="check-button" @click="checkNickname">중복 확인</button>
      </div>
      <p class="explanation">프로필 이미지</p>
      <div class="half-form">
        <div class="profile-preview">
          <img :src="imgUrl" alt="" class="profile-image" />
        </div>
        <input type="file" @change="handleProfileImageUpload" accept="image/*" />
      </div>

      <button type="button" @click="signup">회원가입</button>
    </form>
  </div>
</template>

<script>
import {useUserStore} from '@/store/useUserStore';

export default {
    name: "SignUpComponent",
    props: {
      signIn: Boolean,
    },
    data() {
      return {
        userInfo: {
          email: "",
          password: "",
          nickname: "",
          confirmPassword: "",
        },
        imgUrl: "https://dayun2024-s3.s3.ap-northeast-2.amazonaws.com/IMAGE/2024/09/11/0d7ca962-ccee-4fbb-9b5d-f5deec5808c6",
        selectedProfileFile: null,
        emailAvailable: false,
        nicknameAvailable: false,
      };
    },
    methods: {
        isValidEmail(email) {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 이메일 정규식
            return emailRegex.test(email);
        },
        signup() {
            if (!this.userInfo.email || !this.userInfo.email.trim()) {
                alert("이메일을 입력해주세요.");
                return;
            }
            if (!this.isValidEmail(this.userInfo.email) || this.userInfo.email.includes(" ")) {
              alert('올바른 이메일 형식이 아닙니다.');
              this.userInfo.email = this.userInfo.email.trim();
              return;
            }
            if (!this.userInfo.password || !this.userInfo.password.trim()) {
                alert("비밀번호를 입력해주세요.");
                return;
            }
            if (this.userInfo.password.length < 8) {
              alert("비밀번호는 8자 이상이어야 합니다.");
              this.userInfo.password = "";
            }
            if (!this.userInfo.confirmPassword || !this.userInfo.confirmPassword.trim()) {
                alert("비밀번호 확인을 입력해주세요.");
                return;
            }
            if (this.userInfo.password.includes(" ") || this.userInfo.confirmPassword.includes(" ")) {
              alert("비밀번호에 공백이 포함되었습니다.");
              this.userInfo.password = this.userInfo.password.trim();
              this.userInfo.confirmPassword = this.userInfo.confirmPassword.trim();
            }
            if (!this.userInfo.nickname || !this.userInfo.nickname.trim()) {
                alert("닉네임을 입력해주세요.");
                return;
            }
            if (this.userInfo.password !== this.userInfo.confirmPassword) {
                alert("비밀번호가 일치하지 않습니다.");
                return;
            }
            if (!this.emailAvailable) {
                alert("이메일 중복 확인을 해주세요.");
                return;
            }
            if (!this.nicknameAvailable) {
                alert("닉네임 중복 확인을 해주세요.");
                return;
            }
            this.$emit('signup', this.userInfo, this.selectedProfileFile || null);
        },
      handleProfileImageUpload(event) {
        const file = event.target.files[0];
        if (file) {
          this.imgUrl = URL.createObjectURL(file);
          this.selectedProfileFile=file;
        }
      },
      async checkEmail() {
        const userStore = useUserStore();
          this.emailAvailable = await userStore.checkEmail(this.userInfo.email);
      },
      async checkNickname() {
        const userStore = useUserStore();
          this.nicknameAvailable = await userStore.checkNickname(this.userInfo.nickname);
    },
    watch: {
        'userInfo.email'(newValue) {
            if (newValue) {
                this.emailAvailable = false;
            }
        },
        'userInfo.nickname'(newValue) {
            if (newValue) {
                this.nicknameAvailable = false;
            }
        }
   },
   validateEmail() {
      if (this.userInfo.email.includes(" ")) {
        alert("공백이 포함되었습니다.");
        this.userInfo.email = this.userInfo.email.trim();
      }
    },
    validateNickname() {
      if (this.userInfo.nickname.includes(" ")) {
        alert("공백이 포함되었습니다.");
        this.userInfo.nickname = this.userInfo.nickname.trim();
      }
    },
    validatePassword() {
      if (this.userInfo.password.includes(" ")) {
        alert("공백이 포함되었습니다.");
        this.userInfo.password = this.userInfo.password.trim();
      }
    },
  }
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
  box-shadow: 4px 4px 8px 0 rgba(82, 82, 82, 0.25);
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
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
</style>