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
                console.log("로그인 성공, 사용자 ID:", this.userId);
                return true;

            } catch (error) {
                if (error.response) {
                    console.error("서버 응답 에러:", error.response.data);
                } else if (error.request) {
                    console.error("응답 없음:", error.request);
                } else {
                    console.error("에러 메시지:", error.message);
                }
                console.error("전체 에러 객체:", error);
                return false;
            }
        },
        async oauthLogin(social) {
            window.location.href = "/social/oauth2/authorization/" + social;
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
                const response = await axios.post(backend + "/user/signup", formData, {
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
              const response = await axios.get(backend +"/user/duplicate/nickname", { params : {nickname: nickname }
              });
              // 서버로부터 받은 응답에 따라 처리
              if(response.data.result === false){
                alert("중복되는 닉네임입니다.")
              } else {
                alert("사용 가능한 닉네임입니다.");
              }
              return response.data.result;
            } catch (error) {
              console.error("닉네임 중복 확인 중 오류 발생:", error);
              alert("닉네임 확인 중 문제가 발생했습니다. 다시 시도해주세요");
            }
        },
        async checkEmail(email) {
            // 서버에 이메일 중복 여부 확인 요청
            const response = await axios.get(backend + "/user/duplicate/email", {params: {email: email}}
            );
            // 서버로부터 받은 응답에 따라 처리
            if (response.data.result === true) {
                alert("사용 가능한 이메일입니다.");
            } else {
                alert("중복되는 이메일입니다.");
            }
            return response.data.result;
        },
        async verifyEmail(email, uuid) {
            try {
                const response = await axios.post(backend + "/email/verify", {
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
        },
        async quitAccount(password) {
            try {
                const response = await axios.patch(backend +   '/user/quit',
                    { password },
                    { withCredentials: true }
                );
                if (response.data.code === 1000 && response.data.isSuccess) {
                    this.userId = null;
                    this.isLoggedIn = false;
                    return true;
                } else if (response.data.code === 2041) {
                    alert(response.data.message);
                } else {
                    alert(response.data.message || '회원 탈퇴에 실패하였습니다.');
                    return false;
                }
            } catch (error) {
                alert('회원 탈퇴 중 오류가 발생했습니다.');
                console.error(error);
                return false;
            }
        },
        async socialQuit() {
            try {
                const response = await axios.patch('/social/user/oauth/quit',
                    {withCredentials: true}
                );
                if (response.data.code === 1000 && response.data.isSuccess) {
                    this.userId = null;
                    this.isLoggedIn = false;
                    return true;
                } else {
                    alert(response.data.message || '회원 탈퇴에 실패하였습니다.');
                    return false;
                }
            } catch (error) {
                alert('회원 탈퇴 중 오류가 발생했습니다.');
                console.error(error);
                return false;
            }
        }
    },
});
