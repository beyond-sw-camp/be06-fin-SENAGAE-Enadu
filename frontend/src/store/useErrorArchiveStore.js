import { defineStore } from 'pinia';
import axios from 'axios';
import { useUserStore } from './useUserStore';

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
    // 에러 아카이브 등록 기능
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
    // 에러 아카이브 수정 기능
    async updateErrorArchive(id, updatedContent, updatedTitle) {
      const userStore = useUserStore();
      if(!userStore.isLoggedIn){
        console.log("로그인이 필요합니다.");
        return false;
      }
      try {
        const formData = new FormData();
        const updateReq = {
          id: id,
          content: updatedContent,
          title: updatedTitle
        };
        const jsonBlob = new Blob([JSON.stringify(updateReq)], { type: "application/json"});
        formData.append("getErrorArchiveUpdateReq", jsonBlob);

        const response = await axios.patch(backend+"/errorarchive", formData, {
          withCredentials: true,
          headers: { "Content-Type": "multipart/form-data"
          }
        });
        if (response && response.data.isSuccess) {
          console.log("에러 아카이브 수정 성공:", response.data);
          return true;
      } else {
          throw new Error("수정 실패");
      }
      } catch (error) {
          console.error("에러 아카이브 수정 중 오류 발생:", error);
          throw error;
      }
    },
    async fetchErrorArchiveDetail(id) {
      const response = await fetch(`api/erroarchive/detail?${id}`);
      this.errorArchiveDetail = await response.json();
    },
    // 에러 아카이브 상세 조회
    async getErrorArchiveDetail(id) {
      try {
        const response = await axios.get(backend + "/errorarchive/detail", {
          params: { id: id },
          withCredentials: true,
        });
        console.log('응답 데이터:'+response.data);
        if (response && response.data) {
          this.errorArchiveDetail = response.data.result;
          console.log('상세 조회 결과:', this.errorArchiveDetail);
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
    async likeErrorArchive(id, like){
      const request = {
        id: id,
        isLike: like
      }
      try {
        const response = await axios.post(`${backend}/errorarchive/like`,request, {
          withCredentials: true,
        });

        if (response.data.isSuccess) {
          return response.data.result.result;
        } else {
          throw new Error(response.data.message);
        }
      } catch (error) {
        console.error("에러아카이브 좋아요/싫어요 중 오류 발생:", error);
      }
    },
    async scrapErrorArchive(id){
      console.log(id);
      const scrapReq = {
        id: id,
      }
      try {
        const response = await axios.post(`${backend}/errorarchive/scrap`,scrapReq, {
          withCredentials: true,
        });

        if (response.data.isSuccess) {
          return response.data.result.result;
        } else {
          throw new Error(response.data.message);
        }
      } catch (error) {
        console.error("에러아카이브 좋아요/싫어요 중 오류 발생:", error);
      }
    },
  }
});