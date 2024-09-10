<template>
    <div class="category-search-modal-body">
        <div class="modal-background">
            <div class="modal">
                <div class="modal-header">
                    하위 카테고리 선택
                    <div class="super-category-name">상위 카테고리 : {{ superCategory.categoryName }}</div>
                </div>
                <div class="modal-search-container">
                    <input type="text" class="search-box" v-model="searchKeyword" placeholder="카테고리를 검색하세요" />
                    <button class="search-btn">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
                <div class="category-list-wrapper">
                    <div v-if="loading" style="text-align: center;">로딩 중...</div>
                    <div v-if="filteredSubCategories && filteredSubCategories.length > 0">
                        <ul class="category-list">
                            <CategoryItemComponent 
                                v-for="category in filteredSubCategories" 
                                :key="category.id" 
                                :category="category" 
                                :isSelected="category.id === mySubCategory?.id" 
                                @select-category="handleCategorySelect"/>
                        </ul>
                    </div>
                    <div v-else style="text-align: center;">해당 카테고리 없음</div>
                </div>
                <div class="create-category">
                    <button class="btn create-btn" @click="createSubCategory">하위 카테고리 생성</button>
                </div>
                <div class="button-group">
                    <button class="btn close-btn" @click="closeModal">닫기</button>
                    <button class="btn check-btn" @click="confirmSelection">확인</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { useCategoryStore } from '@/store/useCategoryStore';
import CategoryItemComponent from './CategoryItemComponent.vue';

export default {
    name: "SubCategoryModal",
    props: {
        superCategory: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            loading: false,
            mySubCategory: null,
            filteredSubCategories: [],
            default: 1,
        }
    },
    computed: {
        searchKeyword: {
            get() {
                const categoryStore = useCategoryStore();
                return categoryStore.searchKeyword;
            },
            set(value) {
                const categoryStore = useCategoryStore();
                categoryStore.updateSearchKeyword(value);
            }
        }
    },
    methods: {
        loadSubCategories() {
            this.loading = true;
            const categoryStore = useCategoryStore();
            categoryStore.loadSubCategories(this.superCategory.id)
                .then(() => {
                    this.loading = false;
                    this.filteredSubCategories = categoryStore.filteredSubCategories;
                })
                .catch((error) => {
                    console.log("error : " + error);
                    this.loading = false;
                });
        },
        handleCategorySelect(mySubCategory) {
            this.mySubCategory = mySubCategory;
        },
        confirmSelection() {
            if (this.mySubCategory) {
                this.$emit('mySubCategory', this.mySubCategory);
                this.$emit('closeSub');
            } else {
                alert("카테고리를 선택하세요.");
            }
        },
        closeModal() {
            this.$emit('closeSub');
        },
        createSubCategory() {
            alert("등록 등록")
        }
    },
    watch: {
        searchKeyword() {
            const categoryStore = useCategoryStore();
            this.filteredSubCategories = categoryStore.filteredSubCategories;
            this.mySubCategory = null;
        }
    },
    created() {
        this.loadSubCategories();
    },
    components: {
        CategoryItemComponent
    }
}
</script>

<style scoped>
    .modal-background {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .modal {
        background-color: white;
        padding: 30px;
        border-radius: 16px;
        width: 600px;
        max-height: 80%;
        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        overflow: hidden;
    }

    .modal-header {
        font-size: 24px;
        margin-bottom: 20px;
        color: #333;
        font-weight: bold;
    }

    .super-category-name {
        font-size: 18px;
        color: #333;
        font-weight: normal;
        margin-top: 10px;
        text-align: center;
    }

    .modal-search-container {
        display: flex;
        position: relative;
        margin-bottom: 20px;
    }

    .search-box {
        width: 100%;
        padding: 12px 16px;
        border: 1px solid #ddd;
        border-radius: 10px;
        margin-bottom: 20px;
        font-size: 16px;
        box-sizing: border-box;
    }

    .search-btn {
        position: absolute;
        right: 20px;
        top: 40%;
        transform: translateY(-50%);
        background-color: transparent;
        border: none;
        cursor: default;
        padding: 0;
    }

    .search-btn i {
        font-size: 24px;
        color: #666;
        transition: color 0.1s;
    }

    .category-list-wrapper {
        flex-grow: 1;
        overflow-y: scroll;
        margin-bottom: 20px;
        padding-right: 10px;
        height: 300px;
    }

    .category-list {
        display: flex;
        flex-wrap: wrap;
        gap: 10px;
        padding: 0;
        margin: 0;
        list-style: none;
    }

    .button-group {
        display: flex;
        justify-content: center;
        gap: 10px;
    }

    .btn {
        padding: 10px 20px;
        border-radius: 8px;
        border: none;
        cursor: pointer;
        font-size: 14px;
        font-weight: bold;
        transition: background-color 0.3s, transform 0.2s;
    }

    .btn:focus {
        outline: none;
    }

    .close-btn {
        background-color: #e44e4e;
        color: white;
    }

    .close-btn:hover {
        background-color: #ff5c5c;
    }

    .check-btn {
        background-color: var(--main-color);
        color: white;
    }

    .check-btn:hover {
        background-color: #67adfd;
    }

    .create-category {
        display: flex;
        justify-content: flex-end;
        margin-top: 10px;
    }

    .create-btn {
        background-color: #fff;
        border: 1px solid #ddd;
    }

    .create-btn:hover {
        background-color: var(--main-color);
        color: #fff;
    }
</style>