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
        qnaAnswers: [],
        checkQnaLike: 0,
        checkQnaHate: 0,
        checkScrap: 0,
        checkAnsLike: 0,
        checkAnsHate: 0,
        qnaSearchedCards: [],
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
        },
        async registerComment(answerId, superCommentId, myComment) {
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
        async registerAnswer(qnaBoardId, myAnswer) {
            const data = {
                qnaBoardId: qnaBoardId,
                content: myAnswer
            };

            try {
                await axios.post(backend + "/ans", data, {
                    headers: {
                        'Content-Type': 'application/json'
                    }, withCredentials: true
                });
            } catch (error) {
                alert("서버에 등록하는 과정에서 문제가 발생했습니다.")
            }
        },
        async adoptAnswer(qnaBoardId, answerId) {
            const data = {
                qnaBoardId: qnaBoardId,
                answerId: answerId
            };

            try {
                await axios.post(backend + "/ans/adopted", data, {
                    headers: {
                        'Content-Type': 'application/json'
                    }, withCredentials: true
                });
            } catch (error) {
                alert("서버에 등록하는 과정에서 문제가 발생했습니다.")
            }
        },
        async questionLike(qnaBoardId) {
            const data = {
                qnaBoardId: qnaBoardId,
            };

            try {
                const res= await axios.post(backend + "/qna/like", data, {
                    headers: {
                        'Content-Type': 'application/json'
                    }, withCredentials: true
                });
                this.checkQnaLike = res.data.result;
            } catch (error) {
                alert("서버에 등록하는 과정에서 문제가 발생했습니다.")
            }
        },
        async questionHate(qnaBoardId) {
            const data = {
                qnaBoardId: qnaBoardId,
            };

            try {
                const res= await axios.post(backend + "/qna/hate", data, {
                    headers: {
                        'Content-Type': 'application/json'
                    }, withCredentials: true
                });
                this.checkQnaHate = res.data.result;
            } catch (error) {
                alert("서버에 등록하는 과정에서 문제가 발생했습니다.")
            }
        },
        async questionScrap(qnaBoardId) {
            const data = {
                qnaBoardId: qnaBoardId,
            };

            try {
                const res= await axios.post(backend + "/qna/scrap", data, {
                    headers: {
                        'Content-Type': 'application/json'
                    }, withCredentials: true
                });
                this.checkScrap = res.data.result;
            } catch (error) {
                alert("서버에 등록하는 과정에서 문제가 발생했습니다.")
            }
        },
        async answerLike(qnaBoardId, answerId) {
            const data = {
                qnaBoardId: qnaBoardId,
                answerId: answerId,
            };

            try {
                const res= await axios.post(backend + "/ans/like", data, {
                    headers: {
                        'Content-Type': 'application/json'
                    }, withCredentials: true
                });
                this.checkAnsLike = res.data.result;
            } catch (error) {
                alert("서버에 등록하는 과정에서 문제가 발생했습니다.")
            }
        },
        async answerHate(qnaBoardId, answerId) {
            const data = {
                qnaBoardId: qnaBoardId,
                answerId: answerId,
            };

            try {
                const res= await axios.post(backend + "/ans/hate", data, {
                    headers: {
                        'Content-Type': 'application/json'
                    }, withCredentials: true
                });
                this.checkAnsHate = res.data.result;
            } catch (error) {
                alert("서버에 등록하는 과정에서 문제가 발생했습니다.")
            }
        },
        async qnaSearch(type, keyword, category, sort, page) {
            const params = {
                type: type,
                keyword: keyword,
                categoryId: category.id,
                sort: sort,
                page: page-1,
                size: 15,
            };

            try {
                const res = await axios.get(backend + "/qna/search", {
                    params: params,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                });
                this.qnaSearchedCards = res.data.result;
            } catch (error) {
                alert("서버에 등록하는 과정에서 문제가 발생했습니다.")
            }
        },

        async editQna(id, myTitle, myText, myCategoryId) {
            const data = {
                id: id, title: myTitle, content: myText, categoryId: myCategoryId
            };

            try {
                await axios.patch(backend + "/qna", data, {
                    headers: {
                        'Content-Type': 'application/json'
                    }, withCredentials: true
                });
            } catch (error) {
                alert("서버에 등록하는 과정에서 문제가 발생했습니다.")
            }
        },

        async editAnswer(id, myText) {
            const data = {
                id: id, content: myText
            };

            try {
                await axios.patch(backend + "/ans", data, {
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
