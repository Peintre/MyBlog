<template>
    <div class="my-main">
        <Banner :imageUrl="cover" :pageTitle="pageTitle"></Banner>
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
    props:{
        blogInfos:{
            type: Object,
            default() {
                return {}
            }
        }
    },
    components: {
        Banner
    },
    created(){
        //获取文章数据
        this.listArchives()
        //获取网站数据
        this.webConfigData() 
    },
    computed :{
        dataFormat(){
            return function(date){
                return praseDateStr(date,"YYYY-MM-DD HH:mm:ss")
            }
        }
    },
    data() {
        return {
            current: 1,
            count: 0,
            archiveList: [],
            blogInfo:{
                pageList:{
                    pageCover:'',
                    pageName:''
                }
            },
            cover: '',
            pageTitle:''
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
        },
        bannerBackShow(blogInfo){
            const this_ = this 
            //banner背景显示
            if(blogInfo.pageList && blogInfo.pageList.length>0){
                blogInfo.pageList.forEach(item => {
                if (item.pageLabel == "archive") {
                    this_.cover = item.pageCover
                    this_.pageTitle = item.pageName
                }
            })
            }
        },
        webConfigData(){ 
            if(this.blogInfos.pageList){
                this.blogInfo = this.blogInfos
            }
            this.bannerBackShow(this.blogInfo)
        }
    },
    watch:{
        blogInfos(newVal){
            this.blogInfo = newVal
            this.bannerBackShow(this.blogInfo)
        }
    }
}
</script>

<style scoped>
</style>