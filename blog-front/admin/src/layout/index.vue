<template>
  <el-container>
    <!-- 侧边栏 -->
    <el-aside width="auto">
      <SideBar />
    </el-aside>
    <el-container :class="'main-container '+ isHide">
      <!-- 导航栏 -->
      <el-header height="84px" style="padding:0">
        <NavBar :key="this.$route.fullPath" />
      </el-header>
      <!-- 内容 -->
      <el-main style="background:#F7F9FB">
        <div class="fade-transform-box">
          <router-view v-slot="{Component,route}">
            <transition name="fade" mode="out-in">
              <component :is ="Component" :key="route.path"/>
            </transition>
          </router-view>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import NavBar from "@/layout/components/NavBar.vue";
import SideBar from "@/layout/components/SideBar.vue";
export default {
  components: {
    NavBar,
    SideBar
  },
  computed: {
    isHide() {
      return this.$store.state.collapse ? "hideSideBar" : "";
    }
  }
};
</script>

<style scoped>
.main-container {
  transition: margin-left 0.45s;
  margin-left: 210px;
  min-height: 100vh;
}
.hideSideBar {
  margin-left: 64px;
}
.fade-transform-box {
  position: relative;
  top: 0px;
  bottom: 0px;
  width: 100%;
  overflow: hidden;
}
 /* router-view切换渐变设置 */
.fade-enter-from, .fade-leave-to {
  transform: translateX(20px);
  opacity: 0;
}
.fade-enter-to, .fade-leave-from {
  opacity: 1;
}
.fade-enter-active {
  transition: all 0.7s ease;
}
.fade-leave-active {
   transition: all 0.3s cubic-bezier(1, 0.6, 0.6, 1);
}

</style>
