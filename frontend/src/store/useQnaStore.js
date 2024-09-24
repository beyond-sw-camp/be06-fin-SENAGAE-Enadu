import {defineStore} from "pinia";
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
        }
    }
);

export const useQnaStore = defineStore("qna", {
    state: () => ({
        qnaCards: [],
        qnaDetail: [],
        qnaAnswers: []
    }),


    actions: {
        async registerQna(myTitle, myText, myCategory) {
            const data = {
                title: myTitle,
                content: myText,
                categoryId: myCategory
            };

            try {
                await axios.post(backend + "/qna", data, {
                    headers: {
                        'Content-Type': 'application/json'
                    }, withCredentials: true
                });
            } catch (error) {
                alert("서버에 등록하는 과정에서 문제가 발생했습니다.")
            }
        },

        async getQnaList(sort, page) {
            const params = {
                sort: sort,
                page: page,
                size: 15
            };

            try {
                const res = await axios.get(backend + "/qna/list", {
                    params: params, withCredentials: true
                });
                this.qnaCards = res.data.result;
            } catch (error) {
                alert("질문 목록 데이터 요청 중 에러가 발생했습니다.");
            }
        }, async getQnaDetail(id) {
            try {
                let res = await axios.get(backend + "/qna/detail?qnaBoardId=" + id, {withCredentials: true});
                this.qnaDetail = res.data.result;
                this.qnaAnswers = res.data.result.answers;
            } catch (error) {
                alert("질문 상세 데이터 요청 중 에러가 발생했습니다.");
            }
        }, async registerComment(answerId, superCommentId, myComment) {
            const data = {
                answerId: answerId, superCommentId: superCommentId, content: myComment,
            };

            try {
                await axios.post(backend + "/ans/comment", data, {
                    headers: {
                        'Content-Type': 'application/json'
                    }, withCredentials: true
                });
            } catch (error) {
                alert("서버에 등록하는 과정에서 문제가 발생했습니다.")
            }
        },
    },
});
