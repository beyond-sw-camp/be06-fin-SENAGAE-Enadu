<template>
    <div class="custom-container">
    <div id="root">
        <div custom-container>
        <div class="css-ey8mg8" style="">
            <div class="custom-container">
            <ErrorBoardRegisterComponent/>
            </div>
        </div>
        </div>
    </div>
    </div>
</template>

<script>
import ErrorBoardRegisterComponent from '@/components/errorboard/ErrorBoardRegisterComponent.vue';

export default {
    name: "ErrorBoardRegisterPage",
    data() {
        return {
            isLoading:true
        }
    },
    components: {
        ErrorBoardRegisterComponent
    }
}

</script>
<style>
.custom-container {
    width: 75%;
    max-width: 1400px;
    margin: 50px auto;
    /* background-color: #fff; */
    padding: 30px;
    border-radius: 10px;
    /* box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); */
}
</style>