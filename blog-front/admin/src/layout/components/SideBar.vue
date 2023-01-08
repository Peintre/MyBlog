<template>
  <div>
    <el-menu
      class="side-nav-bar"
      router
      :collapse="this.$store.state.collapse"    
      :default-active="this.$route.path"
      background-color="#304156"
      text-color="#BFCBD9"
      active-text-color="#409EFF"
    >
    <template v-for="route of this.$store.state.userMenuList">
      <!-- 有子菜单的菜单渲染 -->
      <template v-if="route.name && !route.hidden && route.childMenuList">
        <el-sub-menu :key="route.path" :index="route.path">
          <!-- 菜单标题 -->
          <template #title>
            <i :class="'iconfont '+route.icon"></i>
            <span>{{ route.name }}</span>
          </template>
          <!-- 菜单选项 -->
          <template v-for="(item, index) of route.childMenuList">
            <el-menu-item v-if="!item.hidden" :key="index" :index="item.path">
                <i :class="'iconfont '+item.icon"></i>
                <span>{{ item.name }}</span>
            </el-menu-item>
          </template>
        </el-sub-menu>
      </template>
      <!-- 无子菜单的菜单渲染 -->
      <template v-else-if="!route.hidden">
        <el-menu-item :index="route.path" :key="route.path">
          <i :class="'iconfont '+route.icon"></i>
          <span>{{ route.name }}</span>
        </el-menu-item>
      </template>
    </template>
    </el-menu>
  </div>
</template>
<style scoped>
.side-nav-bar:not(.el-menu--collapse) {
  width: 210px;
}
.side-nav-bar {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  overflow-x: hidden;
  overflow-y: auto;
}
.side-nav-bar i {
  margin-right: 1rem;
}
*::-webkit-scrollbar {
  width: 0.5rem;
  height: 1px;
}
*::-webkit-scrollbar-thumb {
  border-radius: 0.5rem;
  background-color: rgba(144, 147, 153, 0.3);
}
</style>
