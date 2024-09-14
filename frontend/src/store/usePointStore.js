import {defineStore} from "pinia";
import axios from "axios";

const backend = "/api";

export const usePointStore = defineStore("point", {
    state: () => ({
        pointHistoryList: [
            {
                point: 1000,
                state: false,
                description: "이유",
                createdAt: "YYYY-MM-DDTHH:MM:SS",
                totalPage: 0,
            }
        ],
        myPointRank: {
            point: 0,
            rank: 1,
        },
        pointRankingList: [
            {
                point: 3000,
                userId: 0,
                nickname: "nickname",
                profileImg: "url",
                grade: "뉴비",
                gradeImg: "url",
                rank: "1",
                totalPage: 10,
            }
        ],

    }),
    actions: {
        async getPointHistory(page) {
            const size = 10;
            const res = await axios.get(`${backend}/point?page=${page}&size=${size}`, {withCredentials: true})
            this.pointHistoryList = res.data.result;
        },
        async getMyPointRanking() {
            const res = await axios.get(`${backend}/point/myrank`, {withCredentials: true})
            this.myPointRank = res.data.result;
        },
        async getPointRankList(page) {
            const size = 10;
            const res = await axios.get(`${backend}/point/rank?page=${page}&size=${size}`, {withCredentials: true})
            this.pointRankingList = res.data.result;
        }
    }
})
