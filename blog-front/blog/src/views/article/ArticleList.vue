<template>
    <div class="my-main">
        <Banner :imageUrl="cover" :pageTitle="pageTitle"></Banner>
        <div class="articleList-card-content">
            <el-row class="my-articleList-container" :gutter="10">
                <el-col class="my-articleList-col" :md="8" v-for="item of articleList" :key="item.id">
                    <!-- 文章 -->
                    <el-card class="animated zoomIn article-item-card">
                        <div class="article-item-cover">
                            <router-link :to="'/article/' + item.id">
                                <!-- 缩略图 -->
                                <img
                                class="on-hover"                             
                                :src="item.articleCover"
                                />
                            </router-link>
                        </div>
                        <div class="article-item-info">
                            <!-- 文章标题 -->
                            <div>
                                <router-link :to="'/article/' + item.id">
                                {{ item.articleTitle }}
                                </router-link>
                            </div>
                            <div style="margin-top:0.375rem">
                                <!-- 发表时间 -->
                                <el-icon :size="20"><Clock /></el-icon>
                                {{ dataFormat(item.createTime) }}
                                <!-- 文章分类 -->
                                <router-link
                                    :to="'/category/' + item.categoryId"
                                    class="float-right"
                                >
                                <el-icon><Postcard /></el-icon>
                                {{ item.categoryName }}
                            </router-link>
                            </div>
                        </div>
                        <!-- 分割线 -->
                        <el-divider></el-divider>
                        <!-- 文章标签 -->
                        <div class="tag-wrapper">
                            <router-link
                                :to="'/tag/' + tag.id"
                                class="tag-btn"
                                v-for="tag of item.tagDTOList"
                                :key="tag.id"
                            >
                                {{ tag.tagName }}
                            </router-link>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import Banner from "@/components/Banner.vue"
import {praseDateStr} from "@/assets/js/common.js"
export default {
    mounted() {
        //监听页面滚动事件
        window.addEventListener("scroll", this.scrollEvent,true)
    },
    unmounted() {
        window.removeEventListener("scroll", this.scrollEvent)
    },
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
        this.getPageType()
        this.listArticle()
        //获取网站数据
        this.webConfigData() 
    },
    computed :{
        dataFormat(){
            return function(date){
                return praseDateStr(date,"YYYY-MM-DD")
            }
        }
    },
    data() {
        return {
            isLoadComplete:false,
            current: 1,
            size: 6,
            count: 0,
            name: '',
            //页面类型：0 分类，1 标签
            pageType: null,
            articleList: [],
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
        //获取页面类型
        getPageType(){
            const path = this.$route.path
            if (path.indexOf("/category") != -1) {
                this.pageType = 0
            } else {
                this.pageType = 1
            }
        },
        //获取文章数据
        listArticle(){
            const this_ = this 
            let params = {
                current: this.current,
                size: this.size
            }
            if(this.pageType ==0){
                params.categoryId = this.$route.params.id
            }else{
                params.tagId = this.$route.params.id
            }
            this.axios.get("/api/article/blog/getArticleList", {params})
            .then(({ data }) => {
                if (data.data.name) {
                    this_.name = data.data.name
                }
                if(data.data.articlePreviewDTOList.length< 6){
                    this_.isLoadComplete = true
                }
                if (data.data.articlePreviewDTOList.length) {
                    this_.articleList.push(...data.data.articlePreviewDTOList)
                }
            })
        },
        //实现下拉加载数据
        scrollEvent(){
            let bufferHeight = 137  //缓冲距离
            let dscrollTop = document.documentElement.scrollTop || document.body.scrollTop  //滚动条滚动距离
            const clientHeight = document.documentElement.clientHeight || document.body.clientHeight  //浏览器可见窗口距离
            const pageHeight = document.documentElement.offsetHeight || document.body.offsetHeight  //整个页面内容长度，包括超出浏览器部分
            //console.log("滚动距离", dscrollTop, clientHeight,pageHeight)//打印看一下        
            if(pageHeight - clientHeight - dscrollTop - bufferHeight < 0){//到底了
                if(!this.isLoadComplete){
                    this.current++
                    //加载数据
                    this.listArticle()
                }
            }    
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
.my-articleList-col{
    margin-top: 20px;
}
.article-item-card:hover {
    height: 100%;
    width: 100%;
    transition: all 0.3s;
    box-shadow: 0 4px 12px 12px rgba(7, 17, 27, 0.15);
}
.article-item-card:not(:hover) {
    transition: all 0.3s;
}
.article-item-card:hover .on-hover {
    object-fit: cover;
    height: 100%;
    width: 100%;
    transition: all 0.6s;
    transform: scale(1.1);
}
.article-item-card:not(:hover) .on-hover {
    object-fit: cover;
    height: 100%;
    width: 100%;
    transition: all 0.6s;
}
.article-item-cover{
    height: 220px;
    overflow: hidden;
}

.tag-wrapper {
  padding: 10px 15px 10px 18px;
}
.tag-wrapper a {
  color: #fff !important;
}
.tag-btn {
  display: inline-block;
  font-size: 0.725rem;
  line-height: 22px;
  height: 22px;
  border-radius: 10px;
  padding: 0 12px !important;
  background: linear-gradient(to right, #bf4643 0%, #6c9d8f 100%);
  opacity: 0.6;
  margin-right: 0.5rem;
}
/*适应PC端 宽度大于768px*/
@media screen and (min-width: 768px) {
    .articleList-card-content{
        animation: main 1s;
        max-width: 1106px;
        margin: 370px auto 1rem auto;
    }
    .article-item-info{
        line-height: 1.7;
        padding: 15px 15px 12px 18px;
        font-size: 15px;
    }
}
/*适应移动端 宽度小于767px*/
@media screen and (max-width: 767px){
    .articleList-card-content{
        animation: main 1s;
        margin: 240px 5px 20px 5px;
        padding: 36px 14px;
    }
    .article-item-info {
        line-height: 1.7;
        padding: 15px 15px 12px 18px;
    }
}
/* 动画效果 */
@keyframes main{
    0% {
      opacity: 0;
      filter: alpha(opacity=0);
      transform: translateY(50px);
    }
    100% {
      opacity: 1;
      filter: none;
      transform: translateY(0);
    }
  }
</style>