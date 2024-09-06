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

export const useQnaStore = defineStore("qna", {
  state: () => ({
    qnaCards: [],
  }),
  actions: {
    async getQnaList() {
      let res = await axios.get(
        "https://run.mocky.io/v3/42f06300-36da-49c0-9849-20e4b92fe320"
      );
      if (typeof res.data === "string") {
        this.qnaCards = JSON.parse(res.data).result;
      } else {
        this.qnaCards = res.data.result;
      }
      console.log(res.data);
      console.log(res.data.result);
      if (res.status === 200) {
        console.log(this.qnaCards);
        this.qnaCards = res.data.result;
      }
    },
  },
});
