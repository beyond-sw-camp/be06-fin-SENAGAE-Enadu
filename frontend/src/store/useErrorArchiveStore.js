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
      superCategoryId: 0,
      subCategoryId: 0,
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
        const response = await axios.post(backend + "/errorarchive", errorarchive ,{
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
          return response.data.result.errorArchiveId;
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
    async updateErrorArchive(errorarchive) {
      try {
        const response = await axios.patch(backend+"/errorarchive", errorarchive, {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true,
        });
    
        // 응답 확인
        if (response.data.isSuccess) {
          console.log("에러 아카이브 수정 성공:", response.data.message);
          alert("수정이 완료되었습니다.");
          return response.data.result; // 수정된 결과를 반환
        } else {
          throw new Error("수정 실패: " + response.data.message);
        }
      } catch (error) {
        console.error("에러 아카이브 수정 중 오류 발생:", error);
        if (error.response) {
          console.error("응답 데이터:", error.response.data); // 응답 데이터 확인
        }
        throw error;
      }
    },
    async deleteErrorArchive(id) {
      try {
        const response = await axios.patch(`${backend}/errorarchive/removal`,{id:id} , {
          withCredentials: true,
        });
    
        // 응답 확인
        if (response.data.isSuccess) {
          console.log("에러 아카이브 삭제 성공:", response.data.message);
          alert("삭제가 완료되었습니다.");
        
          return response.data.result; // 삭제된 결과를 반환
        } else {
          throw new Error("삭제 실패: " + response.data.message);
        }
      } catch (error) {
        console.error("에러 아카이브 삭제 중 오류 발생:", error);
        if (error.response) {
          console.error("응답 데이터:", error.response.data);
          alert(`Error: ${error.response.data.message}`);
        }
        throw error;
      }
    },
    
    // 에러 아카이브 상세 조회
    async getErrorArchiveDetail(id) {
      try {
        const response = await axios.get(backend + "/errorarchive/detail", {
          params: { id: id },
          withCredentials: true,
        });
        console.log('응답 데이터:', response.data); // 응답 데이터 로그 추가
    
        // 응답의 유효성 검사
        if (response.data && response.data.isSuccess) {
          this.errorArchiveDetail = response.data.result;
          console.log('상세 조회 결과:', this.errorArchiveDetail);
          return response.data.result; // 결과를 반환
        } else {
          throw new Error("에러아카이브 상세 조회 실패: " + response.data.message);
        }
      } catch (error) {
        console.error("에러아카이브 상세 조회 중 오류 발생:", error);
        throw error; // 오류를 호출자에게 전파
      }
    },
    async getErrorArchiveList(sort, page) {
      const params = {
        sort: sort,
        page: page,
        size: 16
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
    async searchErrorArchive(request){
      request.size= 16;
      const response = await axios.get(backend+"/errorarchive/search", {
        params: request
      });
      if (response.data.result){
        this.errorarchiveCards  = response.data.result;
      } else {
        this.errorarchiveCards = [];
      }
    }
  }
});