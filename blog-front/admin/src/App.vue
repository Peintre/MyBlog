<template>
    <div id="app">
      <router-view :key="this.$route.path"/>
    </div>
</template>


<script>
import {generaMenu } from '@/assets/js/menu'
import store from "@/store"
export default {
  created() {
    // 刷新页面查询用户菜单
    if (store.state.userId!=null) {
      generaMenu();
    }
    // 上传访客信息
   // this.axios.post("/api/report");
  }
}
</script>

<style>
/* 修改element样式 */
.login-container .el-form-item {
  margin-bottom: 22px !important;
}
.login-container .el-input__inner {
  height: 40px !important;
  line-height: 40px !important;
}
.login-container .el-button {
  height: 40px !important;
  line-height: 40px !important;
}
.my-upload-cover .el-upload-dragger{
  padding: 0;
  line-height: 0;
}

.my-page-card .el-upload-dragger,
.my-album-card .el-upload-dragger,
.my-photo-card .el-upload-dragger{
  padding: 0;
  height: 180px;
  width: 360px;
}
.my-page-card .el-upload-dragger .el-icon,
.my-album-card .el-upload-dragger .el-icon,
.my-photo-card .el-upload-dragger .el-icon{
  font-size: 67px;
  color: #c0c4cc;
  line-height: 50px;
  margin: 40px 0 16px;
}
.my-photo-card .photo-list .el-checkbox,
.my-deletePhoto-card .photo-list .el-checkbox{
  display: initial;
}
.my-photo-card .photo-container .el-checkbox__label,
.my-deletePhoto-card .photo-container .el-checkbox__label{
  width: 100%;
}
.my-photo-card .photo-container .el-checkbox__input,
.my-deletePhoto-card .photo-container .el-checkbox__input{
  top: -6rem;
}
.my-photo-card .albumList-item .el-radio-button{
  width: 100%;
  height: 100%;
}
.my-photo-card .albumList-item .el-radio-button .el-radio-button__inner{
  width: 100%;
  height: 100%;
  align-items: center;
  justify-content: center;
  display: flex;
  background: #ecf5ff;
}
.my-photo-card .albumList-item .is-active .el-radio-button__inner{
  background: #409eff;
}




    


</style>

