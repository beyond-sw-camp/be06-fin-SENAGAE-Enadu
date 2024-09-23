import { defineStore } from "pinia";
import axios from "axios";

const backend = "/api";

export const useUserStore = defineStore('user', {
    state: () => ({
        userId: null,
        isLoggedIn: false,
    }),
    persist: {
        storage: sessionStorage,
    },
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
        async checkNickname(nickname) {
            try {
              // 서버에 닉네임 중복 여부 확인 요청
              const response = await axios.get("http://localhost:8080"+"/user/duplicate/nickname", { params : {nickname: nickname }
              });
              // 서버로부터 받은 응답에 따라 처리
              if(response.data.result === false){
                alert("중복되는 닉네임입니다.")
              } else {
                alert("중복되지 않는 닉네임입니다.");
              }
            } catch (error) {
              console.error("닉네임 중복 확인 중 오류 발생:", error);
              alert("닉네임 확인 중 문제가 발생했습니다. 다시 시도해주세요");
            }
        },
        async checkEmail(email) {
            try {
                // 서버에 이메일 중복 여부 확인 요청
                const response = await axios.get("http://localhost:8080/user/duplicate/email", { params : {email: email} }
                );
                console.log(response);  // 응답 데이터 확인

                // 서버로부터 받은 응답에 따라 처리
                if(response.data.result == true) {
                    alert("사용 가능한 이메일입니다.");
                } else {
                    alert("중복되는 이메일입니다.");
                }
            },
            async verifyEmail(email, uuid) {
                try {
                    const response = await axios.post(`http://localhost:8080/email/verify`, {
                            email,
                            uuid,
                    });
                    
            
                    // 응답 코드와 성공 여부 확인
                    if (response.data.code === 1000 && response.data.isSuccess) {
                        alert('이메일 인증에 성공했습니다!');
                    } else {
                        alert(response.data.message || '이메일 인증에 실패했습니다.');
                    }
                } catch (error) {
                    console.error('이메일 인증 중 오류 발생:', error);
                    alert('이메일 인증에 실패했습니다.');
                }
            }
    },
});
