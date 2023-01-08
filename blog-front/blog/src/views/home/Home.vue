<template>
    <div class="my-main">
        <!-- banner图 -->
        <div class="my-home-banner" :style=" 'background: url(' + cover + ') center center / cover no-repeat;' ">
            <div class="banner-container">
                <h1 class="blog-title animated zoomIn">
                    {{blogInfo.websiteConfig.websiteAuthor}}个人博客
                </h1>
                <!-- 打字机特效 -->
                <div class="blog-intro">
                    {{ obj.output }}
                    <span class="typed-cursor">|</span>
                </div>
                <!--联系方式:手机端显示 -->
                <div class="blog-contact contact-device">
                    <a 
                        v-if="blogInfo.websiteConfig.socialUrlList.indexOf('qq')!=-1" 
                        class="mr-5 iconfont iconqq"
                        target="_blank" :href="
                        'http://wpa.qq.com/msgrd?v=3&uin=' 
                        + blogInfo.websiteConfig.qq +
                        '&site=qq&menu=yes'
                        "
                    />
                    <a
                        v-if="blogInfo.websiteConfig.socialUrlList.indexOf('github')!=-1"
                        target="_blank"
                        :href="blogInfo.websiteConfig.github"
                        class="mr-5 iconfont icongithub"
                    />
                    <a
                        v-if="blogInfo.websiteConfig.socialUrlList.indexOf('gitee')!=-1"
                        target="_blank"
                        :href="blogInfo.websiteConfig.gitee"
                        class="iconfont icongitee-fill-round"
                    />
                </div>
                <!-- 向下滚动图标 -->
                <div class="scroll-down" @click="scrollDown">
                    <el-icon :size="25" class="scroll-down-effects"><ArrowDownBold /></el-icon>
                </div>
            </div>
        </div>
        <!-- 主页内容 -->
        <el-row class="my-home-container" :gutter="10">
            <!-- 左侧内容 -->
            <el-col :md="18" :sm="24" class="container-left">
                <!-- 说说轮播 -->
                <el-card shadow="always" class="my-card-talk animated zoomIn">
                     <Swiper :list="list_contain"></Swiper>
                </el-card>
                <!-- 文章 -->
                <el-card class="my-card-article animated zoomIn" shadow="always"
                v-for="(item, index) of articleList"
                :key="item.id"
                >
                    <!-- 文章封面图 -->
                    <div :class="isRight(index)">
                        <router-link :to="'/article/' + item.id">
                            <el-image
                                class="on-hover block"
                                :src="item.articleCover"
                            />
                        </router-link>
                    </div>
                    <!-- 文章信息-->
                    <div class="article-wrapper">
                        <!-- 文章相关信息 -->
                        <div class="article-title">
                            <router-link :to="'/article/' + item.id">
                                {{ item.articleTitle }}
                            </router-link>
                        </div>
                        <div class="article-info">
                            <!-- 是否置顶 -->
                            <span v-if="item.isTop == 1" class="unselect">
                                <span style="color:#ff7242">
                                <i class="iconfont iconzhiding" /> 置顶
                                </span>
                                <span class="separator">|</span>
                            </span>
                            <!-- 发表时间 -->
                            <el-icon size="14"><Calendar /></el-icon>
                            {{ dataFormat(item.createTime)}}
                            <span class="separator">|</span>
                            <!-- 文章分类 -->
                            <router-link :to="'/categorie/' + item.id">
                                <el-icon size="14" ><Tickets /></el-icon>
                                {{ item.categoryName }}
                            </router-link>
                            <span class="separator">|</span>
                            <!-- 文章标签 -->
                            <router-link
                                style="display:inline-block"
                                :to="'/tag/' + tag.id"
                                v-for="tag of item.tagDTOList"
                                :key="tag.id"
                            >
                                <el-icon size="14"><PriceTag /></el-icon>
                                {{ tag.tagName }}
                            </router-link>
                        </div>
                        <!-- 文章内容 -->
                        <div class="article-content">
                            {{ item.articleContent }}
                        </div>
                    </div>
                </el-card>
            </el-col>
            <!-- 右侧内容 -->
            <el-col :md="6" class="container-right hidden-sm-and-down">
                <div class="right-info">
                    <!-- 博客卡片信息 -->
                    <el-card class="animated zoomIn blog-card">
                        <!-- 头像 -->
                        <el-avatar :size="110" shape="square">
                            <img
                            class="author-avatar"
                            :src="blogInfo.websiteConfig.websiteAvatar"
                            />
                        </el-avatar>
                        <!-- 博主名 -->
                        <div class="author-name" >
                            {{ blogInfo.websiteConfig.websiteAuthor }}
                        </div>
                        <!-- 博主介绍 -->
                        <div class="author-intro" >
                            {{ blogInfo.websiteConfig.websiteIntro }}
                        </div>
                        <!-- 博客信息 -->
                        <div class="blog-info-wrapper">
                            <div class="blog-info-data">
                                <router-link to="/archives">
                                <div style="font-size: 0.875rem">文章</div>
                                <div style="font-size: 1.25rem">
                                    {{ blogInfo.articleCount }}
                                </div>
                                </router-link>
                            </div>
                            <div class="blog-info-data">
                                <router-link to="/categories">
                                <div style="font-size: 0.875rem">分类</div>
                                <div style="font-size: 1.25rem">
                                    {{ blogInfo.categoryCount }}
                                </div>
                                </router-link>
                            </div>
                            <div class="blog-info-data">
                                <router-link to="/tags">
                                <div style="font-size: 0.875rem">标签</div>
                                <div style="font-size: 1.25rem">{{ blogInfo.tagCount }}</div>
                                </router-link>
                            </div>
                        </div>
                        <!-- 收藏按钮 -->
                        <a class="collection-btn" @click="collectBtn">
                            <el-icon color="#fff" :size="18"><CollectionTag /></el-icon>
                            <span>加入书签</span>
                        </a>
                        <!-- 社交信息 -->
                        <div class="card-info-social">
                            <a
                                v-if="blogInfo.websiteConfig.socialUrlList.indexOf('qq')!=-1"
                                class="iconfont iconqq"
                                style="margin-left:5px;"
                                target="_blank"
                                :href="
                                'http://wpa.qq.com/msgrd?v=3&uin=' +
                                    blogInfo.websiteConfig.qq +
                                    '&site=qq&menu=yes'
                                "
                            />
                            <a
                                v-if="blogInfo.websiteConfig.socialUrlList.indexOf('github')!=-1"
                                target="_blank"
                                :href="blogInfo.websiteConfig.github"
                                class="iconfont icongithub"
                            />
                            <a
                                v-if="blogInfo.websiteConfig.socialUrlList.indexOf('gitee')!=-1"
                                style="margin-right:5px;"
                                target="_blank"
                                :href="blogInfo.websiteConfig.gitee"
                                class="iconfont icongitee-fill-round"
                            />
                        </div>
                    </el-card>
                    <!-- 公告 -->
                    <el-card class="blog-card notice animated zoomIn my-t-5">
                        <div class="web-info-title">
                            <el-icon :size="18"><BellFilled /></el-icon>
                            <div class="font">公告</div>
                        </div>
                        <div style="font-size:0.875rem">
                        {{ blogInfo.websiteConfig.websiteNotice }}
                        </div>
                    </el-card>
                    <!-- 网站信息 -->
                    <el-card class="blog-card web-info animated zoomIn my-t-5">
                        <div class="web-info-title">
                            <el-icon :size="18"><DataLine /></el-icon>
                            <div class="font">网站资讯</div>      
                        </div>
                        <div class="web-info">
                            <div style="padding:4px 0 0">
                                运行时间:<span class="float-right">{{ time }}</span>
                            </div>
                            <div style="padding:4px 0 0">
                                总访问量:<span class="float-right">
                                {{ blogInfo.viewsCount }}
                                </span>
                            </div>
                        </div>
                    </el-card>
                </div>
            </el-col>
        </el-row >
    </div>
