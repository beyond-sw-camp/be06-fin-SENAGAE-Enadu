import { defineStore } from 'pinia';
import axios from 'axios';
const backend = "/api";

export const useErrorArchiveStore = defineStore('errorarchive', {
  state: () => ({
    errorArchiveId: 1,
    errorArchiveDetail: {
      id: 1,
      nickname: "",
      title: "",
      content: "",
      superCategory: "",
      subCategory: "",
      createAt: "",
      modifiedAt: "",
      likeCnt: 0,
      hateCnt: 0,
      checkLike: false,
      checkHate: false,
      checkScrap: false,
      profileImg: "",
      grade: "",
      gradeImg: ""
    },
    errorarchiveCards: [],
  }),
  actions: {
    async registerErrorArchive(errorarchive) {
      try {
        const response = await axios.post(backend + "/errorarchive", errorarchive, {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true,
        });
         // 서버 응답 확인
         console.log('응답 데이터 로그로 확인'+response.data); // 응답 데이터 로그로 확인
         // 응답 구조 로그 출력
         console.log('API 응답:', response.data);

        // 응답의 유효성 검사
        if (response.data && response.data.isSuccess) {
          // 응답 데이터가 성공적으로 반환된 경우의 처리
          console.log('API 호출 성공:', response.data.message);
          
        } else {
          console.error('응답 데이터에 isSuccess가 없거나 실패한 경우:', response.data);
          throw new Error('응답 데이터에 isSuccess가 없거나 실패한 경우.');
        }
      } catch (error) {
        console.error('등록 중 오류 발생:', error);
        throw error;
      }
    },
    async getErrorArchiveDetail(id) {
      try {
        const response = await axios.get(backend + "/errorarchive/detail", {
          params: { id: id },
          withCredentials: true,
        });
        if (response && response.data) {
          this.errorArchiveDetail = response.data.result;
        } else {
          throw new Error("에러아카이브 상세 조회 실패");
        }
      } catch (error) {
        console.error("에러아카이브 상세 조회 중 오류 발생:", error);
      }
    },
    async getErrorArchiveList(sort, page) {
      const params = {
        sort: sort,
        page: page,
        size: 15
      };
      try {
        const response = await axios.get(backend+"/errorarchive/list", { 
          params: params,
          withCredentials: true });
        this.errorarchiveCards  = response.data.result;
        console.log(this.errorarchiveCards);
      } catch(error) {
        console.error("error fetching errorarchive list:",  error);
      }
    },
  }
});