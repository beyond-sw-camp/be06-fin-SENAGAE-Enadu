<template>
    <div class="custom_container">
        <div id="root">
            <div class="css-ey8mg8" style="">
                <WikiRegisterComponent />
            </div>
        </div>
    </div>
</template>

<script>
import WikiRegisterComponent from '@/components/wiki/WikiRegisterComponent.vue';

export default {
    name: "WikiRegisterPage",
    data() {
        return {
            isLoading: true
        }
    },
    components: {
        WikiRegisterComponent
    }
}

</script>
