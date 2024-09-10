import { defineStore } from "pinia";
import axios from "axios";

const backend = "/api";

export const useCategoryStore = defineStore("category", {
    state: () => ({
        superCategories: [],
        subCategories: [],
        loading: false,
        searchKeyword: ""
    }),
    actions: {
        async loadSuperCategories() {
            this.loading = true;
            try {
                const response = await axios.get(backend + "/category/super");
                this.superCategories = response.data.result;
            } catch (error) {
                console.log("상위 카테고리 로딩 실패");
            } finally {
                this.loading = false;
            }
        },
        async loadSubCategories(superCategoryId) {
            this.loading = true;
            if (superCategoryId === null) superCategoryId = 1;
            try {
                if (!superCategoryId) {
                    throw new Error("superCategoryId가 유효하지 않습니다.");
                }
                const response = await axios.get(`${backend}/category/sub?superCategoryId=${superCategoryId}`);
                        this.subCategories = response.data.result;
            } catch (error) {
                if (error.response) {
                    console.error("서버 응답 에러:", error.response.data);
                    console.error("상태 코드:", error.response.status);
                    console.error("헤더:", error.response.headers);
                } else if (error.request) {
                    console.error("응답 없음:", error.request);
                } else {
                    console.error("에러 메시지:", error.message);
                }
                console.error("전체 에러 객체:", error);
            } finally {
                this.loading = false;
            }
        },
        updateSearchKeyword(keyword) {
            this.searchKeyword = keyword;
        }
    },
    getters: {
        filteredCategories(state) {
            if (state.searchKeyword === "") {
                return state.superCategories;
            }
            return state.superCategories.filter(category =>
                category.categoryName.toLowerCase().includes(state.searchKeyword.toLowerCase())
            );
        },
        filteredSubCategories(state) {
            if (state.searchKeyword === "") {
                return state.subCategories;
            }
            return state.subCategories.filter(category =>
                category.categoryName.toLowerCase().includes(state.searchKeyword.toLowerCase())
            );
        }
    }
});