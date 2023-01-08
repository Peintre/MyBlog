<template>
  <div>
    <!-- 导航栏 -->
    <div class="nav-bar">
      <!-- 折叠按钮 -->
      <div class="hambuger-container" @click="trigger">
        <el-icon>
          <component :is="isFold"></component>
        </el-icon>
      </div>
      <!-- 面包屑导航 -->
      <el-breadcrumb>
        <el-breadcrumb-item v-for="item of breadcrumbList" :key="item.path">
          <span >{{ item.name }}</span>
        </el-breadcrumb-item>
      </el-breadcrumb>
      <!-- 右侧菜单 -->
      <div class="right-menu">
        <!-- 全屏按钮 -->
        <div class="screen-full" @click="fullScreen">
          <i class="iconfont el-icon-myicwindowzoom48px" />
        </div>
        <!-- 用户选项 -->
        <el-dropdown @command="handleCommand">
          <span>
            <el-avatar :size="40" :src="this.$store.state.avatar" />
            <el-icon><CaretBottom /></el-icon>
          </span>   
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="setting">
                <el-icon><Avatar /></el-icon>个人中心
              </el-dropdown-item>
              <el-dropdown-item command="logout" divided>
                <i class="iconfont el-icon-mytuichu" />退出登录
              </el-dropdown-item>
          </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <!-- 历史标签栏 -->
    <div class="tabs-view-container">
      <div class="tabs-wrapper">
        <span
          :class="isActive(item)"
          v-for="item of this.$store.state.tabList"
          :key="item.path"
          @click="goTo(item)"
        >
          {{ item.name }}
          <el-icon v-if="item.path != '/home'"
            @click.stop="removeTab(item)">
            <Close />
          </el-icon>
        </span>
      </div>
      <div class="tabs-close-item" style="float:right" @click="closeAllTab">
        全部关闭
      </div>
    </div>
  </div>
</template>

<script>
import store from '@/store';
import { resetRouter } from "@/router";
import axios from 'axios';
import router from '@/router';
export default {
  created() {
    //替换面包屑导航
    let matched = this.$route.matched.filter(item => item.name);
    const first = matched[1];
    if (first && first.name === "首页") {
      matched = [{ path: "/home", name: "首页" }];
    }
    this.breadcrumbList = matched;
    //保存当前页标签
    store.commit("saveTab", this.$route);
  },
  data: function() {
    return {
      breadcrumbList: [],
      fullscreen: false,
    };
  },
  methods: { 
    trigger() {
      store.commit("trigger");
    },
    fullScreen() {
      let element = document.documentElement;
      if (this.fullscreen) {
        if (document.exitFullscreen) {
          document.exitFullscreen();
        } else if (document.webkitCancelFullScreen) {
          document.webkitCancelFullScreen();
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen();
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen();
        }
      } else {
        if (element.requestFullscreen) {
          element.requestFullscreen();
        } else if (element.webkitRequestFullScreen) {
          element.webkitRequestFullScreen();
        } else if (element.mozRequestFullScreen) {
          element.mozRequestFullScreen();
        } else if (element.msRequestFullscreen) {
          element.msRequestFullscreen();
        }
      }
      this.fullscreen = !this.fullscreen;
    },
    handleCommand(command) {
      if (command == "setting") {
        router.push({ path: "/setting" });
      }
      if (command == "logout") {
        // 调用注销接口
        axios.post("/api/logout");
        // 清空用户信息
        store.commit("logout");
        store.commit("resetTab");
        // 清空用户菜单
        resetRouter();
        router.push({ path: "/login" });
      }
    },
    goTo(tab) {
      //跳转标签
      router.push({ path: tab.path });
    },
    closeAllTab() {
      store.commit("resetTab");
      router.push({ path: "/home" });
    },
    removeTab(tab) {
      //删除标签
      store.commit("removeTab", tab);
      //如果删除的是当前页则返回上一标签页
      if (tab.path == this.$route.path) {
        var tabList = store.state.tabList;
        router.push({ path: tabList[tabList.length - 1].path });
      }
    }   
  },
  computed: {
    isFold() {
      return store.state.collapse ? "Expand" : "Fold";
    },
    //标签是否处于当前页
    isActive(tab) {
      return (tab) => {
        if (tab.path == this.$route.path) {
          return "tabs-view-item-active";
        }
        return "tabs-view-item";
      }   
    },
  }
};
</script>

<style scoped>
.nav-bar {
  display: flex;
  align-items: center;
  padding-left: 15px;
  padding-right: 30px;
  height: 50px;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}
.hambuger-container {
  font-size: 1.25rem;
  cursor: pointer;
  margin-right: 24px;
}
.tabs-wrapper {
  overflow-x: auto;
  overflow-y: hidden;
  white-space: nowrap;
  width: 95%;
}
.tabs-view-container {
  display: flex;
  position: relative;
  padding-left: 10px;
  padding-right: 10px;
  height: 33px;
  background: #fff;
  border-bottom: 1px solid #d8dce5;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
}
.tabs-view-item {
  display: inline-block;
  cursor: pointer;
  height: 25px;
  line-height: 25px;
  border: 1px solid #d8dce5;
  color: #495060;
  background: #fff;
  padding: 0 8px;
  font-size: 12px;
  margin-top: 4px;
  margin-left: 5px;
}
.tabs-close-item {
  position: absolute;
  right: 10px;
  display: inline-block;
  cursor: pointer;
  height: 25px;
  line-height: 25px;
  border: 1px solid #d8dce5;
  color: #495060;
  background: #fff;
  padding: 0 8px;
  font-size: 12px;
  margin-top: 4px;
  margin-left: 5px;
}
.tabs-close-item:hover{
  color: #8e8cd8;
}
.tabs-view-item-active {
  display: inline-block;
  cursor: pointer;
  height: 26px;
  line-height: 26px;
  padding: 0 8px;
  font-size: 12px;
  margin-top: 4px;
  margin-left: 5px;
  background-color: #42b983;
  color: #fff;
  border-color: #42b983;
}
.tabs-view-item-active:before {
  content: "";
  background: #fff;
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  position: relative;
  margin-right: 2px;
}
.el-icon-close {
  padding: 0.1rem;
}
.el-icon-close:hover {
  border-radius: 50%;
  background: #b4bccc;
  transition-duration: 0.3s;
}
.right-menu {
  margin-left: auto;
  display: flex;
  align-items: center;
}
.el-icon-caret-bottom {
  margin-left: 0.5rem;
  font-size: 0.75rem;
}
.screen-full {
  cursor: pointer;
  margin-right: 1rem;
  font-size: 1.25rem;
}
*::-webkit-scrollbar {
  width: 0.5rem;
  height: 6px;
}
*::-webkit-scrollbar-thumb {
  border-radius: 0.5rem;
  background-color: rgba(144, 147, 153, 0.3);
}
</style>
