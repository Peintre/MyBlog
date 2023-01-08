<template>
    <div class="rightSide" :style="isShow">
        <div :class="'rightSide-config-hide ' + isOut">
            <i :class="'iconfont rightSide-icon ' + icon" @click="check" />
        </div>
        <div class="setting-container" @click="show">
            <i class="iconfont iconshezhi setting" />
        </div>
        <i @click="backTop" class="iconfont rightSide-icon iconziyuanldpi" />
    </div>
</template>

<script>
export default {
  mounted() {
    window.addEventListener("scroll", this.scrollToTop,true)
  },
  destroyed() {
    window.removeEventListener("scroll", this.scrollToTop)
  },
  created(){
    this.getThemeType()
  },
  data: function() {
    return {
      isShow: "",
      isOut: "rightSide-out",
      icon: ''
    }
  },
  methods: {
    // 回到顶部方法
    backTop() {
      window.scrollTo({
        behavior: "smooth",
        top: 0
      })
    },
    //返回滚动条高度
    scrollHeight(){
      return window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop
    },
    // 为了计算距离顶部的高度，当高度大于100显示回顶部图标，小于100则隐藏
    scrollToTop() {     
      this.scrollTop = this.scrollHeight()
      if (this.scrollTop > 20) {
        this.isShow = "opacity: 1;transform: translateX(-38px);"
      } else {
        this.isShow = ""
      }
    },
    show() {
      const flag = this.isOut == "rightSide-out"
      this.isOut = flag ? "rightSide-in" : "rightSide-out"
    },
    check() {
        if(this.icon == "iconyueliang"){
            this.icon = "icontaiyang"
            this.$store.commit('changeTheme','dark')
            document.documentElement.setAttribute('class', 'dark')
            //变换header底色
            if(this.scrollHeight() > 0){
              document.getElementById("header").style.setProperty('background-color','rgba(18,18,18,.8)')
            }
        }else{
            this.icon = "iconyueliang"
            this.$store.commit('changeTheme','light')
            document.documentElement.setAttribute('class', 'light')
            //变换header底色
            if(this.scrollHeight() > 0){
              document.getElementById("header").style.setProperty('background-color','hsla(0,0%,100%,.8)')
            }
        }      
    },
    getThemeType(){
      let theme = this.$store.state.theme
      if(theme === 'light'){
        this.icon = 'iconyueliang'
      }else{
        this.icon = 'icontaiyang'
      }     
    }
  }
}
</script>

<style scoped>
.rightSide {
  z-index: 4;
  position: fixed;
  right: -38px;
  bottom: 85px;
  transition: all 0.5s;
}
.rightSide-config-hide {
  transform: translate(35px, 0);
}
.rightSide-out {
  animation: rightSideOut 0.3s;
}
.rightSide-in {
  transform: translate(0, 0) !important;
  animation: rightSideIn 0.3s;
}
.rightSide-icon,
.setting-container {
  display: block;
  margin-bottom: 2px;
  width: 30px;
  height: 30px;
  background-color: #49b1f5;
  color: #fff;
  text-align: center;
  font-size: 16px;
  line-height: 30px;
  cursor: pointer;
}
.rightSide-icon:hover,
.setting-container:hover {
  background-color: #ff7242;
}
.setting-container i {
  display: block;
  animation: turn-around 2s linear infinite;
}
@keyframes turn-around {
  0% {
    transform: rotate(0);
  }
  100% {
    transform: rotate(360deg);
  }
}
@keyframes rightSideOut {
  0% {
    transform: translate(0, 0);
  }
  100% {
    transform: translate(30px, 0);
  }
}
@keyframes rightSideIn {
  0% {
    transform: translate(30px, 0);
  }
  100% {
    transform: translate(0, 0);
  }
}
</style>