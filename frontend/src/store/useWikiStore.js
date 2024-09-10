import { defineStore } from "pinia";
import axios from "axios";

axios.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            console.log("401 에러 처리");
        } else if (error.response && error.response.status === 405) {
            console.log("405 에러 처리");
        } else if (error.response && error.response.status === 304) {
            console.log("304 에러 처리");
        }
    }
);

export const useWikiStore = defineStore("wiki", {
    state: () => ({
        wikiCards: [],
    }),
    actions: {
        async getWikiList() {
            let res = await axios.get("http://localhost:8080/wiki/list");
            if (typeof res.data === "string") {
                this.wikiCards = JSON.parse(res.data).result;
            } else {
                this.wikiCards = res.data.result;
            }
        },
        async registerWiki(formData) {
            try {
                let res = await axios.post("http://localhost:8080/wiki", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });

                if (res.status === 200) {
                    console.log("Wiki 등록 성공:", res.data);
                }
            } catch (error) {
                console.error("Wiki 등록 실패:", error);
                throw error; 
            }
        }
    },
});