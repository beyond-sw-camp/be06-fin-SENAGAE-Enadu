import { defineStore } from "pinia";
import axios from "axios";

const backend = "/api";

axios.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            console.log("401 에러 처리");
        } else if (error.response && error.response.status === 405) {
            console.log("405 에러 처리");
        } else if (error.response && error.response.status === 304) {
            console.log("304 에러 처리");
        } else {
            console.log("다른 에러 처리:", error.response ? error.response.status : "응답 없음");
        }
        return Promise.reject(error);
    }
);
export const useWikiStore = defineStore("wiki", {
    state: () => ({
        wikiCards: [], 
        wikiRegisterReq: {
            title: '',
            categoryId: '',
            content: '',
        },
        userDetails: null, 
        wikiDetail: null, 
        isLoggedIn: false, 
    }),

    actions: {

        // 위키 등록 기능
        async registerWiki(thumbnail) {
            try {
                console.log("위키 등록 요청 데이터:", this.wikiRegisterReq);

                const formData = new FormData();
                const jsonBlob = new Blob([JSON.stringify(this.wikiRegisterReq)], { type: "application/json" });
                formData.append("wikiRegisterReq", jsonBlob);

                if (thumbnail) {
                    formData.append("thumbnail", thumbnail);
                }

                const response = await axios.post(backend + "/wiki", formData, {
                    withCredentials: true,
                    headers: { "Content-Type": "multipart/form-data" }
                });

                if (response && response.data) {
                    if (response.data.isSuccess) {
                        return true;
                    } else {
                        throw new Error(response.data.message || "서버 응답 오류");
                    }
                } else {
                    throw new Error("응답 데이터가 없습니다.");
                }
            } catch (error) {
                console.error("위키 등록 중 오류 발생:", error);
                throw error;
            }
        },

        // 위키 수정 기능
        async updateWiki(id, updatedContent, updatedThumbnail) {
            try {
                const formData = new FormData();
                const updateReq = {
                    id: id,
                    content: updatedContent 
                };
        
                const jsonBlob = new Blob([JSON.stringify(updateReq)], { type: "application/json" });
                formData.append("getWikiUpdateReq", jsonBlob);
    
                if (updatedThumbnail) {
                    formData.append("thumbnail", updatedThumbnail);
                }
        
                const response = await axios.patch(backend + "/wiki", formData, {
                    withCredentials: true,
                    headers: { "Content-Type": "multipart/form-data" }
                });
        
                if (response && response.data.isSuccess) {
                    console.log("위키 수정 성공:", response.data);
                    return true;
                } else {
                    throw new Error("수정 실패");
                }
            } catch (error) {
                console.error("위키 수정 중 오류 발생:", error);
                throw error;
            }
        },

        // 위키 상세 조회
        async fetchWikiDetail(id) {
            try {
                const response = await axios.get(`${backend}/wiki/detail`, {
                    params: { id },
                    withCredentials: true,
                });

                if (response && response.data) {
                    console.log('Wiki Detail Response:', response.data); 
                    this.wikiDetail = response.data.result; 

                    if (response.data.result.userGrade) {
                        console.log("사용자 정보 확인:", response.data.result.userGrade);
                        this.userDetails = { grade: response.data.result.userGrade };
                        this.isLoggedIn = true;  // 로그인 상태 true
                    } else {
                        this.userDetails = null; // 로그인이 안된 경우 사용자 정보 초기화
                        this.isLoggedIn = false; // 로그인 상태 false
                    }

                } else {
                    throw new Error("위키 상세 조회 실패");
                }
            } catch (error) {
                console.error("위키 상세 조회 중 오류 발생:", error);
            }
        },
    }
});