</template>

<script>
import {praseDateStr} from "@/assets/js/common.js"
import Swiper from "@/components/Swiper.vue"
import EasyTyper from "easy-typer-js";
import { ElMessage } from 'element-plus'
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
    created() {
        this.init()
    },
    components:{
        Swiper
    },
    data: function() {
        return {
            newSentence:'',
            isLoadComplete:false,
            cover:'',
            current:1,
            size:5,
            time:'',
            blogInfo:{
                websiteConfig:{
                    websiteAvatar:'',
                    websiteAuthor: '',
                    websiteIntro: '',
                    qq: '',
                    github: '',
                    gitee: '',
                    websiteNotice: '',
                    socialUrlList:[],
                    websiteCreateTime:''
                },
                pageList:{
                    pageCover:'',
                    pageLabel:''
                },
                articleCount: 0,
                categoryCount: 0,
                tagCount: 0,
                viewsCount: 0
            },
            obj: {
                output: '',
                isEnd: false,
                speed: 300,
                singleBack: true,
                sleep: 3000,
                type: "roolback",
                backSpeed: 100,
                sentencePause: false
            },
            list_contain: [],
            articleList:[]
        }
    },
    methods: {
        init(){
            //获取网站数据
            this.webConfigData() 
            this.fetchData()
            //获取说说数据
            this.listTalks()  
            //获取文章数据
            this.listArticles()    
            //动态时间跳转
            setInterval(this.runTime, 1000)  
        },
        fetchData(){
            let this_ = this
            // 一言Api进行打字机循环输出效果
            fetch("https://v1.hitokoto.cn?c=i")
             .then(response => response.json())
             .then(data => {
                this_.newSentence = data.hitokoto
             })
             .catch(console.error)
        },
        initTyped(input,hooks) {
            this.obj.singleBack = true
            const obj = this.obj
            const typed = new EasyTyper(obj, input,this.completeAsentence,hooks)
        },
        completeAsentence(){
            this.fetchData()
        },
        scrollDown(){
            window.scrollTo({
                behavior: "smooth",
                top: document.documentElement.clientHeight
            });
        },
        collectBtn(){
            ElMessage({
                showClose: false,
                message: '按CTRL+D 键将本页加入书签',
                type: 'warning',
                duration: '2000',
                center: true,
                grouping: true
            })
        },
        listTalks(){
            this.axios.get("/api/talk/blog/getTalks").then(({ data }) => {
                this.list_contain = data.data
            })
        },
        listArticles(){
            let this_ = this
            this.axios.get("/api/article/blog/getArticles", {
                params: {
                    current: this.current,
                    size: this.size
                }
                })
            .then(({ data }) => {
                if(data.data.length < 5){
                    this_.isLoadComplete = true
                }
                if(data.data.length){
                    this_.articleList.push(...data.data)
                }
            })
        },
        scrollEvent(){
            //实现下拉加载数据
            let bufferHeight = 137  //缓冲距离
            let dscrollTop = document.documentElement.scrollTop || document.body.scrollTop  //滚动条滚动距离
            const clientHeight = document.documentElement.clientHeight || document.body.clientHeight  //浏览器可见窗口距离
            const pageHeight = document.documentElement.offsetHeight || document.body.offsetHeight  //整个页面内容长度，包括超出浏览器部分
            //console.log("滚动距离", dscrollTop, clientHeight,pageHeight)//打印看一下        
            if(pageHeight - clientHeight - dscrollTop - bufferHeight < 0){//到底了
                if(!this.isLoadComplete){
                    this.current++
                    //加载数据
                    this.listArticles()
                }
            }    
        },
        runTime() {
            //动态时间
            var timeold =
                new Date().getTime() -
                new Date(this.blogInfo.websiteConfig.websiteCreateTime).getTime()
            var msPerDay = 24 * 60 * 60 * 1000
            var daysold = Math.floor(timeold / msPerDay)
            var str = ''
            var day = new Date()
            str += daysold + '天'
            str += day.getHours() + '时'
            str += day.getMinutes() + '分'
            str += day.getSeconds() + '秒'
            this.time = str
        },
        webConfigData(){ 
            if(this.blogInfos.pageList){
                this.blogInfo = this.blogInfos
            }
            this.bannerBackShow(this.blogInfo)
        },
        bannerBackShow(blogInfo){
            const this_ = this
            //banner背景显示
            if(blogInfo.pageList && blogInfo.pageList.length>0){
                blogInfo.pageList.forEach(item => {
                if (item.pageLabel == "home") {
                    this_.cover = item.pageCover
                }
            })
            }
        }
    },
    computed: {
        isRight() {
            return function(index) {
                if (index % 2 == 0) {
                return "article-cover left-radius";
                }
                return "article-cover right-radius";
            }
        },
        dataFormat(){
            return function(date){
                return praseDateStr(date,"YYYY-MM-DD")
            }
        }
    },
    watch:{
        blogInfos(newVal){
            this.blogInfo = newVal
            this.bannerBackShow(this.blogInfo)
        },
        newSentence(newVal,oldVal){
            this.initTyped(newVal)
        }
    }
}
</script>

