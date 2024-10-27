<template>
    <li class="category-item" :class="{ selected: isSelected }" @click="selectCategory">
        {{ category.categoryName }}
    </li>
</template>

<script>
    export default {
        name: "CategoryItemComponent",
        props: {
            category: {
            type: Object,
            required: true
        },
            isSelected: {
                type: Boolean,
                default: false
            }
        },
        methods: {
            selectCategory() {
                this.$emit("selectCategory", this.category);
            }
        }
    }
</script>

<style scoped>
.category-item {
        padding: 10px 15px;
        background-color: #fff;
        border-radius: 25px;
        cursor: pointer;
        transition: background-color 0.3s, transform 0.2s;
        font-size: 14px;
        border: 1px solid #ddd;
    }

    .category-item:hover {
        background-color: #e0e0e0;
    }
    .category-item.selected {
        background-color: var(--main-color);
        color: white;
        border: 1px solid #fff;
    }
</style>