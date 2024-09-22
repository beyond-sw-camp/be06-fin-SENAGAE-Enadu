import { defineStore } from "pinia";
import axios from "axios";

const backend = "/api";

export const useUserStore = defineStore('user', {
    state: () => ({
        userId: null,
        isLoggedIn: false,
    }),
    actions: {
        async login(user) {
            try {
                const response = await axios.post(backend + "/login", user, {
                    headers: {
                        'Content-Type': 'application/json' 
                    } ,
                    withCredentials: true
                });

                if (!response || !response.data) {
                    throw new Error("Invalid response from server");
                }
        
                this.userId = response.data.userId;
                this.isLoggedIn = true;
                return true;
                
            } catch (error) {
                if (error.response) {
                    console.error("서버 응답 에러:", error.response.data);
                    console.error("상태 코드:", error.response.status);
                } else if (error.request) {
                    console.error("응답 없음:", error.request);
                } else {
                    console.error("에러 메시지:", error.message);
                }
                console.error("전체 에러 객체:", error);
                return false;
            }
        },
        async logout() {
            try {
                const response = await axios.post(backend + "/user/logout", {
                    headers: {
                        'Content-Type': 'application/json' 
                    } ,
                    withCredentials: true
                });
                if (!response || !response.data) {
                    throw new Error("Invalid response from server");
                }        
                this.userId = null;
                this.isLoggedIn = false;
                return true;
            } catch (error) {
                return false;
            }
        },
        setUserLoggedIn(userId) {
            this.isLoggedIn = true;
            this.userId = userId;
        },
        async signup(userInfo,selectedProfileFile) {
            try {
                console.log(userInfo,selectedProfileFile);
                // formData 객체 생성
                const formData = new FormData();
                const blob = new Blob([JSON.stringify(userInfo)], { type: 'application/json'});
                formData.append('userSignupReq', blob);
                formData.append('profileImg',selectedProfileFile);

                if(selectedProfileFile){
                    formData.append('profileImg',selectedProfileFile);
                }
            
                // 요청 보내기
                const response = await axios.post("http://localhost:8080/user/signup", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });
                
                
                if (response || response.data) {
                   return true;
                } else {
                    throw new Error("Invalid response from server");
                }
              
            }
            catch(error) {
                alert("회원가입 실패");
                console.error("회원가입 에러:", error.response ? error.response.data : error.message);
                return false;
            }   
        },
        async checkNickname() {
            try {
              // 닉네임이 입력되지 않았을 때의 처리
              if(!this.userInfo.nickname){
                alert("닉네임을 입력해주세요.");
                return;
              }
              // 서버에 닉네임 중복 여부 확인 요청
              const response = await axios.post("http://localhost:8080"+"/user/duplicate/nickname", {nickname: this.userInfo.nickname });
              // 서버로부터 받은 응답에 따라 처리
              if(response.data.code === 2024){
                alert("중복되는 닉네임입니다.")
              } else {
                alert("중복되지 않는 닉네임입니다.");
              }
            } catch (error) {
              console.error("닉네임 중복 확인 중 오류 발생:", error);
              alert("닉네임 확인 중 문제가 발생했습니다. 다시 시도해주세요");
            }
        },
        async checkEmail() {
                try {
                  // 이메일이 입력되지 않을때의 처리
                  if(!this.userInfo.email){
                    alert("이메일을 입력해주세요.");
                    return
                  }
                  // 서버에 이메일 중복 여부 확인 요청
                  const response = await axios.post("http://localhost:8080"+"/user/duplicate/email", {email: this.userInfo.email});
        
                 // 서버로부터 받은 응답에 따라 처리
                 if(response.data.isDuplicate) {
                  alert("중복되는 이메일입니다.");
                 } else {
                  alert("중복되지 않는 이메일입니다.");
                 }
                } catch(error){
                  console.error("이메일 중복 확인 중 오류 발생:", error);
                  this.alert("이메일 확인 중 문제가 발생했습니다. 다시 시도해주세요");
                }
            }

    },
});
         