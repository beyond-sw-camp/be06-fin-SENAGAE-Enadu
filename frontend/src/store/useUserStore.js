import { defineStore } from "pinia";
import axios from "axios";

const backend = "/api";

export const useUserStore = defineStore('user', {
    state: () => ({
        userId: null,
        isLoggedIn: false,
        userInfo: {},
    }),
    persist: {
        storage: sessionStorage,
        paths: ['userId', 'isLoggedIn'],
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
                this.userInfo = {};
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
                } catch(error){
                    console.error("이메일 중복 확인 중 오류 발생:", error);
                    alert("이메일 확인 중 문제가 발생했습니다.");
                }            
            },

        async fetchUserInfo() {
            try {
                const response = await axios.get(backend + "/user/info", {
                    withCredentials: true
                });
                if (!response || !response.data) {
                    throw new Error("Invalid response from server");
                }
                this.userInfo = response.data.result;
            } catch (error) {
                console.error("유저 정보 가져오기 에러:", error);
            }
        },
        async checkCheckNickname(nickname) {
            try {
                const response = await axios.get(backend + "/user/duplicate/nickname", {
                    params: { nickname }
                });
                if (!response || !response.data) {
                    throw new Error("Invalid response from server");
                }
                console.log(response.data.result);
                return response.data.result;
            } catch (error) {
                console.error("닉네임 중복 확인 중 오류 발생:", error);
            }
        },
        async updateNickname(nickname) {
            try {
                const response = await axios.patch(backend + "/user/nickname",
                    nickname,
                    { withCredentials: true }
                );
                if (!response || !response.data) {
                    throw new Error("Invalid response from server");
                }
                this.userInfo.nickname = nickname;
                return true;
            } catch (error) {
                console.error("닉네임 업데이트 중 오류 발생:", error);
                return false;
            }
        },
        async uploadProfileImage(formData) {
            try {
                const response = await axios.patch(backend + "/user/img", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                    withCredentials: true
                });
                console.log(response.data);
                if (!response || !response.data) {
                    throw new Error("Invalid response from server");
                }
                this.userInfo.profileImg = response.data.result;
                return true;
            } catch (error) {
                console.error("프로필 이미지 업로드 중 오류:", error);
                return false;
            }
        },
        async updatePassword(passwordData) {
            try {
                const response = await axios.patch(backend + '/user/password', passwordData,
                    {withCredentials: true});
                if (response.data.code === 1000) {
                    return true;
                } else if (response.data.code === 2041) {
                    window.alert(response.data.message);
                } else if (response.data.code === 2042) {
                    window.alert(response.data.message);
                } else {
                    throw new Error('비밀번호 변경 실패');
                }
            } catch (error) {
                console.error("비밀번호 변경 중 오류 발생:", error);
                alert("비밀번호 변경에 실패하였습니다.");
            }
        }
    },
});