<style lang="stylus">
.typed-cursor
  opacity: 1
  animation: blink 0.7s infinite
@keyframes blink
  0%
    opacity: 1
  50%
    opacity: 0
  100%
    opacity: 1
</style>

<style scoped>
.my-home-banner{
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 100vh;
    background-attachment: fixed;
    text-align: center;
    animation: header-effect 1s;
}
.banner-container {
    margin-top: 43vh;
    line-height: 1.5;
    color: var(--my-theme-white-color-2);
}
.scroll-down {
    cursor: pointer;
    position: absolute;
    bottom: 0;
    width: 100%;
}
.my-card-talk{
    --el-card-padding: 0.6rem 1rem !important;
}
.article-wrapper{
    order:1;
    font-size: 14px;
    z-index: 1;
}
 .article-wrapper .article-info a{
    color:var(--my-theme-black-color-3);
}
.article-wrapper a:hover {
    color: #8e8cd8;
}
.article-wrapper a{
    transition: all 0.3s;
    color: #858585;
}
.article-title{
    line-height:1.4;
}
.article-info{
    font-size: 95%;
    line-height: 2;
    margin: 0.375rem 0;
    color: var(--my-theme-black-color-3);
}
.article-content {
    line-height: 2;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    user-select: none;
}
.right-info{
    position: sticky;
    top: 10px;
}
.blog-card{
    line-height: 2;
    text-align: center;
}
.author-avatar {
  transition: all 0.5s;
}
.author-avatar:hover {
  transform: scale(1.1);
}
.right-info .el-avatar{
    --el-avatar-bg-color: #ffffff;
}
.author-name{
    font-size: 1.375rem;
    margin-top:0.625rem
}
.author-intro{
    font-size: 0.875rem;
}
.blog-info-wrapper {
  display: flex;
  justify-self: center;
  padding: 0.875rem 0;
}
.blog-info-wrapper a{
    color: #000000;
}
.blog-info-wrapper a:hover{
    color: #8e8cd8;
}
.blog-info-data {
  flex: 1;
  text-align: center;
}
.collection-btn {
  align-items: center;
  justify-content: center;
  z-index: 1;
  font-size: 14px;
  position: relative;
  display: flex;
  background-color: #49b1f5;
  color: #fff !important;
  height: 32px;
  line-height: 32px;
  transition-duration: 1s;
  transition-property: color;
}
.collection-btn:before {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: -1;
  background: #ff7242;
  content: "";
  transition-timing-function: ease-out;
  transition-duration: 0.5s;
  transition-property: transform;
  transform: scaleX(0);
  transform-origin: 0 50%;
}
.collection-btn:hover:before {
  transition-timing-function: cubic-bezier(0.45, 1.64, 0.47, 0.66);
  transform: scaleX(1);
}
.card-info-social {
  line-height: 40px;
  text-align: center;
  margin: 6px 0 -6px;
}
.card-info-social a {
  font-size: 1.5rem;
  color: #000000;
  padding-left: 20px;
}
.card-info-social a:hover{
    color: #8e8cd8;
}
.right-info .notice,.web-info{
    text-align: left;
}
.right-info .web-info-title{
    display: flex;
}
.right-info .web-info-title i{
    margin-top: 6px;
}
.right-info .web-info-title .font{
    margin-left: 4px;
}
.article-cover .block{
    display: block;
}
/*适应PC端 宽度大于768px*/
@media screen and (min-width: 768px) {
    .contact-device{
        display: none;
    }
    .blog-title{
        font-size: 2.5rem;
    }
    .blog-intro{
        font-size: 1.5rem;
    }
    .my-home-container{
        max-width: 1200px;
        margin: calc(100vh - 48px) auto 28px auto !important;
        padding: 20px 5px 0px 5px;  
    }
    .my-card-article{
        border-radius: 12px 8px 8px 12px;
        display: flex;
        align-items: center;
        height: 280px;
        width: 100%;
        margin-top: 20px;
    }
    .article-cover{
        overflow: hidden;
        width: 45%;
        height: 100%;
    }
    .article-wrapper{
        padding: 0 2.5rem;
        width: 55%;
    }
    .article-title a{
        font-size: 1.5rem;
    }
    .right-radius{
        order: 2;
    }
    .left-radius{
        order: 0;
    }
}
/*适应移动端 宽度小于767px*/
@media screen and (max-width: 767px){
    .blog-title{
        font-size: 26px;
    }
    .my-home-container{
        width: 100%;
        margin: calc(100vh - 66px) auto 0 auto !important;
        padding: 20px 5px 0px 5px;
    }
    .my-card-article{
        margin-top: 1rem;
    }
    .article-cover{
        height: 230px;
        width: 100%;
    }
    .article-wrapper{
        padding: 1.25rem 1.25rem 1.875rem;
        width: 100%;
    }   
    .article-cover .on-hover {
        width: 100%;
    }
    .article-title a{
        font-size: 1.25rem;
    }
}
/* 图标浮动动画效果 */
.scroll-down-effects {
    color: #eee !important;
    text-align: center;
    text-shadow: 0.1rem 0.1rem 0.2rem rgba(0, 0, 0, 0.15);
    line-height: 1.5;
    display: inline-block;
    text-rendering: auto;
    -webkit-font-smoothing: antialiased;
    animation: scroll-down-effect 1.5s infinite;
}
@keyframes scroll-down-effect {
    0% {
        top: 0;
        opacity: 0.4;
        filter: alpha(opacity=40);
    }
    50% {
        top: -16px;
        opacity: 1;
        filter: none;
    }
    100% {
        top: 0;
        opacity: 0.4;
        filter: alpha(opacity=40);
    }
}
.notice .web-info-title i{
    color: #f00;
    animation: big 0.8s linear infinite;
}
@keyframes big {
  0%,
  100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
}
/* 鼠标移动动画效果 */
.on-hover {
    transition: all 0.6s;
    height:100%;
}
.my-card-article:hover .on-hover {
    transform: scale(1.1);
}
</style>