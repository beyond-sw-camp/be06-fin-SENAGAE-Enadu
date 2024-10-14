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
        myPointRank: {},
        rankingList: [],

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
        async fetchRanking(page, pageType) {
            const size = 10;
            const res = await axios.get(`${backend}/point/ranking/${pageType}?page=${page}&size=${size}`)
            this.rankingList = res.data.result;
        }
    }
})
