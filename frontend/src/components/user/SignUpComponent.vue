<template>
  <div class="sign-up-container" :class="{ active: !signIn }">
    <form @submit.prevent>
      <h1 class="title">회원가입</h1>
      <p class="explanation">아이디(E-mail)</p>

      <div class="half-form">
        <input type="email" placeholder="sample@gmail.com" v-model="userInfo.email" />
        <button type="button" class="check-button" @click="checkEmail">중복 확인</button>
      </div>
      
      <p class="explanation">비밀번호</p>
      <input type="password" placeholder="비밀번호를 8자 이상 입력해주세요" v-model="userInfo.password" />

      <p class="explanation">
        비밀번호 확인
        <span v-if="userInfo.password && userInfo.confirmPassword">
          {{ userInfo.password === userInfo.confirmPassword ? ' (일치)' : ' (불일치)' }}
        </span>
      </p>
      <input type="password" placeholder="비밀번호를 한 번 더 입력해주세요" v-model="userInfo.confirmPassword" />


      <p class="explanation">닉네임</p>
      <div class="half-form">
        <input type="text" placeholder="닉네임" v-model="userInfo.nickname" />
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
import axios from 'axios';
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
      };
    },
    methods: {
      signup() {
        if (this.userInfo.password !== this.userInfo.confirmPassword) {
          alert("비밀번호가 일치하지 않습니다.");
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
    try {
        const response = await axios.get("http://localhost:8080/user/duplicate/email", {
            params: {
                email: this.userInfo.email,
            },
        });
        // BaseResponse에서 data를 꺼내서 처리합니다.
        if (response.data.result === false) {
            alert("중복된 이메일입니다.");
        } else {
            alert("사용 가능한 이메일입니다.");
        }
    } catch (error) {
        alert("이메일 중복 확인 실패");
        console.error("이메일 중복 확인 에러:", error.response ? error.response.data : error.message);
    }
},
      async checkNickname() {
      try {
        const response = await axios.get("http://localhost:8080/user/duplicate/nickname", {
          params: {
            nickname: this.userInfo.nickname,
          },
        });

        if (response.data.result === false) {
          alert("중복된 닉네임입니다.");
        } else {
          alert("사용 가능한 닉네임입니다.");
        }
      } catch (error) {
        alert("닉네임 중복 확인 실패");
        console.error("닉네임 중복 확인 에러:", error);
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