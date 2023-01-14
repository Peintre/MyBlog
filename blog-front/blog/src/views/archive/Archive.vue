<template>
    <div class="my-main">
        <Banner />
        <el-card class="card-content my-archive-container">
            <el-timeline>
                <el-timeline-item 
                    v-for="item of archiveList" 
                    :key="item.id"
                    :timestamp="dataFormat(item.createTime)"
                    placement="top"
                >
                    <router-link
                        :to="'/article/' + item.id"
                        style="color:#666;text-decoration: none;"
                    >
                        {{ item.articleTitle }}
                    </router-link>
                    <el-divider />
                </el-timeline-item>
            </el-timeline>
        </el-card>
    </div>
</template>

<script>
import Banner from "@/components/Banner.vue"
import {praseDateStr} from "@/assets/js/common.js"
export default {
    components: {
        Banner
    },
    created(){
        //获取文章数据
        this.listArchives()
    },
    computed :{
        dataFormat(){
            return function(date){
                return praseDateStr(date,"YYYY-MM-DD HH:mm:ss")
            }
        },
        blogInfo(){
            return this.$store.state.blogInfo
        }
    },
    data() {
        return {
            current: 1,
            count: 0,
            archiveList: []
        }
    },
    methods:{
        listArchives(){
            const this_ = this 
            this.axios.get("/api/article/blog/getAchieveArticles", {
                params: { current: this.current }
            })
            .then(({ data }) => {
                this_.archiveList = data.data.recordList
                this_.count = data.data.count
            })
        }
    }
}
</script>

<style scoped>
</style>