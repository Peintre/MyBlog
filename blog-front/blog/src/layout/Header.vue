<template>
    <div :class="navClass">
        <div class="my-header header-device">
            <!-- 手机端导航栏 -->
            <div class="nav-mobile-container hidden-sm-and-up">
                <div class="blog-title">
                    <a>{{blogInfo.websiteConfig.websiteName}}</a>
                </div>
                <div style="margin-left:auto">
                    <a >
                        <i class="iconfont iconsousuo"/>
                    </a>
                    <a  @click="openMbMenu" style="margin-left:10px;font-size:20px">
                        <i class="iconfont iconhanbao" />
                    </a>
                </div>
            </div>
            <!-- 电脑导航栏 -->
            <div class="nav-container hidden-xs-only">
                <div class="float-left blog-title ">
                    <a>{{blogInfo.websiteConfig.websiteName}}</a>
                </div>
                <div class="float-right nav-title">
                    <div class="menus-item">
                        <router-link class="menus-btn" to="/">
                            <i class="iconfont iconsousuo" /> 搜索
                        </router-link>
                    </div>
                    <div class="menus-item">
                        <router-link class="menus-btn" to="/">
                            <i class="iconfont iconzhuye" /> 首页
                        </router-link>
                    </div>
                    <div class="menus-item">
                        <a class="menu-btn">
                            <i class="iconfont iconfaxian" /> 发现
                            <i class="iconfont iconxiangxia2 expand" />
                        </a>
                        <ul class="menus-submenu">
                            <li>
                            <router-link to="/archive">
                                <i class="iconfont iconguidang" /> 归档
                            </router-link>
                            </li>
                            <li>
                            <router-link to="/category">
                                <i class="iconfont iconfenlei" /> 分类
                            </router-link>
                            </li>
                            <li>
                            <router-link to="/tag">
                                <i class="iconfont iconbiaoqian" /> 标签
                            </router-link>
                            </li>
                        </ul>
                    </div>
                    <div class="menus-item">
                        <a class="menu-btn">
                            <i class="iconfont iconqita" /> 娱乐
                            <i class="iconfont iconxiangxia2 expand" />
                        </a>
                        <ul class="menus-submenu">
                            <li>
                            <router-link to="/album">
                                <i class="iconfont iconxiangce1" /> 相册
                            </router-link>
                            </li>
                            <li>
                            <router-link to="/talk">
                                <i class="iconfont iconpinglun" /> 说说
                            </router-link>
                            </li>
                        </ul>
                    </div>
                    <!-- <div class="menus-item">
                        <router-link class="menu-btn" to="/">
                            <i class="iconfont iconlianjie" /> 友链
                        </router-link>
                    </div>
                    <div class="menus-item">
                        <router-link class="menu-btn" to="/about">
                            <i class="iconfont iconzhifeiji" /> 关于
                        </router-link>
                    </div>
                    <div class="menus-item">
                        <router-link class="menu-btn" to="/">
                            <i class="iconfont iconpinglunzu" /> 留言
                        </router-link>
                    </div> -->
                    <div class="menus-item">
                        <a class="menu-btn" @click="openAdmin">
                            <i class="iconfont iconzhifeiji" /> 后台
                        </a>
                    </div>
                    <div class="menus-item" v-if="this.$store.getters.getUserInfo.avatar == null">
                        <a class="menu-btn" @click="openModel">
                            <i class="iconfont icondenglu" /> 登录
                        </a>
                    </div>
                    <div class="menus-item" v-else>
                        <img
                            class="user-avatar"
                            :src="this.$store.state.userInfo.avatar"
                            height="30"
                            width="30"
                        />
                        <ul class="menus-submenu">
                            <li>
                                <router-link to="/user">
                                    <i class="iconfont icongerenzhongxin" /> 个人中心
                                </router-link>
                            </li>
                            <li>
                                <a @click="logout"><i class="iconfont icontuichu" /> 退出</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>  
</template>

