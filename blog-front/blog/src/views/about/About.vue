<template>
    <div class="my-main">
        <Banner :imageUrl="cover" :pageTitle="pageTitle"></Banner>
    </div>
</template>

<script>
import Banner from "@/components/Banner.vue"
export default {
    components: {
        Banner
    },
    computed :{
    },
    data() {
        return {
            cover: "https://static.talkxj.com/config/2a56d15dd742ff8ac238a512d9a472a1.jpg",
            pageTitle:'关于'
        }
    },
}
</script>

