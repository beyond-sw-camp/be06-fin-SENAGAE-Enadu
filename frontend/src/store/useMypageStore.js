import {defineStore} from "pinia";
import axios from "axios";

const backend = "/api";

export const useMypageStore = defineStore("mypage", {
    state: () => ({
        userInfo: {},
        history: {
            archiveList: [],
            wikiList: [],
            questionList: [],
            answerList: []
        },
        scrap: {
            archiveList: [],
            wikiList: [],
            qnaList: []
        }
    }),
    actions: {
        async fetchUserInfo() {
            try {
                if (Object.keys(this.userInfo).length !== 0) {
                    return;
                }
                const response = await axios.get(backend + "/mypage/info", {
                    withCredentials: true
                });
                if (!response || !response.data) {
                    throw new Error("Invalid response from server");
                }
                this.userInfo = response.data.result;
            } catch (error) {
                console.error("유저 정보 가져오기 에러:", error);
            }
        },
        async checkNickname(nickname) {
            try {
                const response = await axios.get(backend + "/user/duplicate/nickname", {
                    params: { nickname }
                });
                if (!response || !response.data) {
                    throw new Error("Invalid response from server");
                }
                return response.data.result;
            } catch (error) {
                console.error("닉네임 중복 확인 중 오류 발생:", error);
            }
        },
        async updateNickname(nickname) {
            try {
                const response = await axios.patch(backend + "/user/nickname",
                    { nickname },
                    { withCredentials: true }
                );
                if (!response || !response.data) {
                    throw new Error("Invalid response from server");
                }
                this.userInfo.nickname = nickname;
                return true;
            } catch (error) {
                console.error("닉네임 업데이트 중 오류 발생:", error);
                return false;
            }
        },
        async uploadProfileImage(formData) {
            try {
                const response = await axios.patch(backend + "/user/img", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                    withCredentials: true
                });
                console.log(response.data);
                if (!response || !response.data) {
                    throw new Error("Invalid response from server");
                }
                this.userInfo.profileImg = response.data.result;
                return true;
            } catch (error) {
                console.error("프로필 이미지 업로드 중 오류:", error);
                return false;
            }
        },
        async updatePassword(passwordData) {
            try {
                const response = await axios.patch(backend + '/user/password', passwordData,
                    {withCredentials: true});
                if (response.data.code === 1000) {
                    return true;
                } else if (response.data.code === 2041) {
                    window.alert(response.data.message);
                } else if (response.data.code === 2042) {
                    window.alert(response.data.message);
                } else {
                    throw new Error('비밀번호 변경 실패');
                }
            } catch (error) {
                console.error("비밀번호 변경 중 오류 발생:", error);
                alert("비밀번호 변경에 실패하였습니다.");
            }
        },
        async getLogWikiList(page) {
            const params = {
                page: page,
                size: 12
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
                size: 15
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
                size: 15
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
        async getQnaScrapList(page) {
            const params = {
                page: page,
                size: 15
            };
            try {
                const response = await axios.get(backend + "/mypage/scrap/qna", {
                    params: params,
                    withCredentials: true
                });
                this.scrap.qnaList = response.data.result;
            } catch (error) {
                alert("스크랩 내역을 불러오는데 실패하였습니다.");
            }
        },
    }
});