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
    }),
    actions: {
        async registerWiki(thumbnail) {
            try {
                console.log(this.wikiRegisterReq.title);
                console.log(this.wikiRegisterReq.categoryId);
                console.log("content : "+ this.wikiRegisterReq.content);
                const formData = new FormData();

                const jsonBlob = new Blob([JSON.stringify(this.wikiRegisterReq)], { type: "application/json" });
                formData.append("wikiRegisterReq", jsonBlob);
                formData.append("wikiRegisterReq", this.wikiRegisterReq);

                // 썸네일 파일 추가 
                if (thumbnail) {
                    formData.append("thumbnail", thumbnail);
                }

                const response = await axios.post(backend + "/wiki", formData, {
                    withCredentials: true,
                    headers: { "Content-Type": "multipart/form-data" }
                });

                // 응답 데이터 유효성 확인
                if (response && response.data) {
                    if (response.data.isSuccess) {
                        this.registeredWiki = response.data.result;
                        return true; // 성공 시 true 반환
                    } else {
                        throw new Error(response.data.message || "서버 응답 오류");
                    }
                } else {
                    throw new Error("응답 데이터가 없습니다.");
                }
            } catch (error) {
                // 구체적인 오류 처리
                if (error.response) {
                    console.error("서버 응답 에러:", error.response.data);
                } else if (error.request) {
                    console.error("서버 응답 없음:", error.request);
                } else {
                    console.error("요청 중 오류 발생:", error.message);
                }
                throw error; 
            }
        },
    },
});