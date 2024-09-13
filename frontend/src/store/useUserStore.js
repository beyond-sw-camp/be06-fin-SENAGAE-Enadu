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
    }
});