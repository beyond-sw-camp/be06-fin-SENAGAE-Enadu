import { defineStore } from "pinia";
import axios from "axios";

const backend = "/api";

// Axios Interceptor 설정
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
        wikiCards: [], // 위키 목록
        wikiRegisterReq: {
            title: '',
            categoryId: '',
            content: '',
        },
        wikiDetail: null, // 위키 상세 정보
    }),

    actions: {
        // 위키 등록 기능
        async registerWiki(thumbnail) {
            try {
                console.log("위키 등록 요청 데이터:", this.wikiRegisterReq);

                const formData = new FormData();
                const jsonBlob = new Blob([JSON.stringify(this.wikiRegisterReq)], { type: "application/json" });
                formData.append("wikiRegisterReq", jsonBlob);

                // 썸네일 파일 추가
                if (thumbnail) {
                    formData.append("thumbnail", thumbnail);
                }

                const response = await axios.post(backend + "/wiki", formData, {
                    withCredentials: true,
                    headers: { "Content-Type": "multipart/form-data" }
                });

                // 응답 데이터 처리
                if (response && response.data) {
                    if (response.data.isSuccess) {
                        return true; // 성공 시 true 반환
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

        // 위키 상세 조회 기능
        async fetchWikiDetail(id) {
            try {
                const response = await axios.get(backend + "/wiki/detail", {
                    withCredentials: true,
                    params: { id: id }, 
                });

                if (response && response.data) {
                    this.wikiDetail = response.data.result;
                } else {
                    throw new Error("위키 상세 조회 실패");
                }
            } catch (error) {
                console.error("위키 상세 조회 중 오류 발생:", error);
            }
        },
    },
});
