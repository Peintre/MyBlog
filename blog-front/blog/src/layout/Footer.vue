<template>
  <div class="my-footer" v-if="!this.isMessage">
    <div class="footer-wrap">
      <div>
        ©{{ dataFormat(blogInfo.websiteConfig.websiteCreateTime) }} -
        {{ new Date().getFullYear() }} By
        {{ blogInfo.websiteConfig.websiteAuthor }}
      </div>
      <a href="https://beian.miit.gov.cn/" target="_blank">
        {{ blogInfo.websiteConfig.websiteRecordNo }}
      </a>
    </div>
  </div>
</template>
  
<script>
import {praseDateStr} from '@/assets/js/common'
export default {
  computed: {
    isMessage() {
      return this.$route.path == "/message";
    },
    dataFormat(){
      return function(date){
        return praseDateStr(date,"YYYY")
      }
    },
    blogInfo(){
      return this.$store.state.blogInfo
    }
  }
}
</script>
  
<style scoped>
.my-footer{
  padding-top: 10px;
}
.footer-wrap {
  width: 100%;
  line-height: 2;
  position: relative;
  padding: 40px 20px;
  color: #eee;
  font-size: 14px;
  text-align: center;
  background: linear-gradient(-45deg, #ee7752, #ce3e75, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: Gradient 10s ease infinite;
}
.footer-wrap a {
  color: #eee !important;
}
@keyframes Gradient {
  0% {
    background-position: 0 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0 50%;
  }
}
</style>
  