<template>
    <div v-show="isSub" class="separator"> / </div>
    <div class="category">
        {{ category }}
    </div>
</template>

<script>
export default {
    name: "CategoryComponent",
    props: {
        category: {
            type: String,
            required: true,
        },
        isSub: {
            type: Boolean,
            default: false
        }
    }
}
</script>

<style scoped>
.category {
    margin-bottom: 0.875rem;
    background: #F8F9FA;
    padding-left: 1rem;
    padding-right: 1rem;
    height: 2rem;
    border-radius: 1rem;
    display: inline-flex;
    align-items: center;
    margin-right: 0.875rem;
    //color: #12B886;
    color: var(--main-color);
    text-decoration: none;
    font-weight: 700;
    font-size: 1rem;
}
.separator {
    margin-right: 15px;
    margin-top: 3px;
    color: var(--main-color);
    font-size: 1rem;
    display: inline-block;
    vertical-align: middle;
}
</style>