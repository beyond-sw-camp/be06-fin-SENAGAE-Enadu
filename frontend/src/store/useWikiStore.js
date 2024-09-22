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
        wikiRegisterReq: {
            title: '',
            categoryId: '',
            content: '',
        },
        wikiDetail: null,
        wikiVersions: [], 
        totalPages: 0,  
        currentPage: 0,  
        pageSize: 10,  
        wikiTitle: '',
        category: '',    
    }),

    actions: {

        // 위키 등록 기능
        async registerWiki(thumbnail) {
            const userStore = useUserStore();
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
                        const newWikiId = response.data.result.wikiId;
                        return newWikiId; 
                    } else {
                        throw new Error(response.data.message || "서버 응답 오류");
                    }
                } else {
                    throw new Error("응답 데이터가 없습니다.");
                }
            } catch (error) {
                console.error("위키 등록 중 오류 발생:", error);
                return false; 
            }
        },

        // 위키 수정 기능
        async updateWiki(id, updatedContent, updatedThumbnail) {
            const userStore = useUserStore();
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
                const response = await axios.get(`${backend}/wiki/detail`, {
                    params: { id },
                    withCredentials: true,
                });

                if (response && response.data.isSuccess) {
                    console.log('Wiki Detail Response:', response.data);
                    const result = response.data.result;

                    this.wikiDetail = result;
                    this.wikiTitle = result.title || 'Unknown Title';
                    this.category = result.category || 'Unknown Category';

                }
            } catch (error) {
                console.error("위키 상세 조회 중 오류 발생:", error);
            }
        },
        // 위키 버전 목록 조회
        async fetchWikiVersionList(wikiId, page) {
            try {
                const response = await axios.get(backend + "/wiki/version/list", {
                    params: { id: wikiId, page: page, size: this.pageSize },
                    withCredentials: true,
                });

                if (response.data.isSuccess) {
                    this.wikiVersions = response.data.result;
                    this.totalPages = response.data.result[0]?.totalPages || 1;
                }
            } catch (error) {
                console.error('API 호출 중 오류 발생:', error);
            }
        },
        // 위키 버전 상세 조회
        async fetchWikiVersionDetail(wikiContentId) {
            try {
                const response = await axios.get(backend + "/wiki/version/detail", {
                    params: { wikiContentId },
                    withCredentials: true,
                });
                if (response && response.data.isSuccess) {
                    this.wikiDetail = response.data.result;
                    return response.data.result; 
                }
            } catch (error) {
                console.error('버전 상세 조회 중 오류 발생:', error);
            }
        },
    }
});
