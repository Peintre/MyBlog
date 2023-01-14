<template>
      <!-- 导航栏 -->
      <div class="header" id="header">
        <Header />
      </div >
      <!-- 侧边导航栏 -->
      <div class="siderBar hidden-sm-and-up">
        <SiderBar />
      </div >
      <!-- 页面内容 -->
      <div class="main">
        <router-view :key="$route.fullPath"/>
      </div>
      <!-- 页脚 -->
      <div class="footer">
        <Footer />
      </div>
      <!-- 返回顶部 -->
      <div class="backTop">
        <BackToTop />
      </div>
      <!-- 登录模态框 -->
      <div class="loginIn">
        <Model />
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
  methods:{
    listWebConfigInfo(){
      let this_=this
      this.axios.get("/api/websiteConfig/getWebConfig").then(({ data }) => {   
        this_.$store.commit('setBlogInfo',data.data)
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
.main{
  min-height: calc(100vh - 136px);
}
</style>