<script>
export default {
    mounted() {
        //监听页面滚动事件
        window.addEventListener("scroll", this.scrollBottom,true)
    },
    unmounted() {
        window.removeEventListener("scroll", this.scrollBottom)
    },
    data: function() {
        return {
            navClass: 'top',
            // 滚动前，滚动条距离顶部的距离
            oldScrollTop: 0
        }
    },
    methods: {
        scrollBottom() {
            let scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
            // 滚动条滚动距离
            let scrollStep = scrollTop-this.oldScrollTop
            //更新滚动前滚条距离顶部距离
            this.oldScrollTop = scrollTop
            if (scrollStep < 0) {
                //向上滚动   
                let header = document.getElementById("header").style
                if(this.$store.state.theme === 'dark'){
                    header.setProperty('background-color','rgba(18,18,18,.8)')
                }
                header.setProperty('transform','translateY(0px)')
                header.setProperty('transition','all 0.5s')
                if(scrollTop == 0 ){
                    //在顶点
                    header.setProperty('background-color','hsla(0,0%,100%,0)')
                    this.navClass = "top"
                }else{
                    //不在顶点
                    this.navClass = "noTop"
                }
            } else {
                //向下滚动
                this.navClass = "noTop"
                let header = document.getElementById("header").style  
                header.setProperty('transform','translateY(-60px)')
                header.setProperty('transition','all 0.5s')  
                if(document.documentElement.getAttribute('class') == 'dark'){
                    header.setProperty('background-color','rgba(18,18,18,.8)')
                }else{
                    header.setProperty('background-color','hsla(0,0%,100%,.8)')
                }   
            }
        },
        openMbMenu(){
            this.$store.commit('openMbMenu',true)
        },
        openModel(){
            this.$store.commit('setModelFlag',true)
        },
        openAdmin(){
            window.open("http://47.92.51.50:81/", "_blank")
        },
        logout(){
            //如果在个人中心则跳回上一页
            if (this.$route.path == "/personalCenter") {
                this.$router.go(-1)
            }
            const this_ = this
            this.axios.get("/api/logout").then(({ data }) => {
                if (data.flag) {
                    this_.$store.commit("resetUserInfo")
                    this_.$message.success("注销成功")
                } else {
                    this_.$message.error(data.message)
                }
            })
        }
    },
    computed:{
        blogInfo(){
            return this.$store.state.blogInfo
        }
    }
}
</script>

<style scoped>
html.dark .header{
    background-color:rgba(18,18,18,.8);
}
.top,
.header a:-webkit-any-link,
.menu-btn{
    color: var(--my-theme-white-color-2);
}
html.light .noTop a:-webkit-any-link,
html.light .noTop .menu-btn{
    color:var(--my-theme-black-color-2);
}
html.dark .noTop a:-webkit-any-link,
html.dark .noTop .menu-btn{
    color:var(--my-theme-white-color-2);
}
.my-header{
    height: 60px;
    left: 0px;
    right: 0px;
    margin-top: 0px;
}
.nav-container{
    font-size: 14px;
    width: 100%;
    height: 100%;
    display: block;
}
.nav-container .blog-title,.nav-title{
    display: flex;
    align-items: center;
    height: 100%;
}
.blog-title a {
  font-size: 18px;
  font-weight: bold;
}
.menus-item{
    position: relative;
    display: inline-block;
    margin: 0 0 0 0.875rem;
    cursor: pointer;
}
.nav-title .menus-item:after{
    transition: all 0.3s;
    content: "";
    width: 0;
    height: 3px;
    background-color: #80c8f8;
    position: absolute;
    top:100%;
    left: 50%;
    right: 50%;
}
.nav-title .menus-item:hover:after{
    width: 100%;
    left: 0;
    right: 0;
}
.nav-mobile-container {
  width: 100%;
  display: flex;
  align-items: center;
}
.menus-item:hover .menus-submenu {
  display: block;
}
.menus-submenu {
  position: absolute;
  display: none;
  right: 0;
  width: max-content;
  margin-top: 8px;
  box-shadow: 0 5px 20px -4px rgba(0, 0, 0, 0.5);
  background-color: var(--my-theme-white-color-3);
  animation: submenu 0.3s 0.1s ease both;
  list-style-type: none;
}
.menus-submenu:before {
  position: absolute;
  top: -8px;
  left: 0;
  width: 100%;
  height: 20px;
  content: "";
}
.menus-submenu a {
  line-height: 2;
  color: #4c4948 !important;
  text-shadow: none;
  display: block;
  padding: 6px 14px;
}
.menus-submenu a:hover {
  background: #4ab1f4;
}
.user-avatar{
    border-radius: 50%;
    overflow: hidden;
}
/*适应PC端 宽度大于768px*/
@media screen and (min-width: 768px) {
    .header-device{   
        padding: 10px 36px;
    }
}
/*适应移动端 宽度小于767px*/
@media screen and (max-width: 767px){
    .header-device{
        padding: 10px 16px
    }
}
/* 动画效果 */
@keyframes submenu {
  0% {
    opacity: 0;
    filter: alpha(opacity=0);
    transform: translateY(10px);
  }
  100% {
    opacity: 1;
    filter: none;
    transform: translateY(0);
  }
}
</style>