import { defineStore } from "pinia";
import axios from "axios";
import { useUserStore } from './useUserStore';

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
        totalPages: 0,
        wikiRegisterReq: {
            title: '',
            categoryId: '',
            content: '',
        },
        wikiDetail: null,
    }),

    actions: {

        // 위키 등록 기능
        async registerWiki(thumbnail) {
            const userStore = useUserStore(); // 유저 스토어 사용
            if (!userStore.isLoggedIn) {
                console.log("로그인이 필요합니다.");
                return false;
            }
            try {
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
                    console.log("응답 데이터:", response.data);

                    if (response.data.isSuccess) {
                        const newWikiId = response.data.result.wikiId; // 서버에서 반환된 ID 사용
                        return newWikiId; // 성공적으로 위키 등록되었을 때 ID 반환
                    } else {
                        throw new Error(response.data.message || "서버 응답 오류");
                    }
                } else {
                    throw new Error("응답 데이터가 없습니다.");
                }
            } catch (error) {
                console.error("위키 등록 중 오류 발생:", error);
                return false; // 오류 발생 시 false 반환
            }
        },

        // 위키 수정 기능
        async updateWiki(id, updatedContent, updatedThumbnail) {
            const userStore = useUserStore(); // 유저 스토어 사용
            if (!userStore.isLoggedIn) {
                console.log("로그인이 필요합니다.");
                return false;
            }

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
                const response = await axios.get(backend + "wiki/detail", {
                    params: { id },
                    withCredentials: true,
                });

                if (response && response.data) {
                    console.log('Wiki Detail Response:', response.data);
                    this.wikiDetail = response.data.result;


                } else {
                    throw new Error("위키 상세 조회 실패");
                }
            } catch (error) {
                console.error("위키 상세 조회 중 오류 발생:", error);
            }
        },

        // 위키 목록 조회
        async fetchWikiList(page) {
            console.log(`Fetching page ${page}`);
            const params = {
                page: page - 1,
                size: 20,
            };
            try {
                const response = await axios.get(backend + "/wiki/list", {
                    params: params,
                    withCredentials: true,
                });
                this.wikiCards = response.data.result;
                if (this.wikiCards.length > 0) {
                    this.totalPages = this.wikiCards[0].totalPages;
                } else {
                    this.totalPages = 1;
                }

                console.log(`Total Pages: ${this.totalPages}`);
            } catch (error) {
                console.error("Error fetching wiki list:", error);
            }
        },
    }
});
