import {defineStore} from 'pinia';
import axios from 'axios';

const backend = "/api";

export const useMainStore = defineStore('main', {
    state: () => ({
        mainPageInfo: {
            wikiListResList: [],
            errorArchiveListResList: [],
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
        }
    }
});


