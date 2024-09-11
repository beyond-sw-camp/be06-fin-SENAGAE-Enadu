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
    qnaDetail: []
  }),
  actions: {
    async registerQna(myTitle, myText) {
      const data = {
        title: myTitle,
        content: myText,
        categoryId: 5
      };

      try {
        await axios.post("http://localhost:8080/qna", data, {
          headers: {
            'Content-Type': 'application/json'
          }, withCredentials: true
        });
      } catch (error) {
        console.log("err")
      }
    },

    async getQnaList() {
      const params = {
        sort: "latest",
        page: 0,
        size: 10
      };

      try {
        const res = await axios.get("http://localhost:8080/qna/list", {
          params: params,
          withCredentials: true
        });
        this.qnaCards = res.data.result;
        console.log(this.qnaCards);
      } catch (error) {
        console.error("Error fetching Q&A list:", error);
      }
    },
  },
});
