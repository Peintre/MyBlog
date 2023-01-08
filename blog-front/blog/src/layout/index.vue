<template>
      <!-- 导航栏 -->
      <div class="header" id="header">
        <Header :blogInfos='blogInfo'></Header>
      </div >
      <!-- 侧边导航栏 -->
      <div class="siderBar">
        <SiderBar :blogInfos='blogInfo'></SiderBar>
      </div >
      <!-- 页面内容 -->
      <div class="main">
        <router-view :blogInfos='blogInfo' :key="$route.fullPath"/>
      </div>
      <!-- 页脚 -->
      <div class="footer">
        <Footer :blogInfos='blogInfo'></Footer>
      </div>
      <!-- 返回顶部 -->
      <div class="backTop">
        <BackToTop></BackToTop>
      </div>
      <!-- 登录模态框 -->
      <div class="loginIn">
        <Model :blogInfos='blogInfo'></Model>
      </div>
         
</template>

<script>
import Header from "@/layout/Header.vue"
import Footer from "@/layout/Footer.vue"
import SiderBar from "@/layout/SiderBar.vue"
import BackToTop from "@/components/BackToTop.vue"
import Model from "@/components/Model.vue"
export default {
  created() {
    //获取网站配置信息
    this.listWebConfigInfo()
  },
  components: {
    Header,
    Footer,
    SiderBar,
    BackToTop,
    Model
  },
  data:function(){
    return{
      blogInfo: {}
    }
  },
  methods:{
    listWebConfigInfo(){
      let this_=this
      this.axios.get("/api/websiteConfig/getWebConfig").then(({ data }) => {
        this_.blogInfo = data.data
      })
    }
  }
}
</script>

<style scoped>
.header{
  position: fixed;
  z-index: 1;
  width: 100%;
}
</style>

