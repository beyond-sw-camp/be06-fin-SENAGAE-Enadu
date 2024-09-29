import {defineStore} from "pinia";
import axios from "axios";

const backend = "/api";

export const useUserLogStore = defineStore("userLog", {
    state: () => ({
        userInfo: {},
        history: {
            archiveList: [],
            wikiList: [],
            questionList: [],
            answerList: []
        },
    }),
    actions: {
        async fetchUserInfo(nickname) {
            try {
                if (Object.keys(this.userInfo).length !== 0) {
                    return;
                }
                const response = await axios.get(backend + "/mypage/log/info",
                    {
                    params: { nickname: nickname },
                        withCredentials: true,
                    }
                );
                if (!response || !response.data) {
                    throw new Error("Invalid response from server");
                }
                this.userInfo = response.data.result;
            } catch (error) {
                console.error("유저 정보 가져오기 에러:", error);
            }
        },
        async getLogArchiveList(page) {
            const params = {
                page: page,
                size: 15,
                userId: this.userInfo.id,
            };
            try {
                const response = await axios.get(backend + "/mypage/log/archive", {
                    params: params,
                    withCredentials: true
                });
                this.history.archiveList = response.data.result;
            } catch (error) {
                alert("아카이브 목록 불러오는데 실패하였습니다.")
            }
        },
        async getLogWikiList(page) {
            const params = {
                page: page,
                size: 12,
                userId: this.userInfo.id,
            };
            try {
                const response = await axios.get(backend + "/mypage/log/wiki", {
                    params: params,
                    withCredentials: true
                });
                this.history.wikiList = response.data.result;
            } catch (error) {
                alert("위키 목록 불러오는데 실패하였습니다.")
            }
        },
        async getLogQuestionList(page) {
            const params = {
                page: page,
                size: 15,
                userId: this.userInfo.id,
            };
            try {
                const response = await axios.get(backend + "/mypage/log/question", {
                    params: params,
                    withCredentials: true
                });
                this.history.questionList = response.data.result;
            } catch (error) {
                alert("작성 목록 불러오는데 실패하였습니다.");
            }
        },
        async getLogAnswerList(page) {
            const params = {
                page: page,
                size: 15,
                userId: this.userInfo.id,
            };
            try {
                const response = await axios.get(backend + "/mypage/log/answer", {
                    params: params,
                    withCredentials: true
                });
                this.history.answerList = response.data.result;
            } catch (error) {
                alert("작성 목록을 불러오는데 실패하였습니다.");
            }
        },
    }
});