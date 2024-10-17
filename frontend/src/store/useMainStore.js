import {defineStore} from 'pinia';
import axios from 'axios';

const backend = "/api";

export const useMainStore = defineStore('main', {
    state: () => ({
        mainPageInfo: {
            wikiListResList: [],
            errorArchiveListResList: [],
            qnaListResList: []
        },
        searchInfo: {
            wikiListResList: [],
            errorArchiveResList: [],
            qnaListResList: []
        }
    }),
    actions: {
        async getMainPageInfo() {
            const request = {
                errorArchiveSize: 8,
                wikiSize: 4,
                qnaSize: 8,
            }
            try {
                const response= await axios.get(backend+'/main',{
                    params: request,
                    headers: { 'Content-Type': 'multipart/form-data' },
                    withCredentials: true
                });
                if (response.data.isSuccess){
                    this.mainPageInfo = response.data.result;
                } else {
                    throw new Error(response.data.message);
                }
            } catch (error) {
                console.error('메인 페이지 로딩 실패:', error);
                return null;
            }
        },
        async getTotalSearchInfo(keyword) {
            // 초성 한 글자인지 체크하는 함수 추가
            const isSingleChosung = (keyword) => /^[ㄱ-ㅎ]$/.test(keyword);
            
            // 초성이 한 글자인 경우 경고 메시지 및 API 호출 중단
            if (isSingleChosung(keyword)) {
                alert("초성검색은 한 글자가 불가합니다.");
                return; // API 호출 방지
            }
        
            const request = {
                errorArchiveSize: 8,
                wikiSize: 4,
                qnaSize: 8,
                keyword: keyword
            };
            try {
                const response = await axios.get(backend + '/main/search', {
                    params: request,
                    headers: { 'Content-Type': 'multipart/form-data' },
                    withCredentials: true
                });
                if (response.data.isSuccess) {
                    this.searchInfo = response.data.result;
                } else {
                    throw new Error(response.data.message);
                }
            } catch (error) {
                console.error('통합 검색 페이지 로딩 실패:', error);
                return null;
            }
        }
        
    }
});


